package com.example.demo.dao;

import com.example.demo.enums.LivroStatusENUM;
import com.example.demo.interfaces.ICRUD;
import com.example.demo.models.Categoria;
import com.example.demo.models.Livro;
import com.fasterxml.jackson.annotation.JacksonInject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class BibliotecaDAO implements ICRUD<Livro>  {

    @Autowired
    private CategoriasDAO categoriasdao;

    private List<Livro> biblioteca = Collections.synchronizedList(new ArrayList<>());

    @Override
    public ResponseEntity<String> addItem(Livro livro) {
        livro.setId(biblioteca.size());
        if(categoriasdao.readItem(livro.getCategoria()) == null) {
            return new ResponseEntity<>("Categoria não existe!", HttpStatus.NOT_FOUND);
        }
        biblioteca.add(livro);
        return new ResponseEntity<>("Livro adicionado!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateItem(int id, Livro livro) {
        Predicate<Livro> validaId = l -> l.getId() == id;
        if(readItem(id) == null) {
            return new ResponseEntity<>("Livro não encontrado!", HttpStatus.NOT_FOUND);
        }
        if(categoriasdao.readItem(livro.getCategoria()) == null) {
            return new ResponseEntity<>("Categoria não existe!", HttpStatus.NOT_FOUND);
        }
        biblioteca.removeIf(validaId);
        biblioteca.add(livro);
        return new ResponseEntity<>("Livro atualizado!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteItem(int id) {
        Predicate<Livro> validaId = l -> l.getId() == id;
        if(biblioteca.removeIf(validaId)) {
            return new ResponseEntity<>("Livro deletado!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Livro não encontrado!", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<List<Livro>> readAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("List-Size:", Integer.toString(biblioteca.size()));

        return new ResponseEntity<>(biblioteca, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Livro> readItem(int id) {
        Predicate<Livro> validaId = l -> l.getId() == id;
        Optional<Livro> livro = biblioteca.stream().filter(validaId).findFirst();
        if(livro.isPresent()) {
            return new ResponseEntity<>(livro.get(), HttpStatus.OK);
        }
        return null;
    }

    public boolean exists(int id) {
        return !(readItem(id).getBody() == null);
    }

    public boolean isDisponivel(int id) {
        return readItem(id).getBody().getStatus() == LivroStatusENUM.DISPONIVEL;
    }

//    public ResponseEntity<String> rentBook(int id) {
//        Predicate<Livro> validaId = l -> l.getId() == id;
//        Optional<Livro> livro = biblioteca.stream().filter(validaId).findFirst();
//        if(isDisponivel(id)) {
//            livro.get().setStatus(LivroStatusENUM.ALUGADO);
//            return new ResponseEntity<>("Livro alugado!", HttpStatus.OK);
//        }
//        return new ResponseEntity<>("Livro indisponível!", HttpStatus.OK);
//    }
//
//    public ResponseEntity<String> buyBook(int id) {
//        Predicate<Livro> validaId = l -> l.getId() == id;
//        Optional<Livro> livro = biblioteca.stream().filter(validaId).findFirst();
//        if(isDisponivel(id)) {
//            livro.get().setStatus(LivroStatusENUM.COMPRADO);
//            return new ResponseEntity<>("Livro comprado!", HttpStatus.OK);
//        }
//        return new ResponseEntity<>("Livro indisponível!", HttpStatus.OK);
//    }

}
