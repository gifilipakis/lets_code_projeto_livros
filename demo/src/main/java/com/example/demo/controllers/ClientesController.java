package com.example.demo.controllers;

import com.example.demo.dao.ClientesDAO;
import com.example.demo.models.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/clientes")
@RestController
public class ClientesController {

    @Autowired
    private ClientesDAO clientes;

    @GetMapping
    public ResponseEntity<List<Cliente>> getClientes() {
        return clientes.readAll();
    }

    @GetMapping(value = "/cliente/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable(value="id") int id) {
        return clientes.readItem(id);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> postClientes(@RequestBody Cliente cliente) {
        return clientes.addItem(cliente);
    }

    @PutMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<String> updateCliente(@PathVariable(value="id") int id, @RequestBody Cliente cliente) {
        return clientes.updateItem(id, cliente);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable(value="id") int id) {
        return clientes.deleteItem(id);
    }

}
