package com.example.biblioteca_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table (name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String titulo;
    @Min(1000)
    private int ano_publicacao;


    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void  setAno_publicacao(int ano_publicacao) {
       this.ano_publicacao = ano_publicacao;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    public int getAno_publicacao() {
        return ano_publicacao;
    }
}