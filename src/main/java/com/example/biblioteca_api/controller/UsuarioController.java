package com.example.biblioteca_api.controller;

import com.example.biblioteca_api.security.JwtService;
import com.example.biblioteca_api.model.Usuario;
import com.example.biblioteca_api.repository.UsuarioRepository;
import com.example.biblioteca_api.dto.UsuarioRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/cadastro")
    public Usuario cadastro(@RequestBody UsuarioRequestDTO request) {
        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setRole("USER");

        return usuarioRepository.save(usuario);
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findByNome(requestDTO.getNome());

        if (usuario == null) {
            return "Usuário não encontrado";
        }

        if (passwordEncoder.matches(requestDTO.getSenha(), usuario.getSenha())) {
            return jwtService.gerarToken(usuario.getNome(), usuario.getRole());
        } else {
            return "Senha incorreta";
        }
    }
}