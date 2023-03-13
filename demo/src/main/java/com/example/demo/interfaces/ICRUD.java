package com.example.demo.interfaces;

import com.example.demo.models.Livro;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICRUD<T> {

    ResponseEntity<String> addItem(T object);

    ResponseEntity<String> updateItem(int id, T object);

    ResponseEntity<String> deleteItem(int id);

    ResponseEntity<List<T>> readAll();

    ResponseEntity<T> readItem(int id);
}
