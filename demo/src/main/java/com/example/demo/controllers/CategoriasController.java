package com.example.demo.controllers;

import com.example.demo.dao.CategoriasDAO;
import com.example.demo.models.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value="/categorias")
@RestController
public class CategoriasController {

    @Autowired
    private CategoriasDAO categorias;

    @GetMapping
    public ResponseEntity<List<Categoria>> getCategorias() {
        return categorias.readAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Categoria> getCategoria(@PathVariable(value="id") int id) {
        return categorias.readItem(id);
    }

    @PostMapping(consumes="application/json")
    public ResponseEntity<String> postCategorias(@RequestBody Categoria categoria) {
        return categorias.addItem(categoria);
    }

    @PutMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<String> updateCategoria(@PathVariable(value="id") int id,
                                                                @RequestBody Categoria categoria) {
        return categorias.updateItem(id, categoria);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCategoria(@PathVariable(value="id") int id) {
        return categorias.deleteItem(id);
    }

}
