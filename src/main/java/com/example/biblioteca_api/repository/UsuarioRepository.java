package com.example.biblioteca_api.repository;

import com.example.biblioteca_api.model.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository  extends CrudRepository<Usuario, Integer> {

    Usuario findByNome(String nome);
}
