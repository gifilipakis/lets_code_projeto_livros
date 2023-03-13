package com.example.demo.dao;

import com.example.demo.interfaces.ICRUD;
import com.example.demo.models.Cliente;
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
public class ClientesDAO implements ICRUD<Cliente> {

    private List<Cliente> clientes = Collections.synchronizedList(new ArrayList<>());

    @Override
    public ResponseEntity<String> addItem(Cliente cliente) {
        cliente.setId(clientes.size());
        clientes.add(cliente);

        return new ResponseEntity<>("Cliente adicionado!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateItem(int id, Cliente cliente) {
        Predicate<Cliente> validaId = c -> c.getId() == id;
//        cliente.setId(id);
        if(clientes.removeIf(validaId)) {
            clientes.add(cliente);
            return new ResponseEntity<>("Cliente atualizado!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Cliente não encontrado!", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> deleteItem(int id) {
        Predicate<Cliente> validaId = c -> c.getId() == id;
        if(clientes.removeIf(validaId)) {
            return new ResponseEntity<>("Cliente deletado!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Cliente não encontrado!", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<List<Cliente>> readAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("List-Size:", Integer.toString(clientes.size()));

        return new ResponseEntity<>(clientes, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Cliente> readItem(int id) {
        Predicate<Cliente> validaId = c -> c.getId() == id;
        Optional<Cliente> cliente = clientes.stream().filter(validaId).findFirst();
        if(cliente.isPresent()) {
            return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
        }
        return null;
    }

    public boolean exists(int id) {
        return !(readItem(id).getBody() == null);
    }

}
