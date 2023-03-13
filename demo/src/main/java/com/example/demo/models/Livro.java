package com.example.demo.models;

import com.example.demo.abstracts.AModel;
import com.example.demo.enums.LivroStatusENUM;
import lombok.Builder;
import lombok.Data;

@Data
public class Livro extends AModel {
    private String titulo;
    private String autor;
    private int categoria;
    private LivroStatusENUM status = LivroStatusENUM.DISPONIVEL;

}
