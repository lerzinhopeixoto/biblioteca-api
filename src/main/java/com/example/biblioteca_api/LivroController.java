package com.example.biblioteca_api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
public class LivroController {

    @Autowired
    private com.example.biblioteca_api.LivroRepository livroRepository;

    @GetMapping("/livros")
    public Page<LivroResponseDTO> listarLivrosDTO(Pageable pageable) {
        Page<Livro> livrosPage = livroRepository.findAll(pageable);

        return livrosPage.map(livro -> {
            LivroResponseDTO dto = new LivroResponseDTO();
            dto.setId(livro.getId());
            dto.setTitulo(livro.getTitulo());
            dto.setAno_publicacao(livro.getAno_publicacao());
            dto.setAutorNome(livro.getAutor().getNome());
            return dto;
        });
    }


    @Autowired
    private com.example.biblioteca_api.AutorRepository autorRepository;

    @GetMapping("/autores")
    public Page<AutorResponseDTO> listarAutores(Pageable pageable) {
        Page<Autor> autoresPage = autorRepository.findAll(pageable);

        return autoresPage.map(autor -> {
            AutorResponseDTO dto = new AutorResponseDTO();
            dto.setId(autor.getId());
            dto.setNome(autor.getNome());
            dto.setNacionalidade(autor.getNacionalidade());
            return dto;
        });
    }


    @PostMapping("/livros")
    public LivroResponseDTO criar(@Valid @RequestBody LivroRequestDTO requestDTO) {
        Autor autor = autorRepository.findById(requestDTO.getAutorId()).orElseThrow();

        Livro livro = new Livro();
        livro.setTitulo(requestDTO.getTitulo());
        livro.setAno_publicacao(requestDTO.getAno_publicacao());
        livro.setAutor(autor);

        Livro livroSalvo = livroRepository.save(livro);

        LivroResponseDTO responseDTO = new LivroResponseDTO();
        responseDTO.setId(livroSalvo.getId());
        responseDTO.setTitulo(livroSalvo.getTitulo());
        responseDTO.setAno_publicacao(livroSalvo.getAno_publicacao());
        responseDTO.setAutorNome(livroSalvo.getAutor().getNome());

        return responseDTO;
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

        LivroResponseDTO responseDTO = new LivroResponseDTO();
        responseDTO.setId(livroSalvo.getId());
        responseDTO.setTitulo(livroSalvo.getTitulo());
        responseDTO.setAno_publicacao(livroSalvo.getAno_publicacao());
        responseDTO.setAutorNome(livroSalvo.getAutor().getNome());
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/livros/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        Optional<com.example.biblioteca_api.Livro> livro = livroRepository.findById(id);
        if (livro.isPresent()) {
            livroRepository.delete(livro.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/autores")
    public AutorResponseDTO criarAutor(@Valid @RequestBody AutorRequestDTO requestDTO) {
        Autor autor = new Autor();
        autor.setNome(requestDTO.getNome());
        autor.setNacionalidade(requestDTO.getNacionalidade());

        Autor autorSalvo = autorRepository.save(autor);

        AutorResponseDTO responseDTO = new AutorResponseDTO();
        responseDTO.setId(autorSalvo.getId());
        responseDTO.setNome(autorSalvo.getNome());
        responseDTO.setNacionalidade(autorSalvo.getNacionalidade());

        return responseDTO;
    }

    @PutMapping("/autores/{id}")
    public ResponseEntity<AutorResponseDTO> atualizarAutor(@PathVariable int id, @Valid @RequestBody AutorRequestDTO requestDTO) {
        if (!autorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Autor autor = autorRepository.findById(id).orElseThrow();
        autor.setNome(requestDTO.getNome());
        autor.setNacionalidade(requestDTO.getNacionalidade());

        Autor autorSalvo = autorRepository.save(autor);

        AutorResponseDTO responseDTO = new AutorResponseDTO();
        responseDTO.setId(autorSalvo.getId());
        responseDTO.setNome(autorSalvo.getNome());
        responseDTO.setNacionalidade(autorSalvo.getNacionalidade());

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/autores/{id}")
    public ResponseEntity<Object> deletarAutor(@PathVariable int id) {
        if (!autorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        try {
            autorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/livros/{id}")
    public ResponseEntity<LivroResponseDTO> buscarLivroPorId(@PathVariable int id) {
        Optional<Livro> livroOptional = livroRepository.findById(id);

        if (livroOptional.isPresent()) {
            Livro livro1 = livroOptional.get();
            LivroResponseDTO dto = new LivroResponseDTO();
            dto.setId(livro1.getId());
            dto.setTitulo(livro1.getTitulo());
            dto.setAno_publicacao(livro1.getAno_publicacao());
            dto.setAutorNome(livro1.getAutor().getNome());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/autores/{id}")
    public ResponseEntity<AutorResponseDTO> buscarAutoresPorId(@PathVariable int id) {
        Optional<Autor> autorOptional = autorRepository.findById(id);

        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            AutorResponseDTO dto = new AutorResponseDTO();
            dto.setId(autor.getId());
            dto.setNome(autor.getNome());
            dto.setNacionalidade(autor.getNacionalidade());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }
    }
