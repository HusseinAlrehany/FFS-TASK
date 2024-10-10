package com.ffs.task.demo.dtos;

import com.ffs.task.demo.entities.Type;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class BookDTO {

    private int id;

    private String name;

    private Type type;

    private int authorId;

    private String serialNumber;

    private Long price;

    private Date creationDate;

    private Date lastUpdated;

    private String authorName;
}
