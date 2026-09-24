package com.example.biblioteca_api;

import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository  extends CrudRepository<Usuario, Integer> {

    Usuario findByNome(String nome);
}
