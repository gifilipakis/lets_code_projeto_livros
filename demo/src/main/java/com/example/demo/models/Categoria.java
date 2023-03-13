package com.example.demo.models;

import com.example.demo.abstracts.AModel;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Categoria extends AModel {

    @NonNull
    private String nomeCategoria;

    @Override
    public String toString(){
        return "Id: "+getId()+" | "+"Nome: "+nomeCategoria;
    }

}
