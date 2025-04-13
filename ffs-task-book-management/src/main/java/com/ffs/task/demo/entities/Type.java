package com.ffs.task.demo.entities;

import com.ffs.task.demo.exception.NotFoundException;

import java.util.Arrays;

public enum Type {

    SCIENCE, HISTORY, FICTION;


    public static Type fromStringToType(String value){
        return Arrays.stream(Type.values())
                .filter(t-> t.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(()-> new NotFoundException("Invalid Book Type"));
    }


}
