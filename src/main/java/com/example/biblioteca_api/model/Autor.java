package com.example.biblioteca_api.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table (name ="autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String nome ;

    @NotBlank
    private String nacionalidade;

    public  int getId()
    {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getNacionalidade() {
        return nacionalidade;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

}
