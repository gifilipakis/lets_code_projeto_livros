package com.example.demo.dao;

import com.example.demo.enums.LivroStatusENUM;
import com.example.demo.enums.TipoOperacaoENUM;
import com.example.demo.models.Cliente;
import com.example.demo.models.Livro;
import com.example.demo.models.Operacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class OperacoesDAO {

    @Autowired
    private  ClientesDAO clientesdao;
    @Autowired
    private BibliotecaDAO bibliotecadao;

    private List<Operacao> operacoes = Collections.synchronizedList(new ArrayList<>());

    public ResponseEntity<String> addItem(int idCliente, int idLivro, TipoOperacaoENUM tipo) {
        Cliente cliente = clientesdao.readItem(idCliente).getBody();
        Livro livro = bibliotecadao.readItem(idLivro).getBody();

        if(!bibliotecadao.exists(idLivro)) {
            return new ResponseEntity<>("Livro não encontrado!", HttpStatus.NOT_FOUND);
        }

        if(!bibliotecadao.isDisponivel(idLivro)) {
            return new ResponseEntity<>("Livro indisponível!", HttpStatus.EXPECTATION_FAILED);
        }

        if(!clientesdao.exists(idCliente)) {
            return new ResponseEntity<>("Cliente não encontrado!", HttpStatus.NOT_FOUND);
        }

        livro.setStatus(LivroStatusENUM.ALUGADO);
        Operacao operacao = new Operacao(tipo, cliente, livro);
        operacoes.add(operacao);
        return new ResponseEntity<>("Operação realizada!", HttpStatus.OK);
    }

}

