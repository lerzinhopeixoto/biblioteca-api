package com.example.biblioteca_api;
import org.springframework.data.jpa.repository.JpaRepository;
public interface LivroRepository extends JpaRepository<com.example.biblioteca_api.Livro, Integer>{
}
