package com.ffs.task.demo.dtos;

import com.ffs.task.demo.entities.Type;
import lombok.Data;

@Data
public class BookDTO {

    private int id;

    private String name;

    private Type type;

    private int authorId;

    private String authorName;
}
