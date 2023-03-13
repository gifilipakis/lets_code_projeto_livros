package com.example.demo.dao;

import com.example.demo.interfaces.ICRUD;
import com.example.demo.models.Categoria;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Configuration
public class CategoriasDAO implements ICRUD<Categoria>  {

    private List<Categoria> categorias = Collections.synchronizedList(new ArrayList<>());

    @Primary
    @Bean
    public CategoriasDAO CategoriasConfigurationBean() {
        return new CategoriasDAO(){{
            addItem(new Categoria("categoria_1"));
            addItem(new Categoria("categoria_2"));
            addItem(new Categoria("categoria_3"));
        }};
    }

    @Override
    public ResponseEntity<String> addItem(Categoria categoria) {
        categoria.setId(categorias.size());
        categorias.add(categoria);

        return new ResponseEntity<>("Categoria adicionada!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateItem(int id, Categoria categoria) {
        Predicate<Categoria> validaId = c -> c.getId() == id;
        categoria.setId(id);
        if(categorias.removeIf((validaId))) {
            categorias.add(categoria);
            return new ResponseEntity<>("Categoria atualizada!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Categoria não encontrada!", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> deleteItem(int id) {
        Predicate<Categoria> validaId = c -> c.getId() == id;
        if(categorias.removeIf(validaId)) {
            return new ResponseEntity<>("Categoria deletada!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Categoria não encontrada!", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<List<Categoria>> readAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("List-Size:", Integer.toString(categorias.size()));

        return new ResponseEntity<>(categorias, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Categoria> readItem(int id) {
        Predicate<Categoria> validaId = c -> c.getId() == id;
        Optional<Categoria> categoria = categorias.stream().filter(validaId).findFirst();
        if(categoria.isPresent()) {
            return new ResponseEntity<>(categoria.get(), HttpStatus.OK);
        }
        return null;
    }

}
