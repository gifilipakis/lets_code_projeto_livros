package com.example.demo.models;

import com.example.demo.abstracts.AModel;
import com.example.demo.enums.TipoOperacaoENUM;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Operacao extends AModel {

    private TipoOperacaoENUM tipoOperacao;
    private Cliente cliente;
    private Livro livro;

}
