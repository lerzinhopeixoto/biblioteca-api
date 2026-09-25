package com.example.biblioteca_api.controller;

import com.example.biblioteca_api.model.Autor;
import com.example.biblioteca_api.repository.AutorRepository;
import com.example.biblioteca_api.dto.AutorRequestDTO;
import com.example.biblioteca_api.dto.AutorResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;

    @GetMapping("/autores")
    public Page<AutorResponseDTO> listarAutores(Pageable pageable) {
        Page<Autor> autoresPage = autorRepository.findAll(pageable);
        return autoresPage.map(this::converterParaDTO);
    }

    @GetMapping("/autores/{id}")
    public ResponseEntity<AutorResponseDTO> buscarAutoresPorId(@PathVariable int id) {
        Optional<Autor> autorOptional = autorRepository.findById(id);

        if (autorOptional.isPresent()) {
            return ResponseEntity.ok(converterParaDTO(autorOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/autores")
    public AutorResponseDTO criarAutor(@Valid @RequestBody AutorRequestDTO requestDTO) {
        Autor autor = new Autor();
        autor.setNome(requestDTO.getNome());
        autor.setNacionalidade(requestDTO.getNacionalidade());

        Autor autorSalvo = autorRepository.save(autor);

        return converterParaDTO(autorSalvo);
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

        return ResponseEntity.ok(converterParaDTO(autorSalvo));
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

    private AutorResponseDTO converterParaDTO(Autor autor) {
        AutorResponseDTO dto = new AutorResponseDTO();
        dto.setId(autor.getId());
        dto.setNome(autor.getNome());
        dto.setNacionalidade(autor.getNacionalidade());
        return dto;
    }
}