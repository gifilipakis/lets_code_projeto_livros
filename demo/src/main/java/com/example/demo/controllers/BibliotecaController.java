package com.example.demo.controllers;

import com.example.demo.dao.BibliotecaDAO;
import com.example.demo.dao.OperacoesDAO;
import com.example.demo.enums.TipoOperacaoENUM;
import com.example.demo.models.Livro;
import com.example.demo.models.Operacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value="/biblioteca")
@RestController
public class BibliotecaController {

    @Autowired
    private BibliotecaDAO biblioteca;

    @Autowired
    private OperacoesDAO operacoesdao;

    @GetMapping
    public ResponseEntity<List<Livro>> getBiblioteca() {

        return biblioteca.readAll();
    }

    @GetMapping(value = "/livro/{id}")
    public ResponseEntity<Livro> getLivro(@PathVariable(value="id") int id) {

        return biblioteca.readItem(id);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> postBiblioteca(@RequestBody Livro livro) {
        return biblioteca.addItem(livro);
    }

    @PutMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<String> updateLivro(@PathVariable(value="id") int id, @RequestBody Livro livro) {
        return biblioteca.updateItem(id, livro);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteLivro(@PathVariable(value="id") int id) {

        return biblioteca.deleteItem(id);
    }

    @PostMapping(value = "/aluguel/{idCliente}/{idLivro}")
    public ResponseEntity<String> rentLivro(@PathVariable(value="id") int idCliente,
                                            @PathVariable(value="id") int idLivro) {
        return operacoesdao.addItem(idCliente, idLivro, TipoOperacaoENUM.ALUGUEL);
    }

    @PostMapping(value = "/compra/{idCliente}/{idLivro}")
    public ResponseEntity<String> buyLivro(@PathVariable(value="id") int idCliente,
                                            @PathVariable(value="id") int idLivro) {
        return operacoesdao.addItem(idCliente, idLivro, TipoOperacaoENUM.COMPRA);
    }
}
