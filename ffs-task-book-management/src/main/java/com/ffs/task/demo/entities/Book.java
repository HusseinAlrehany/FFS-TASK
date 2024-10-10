package com.ffs.task.demo.entities;

import com.ffs.task.demo.dtos.BookDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "book")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private Type type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Author author;



    public BookDTO getBookDTO(){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(id);
        bookDTO.setName(name);
        bookDTO.setType(type);
        bookDTO.setAuthorId(author.getId());
        return bookDTO;

    }
}


