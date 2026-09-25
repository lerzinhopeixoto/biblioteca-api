package com.example.biblioteca_api.controller;

import com.example.biblioteca_api.dto.LivroRequestDTO;
import com.example.biblioteca_api.dto.LivroResponseDTO;
import com.example.biblioteca_api.model.Autor;
import com.example.biblioteca_api.model.Livro;
import com.example.biblioteca_api.repository.AutorRepository;
import com.example.biblioteca_api.repository.LivroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @GetMapping("/livros")
    public Page<LivroResponseDTO> listarLivrosDTO(Pageable pageable) {
        Page<Livro> livrosPage = livroRepository.findAll(pageable);
        return livrosPage.map(this::converterParaDTO);
    }

    @GetMapping("/livros/{id}")
    public ResponseEntity<LivroResponseDTO> buscarLivroPorId(@PathVariable int id) {
        Optional<Livro> livroOptional = livroRepository.findById(id);

        if (livroOptional.isPresent()) {
            return ResponseEntity.ok(converterParaDTO(livroOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/livros")
    public LivroResponseDTO criar(@Valid @RequestBody LivroRequestDTO requestDTO) {
        Autor autor = autorRepository.findById(requestDTO.getAutorId()).orElseThrow();

        Livro livro = new Livro();
        livro.setTitulo(requestDTO.getTitulo());
        livro.setAno_publicacao(requestDTO.getAno_publicacao());
        livro.setAutor(autor);

        Livro livroSalvo = livroRepository.save(livro);

        return converterParaDTO(livroSalvo);
    }

    @PutMapping("/livros/{id}")
    public ResponseEntity<LivroResponseDTO> atualizar(@PathVariable int id, @Valid @RequestBody LivroRequestDTO requestDTO) {
        if (!livroRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Autor autor = autorRepository.findById(requestDTO.getAutorId()).orElseThrow();
        Livro livro = new Livro();
        livro.setId(id);
        livro.setTitulo(requestDTO.getTitulo());
        livro.setAno_publicacao(requestDTO.getAno_publicacao());
        livro.setAutor(autor);

        Livro livroSalvo = livroRepository.save(livro);

        return ResponseEntity.ok(converterParaDTO(livroSalvo));
    }

    @DeleteMapping("/livros/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        Optional<Livro> livro = livroRepository.findById(id);
        if (livro.isPresent()) {
            livroRepository.delete(livro.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private LivroResponseDTO converterParaDTO(Livro livro) {
        LivroResponseDTO dto = new LivroResponseDTO();
        dto.setId(livro.getId());
        dto.setTitulo(livro.getTitulo());
        dto.setAno_publicacao(livro.getAno_publicacao());
        dto.setAutorNome(livro.getAutor().getNome());
        return dto;
    }
}