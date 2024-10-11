package com.ffs.task.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ffs.task.demo.dtos.BookDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "book")
@Data
public class Book  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "serial_number")
    private String serialNumber;

    private Long price;

    @Column(name = "creation_date")
    @CreationTimestamp
    private Date creationDate;

    @Column(name = "last_updated")
    @UpdateTimestamp
    private Date lastUpdated;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Author author;



    @JsonIgnore
    public BookDTO getBookDTO(){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(id);
        bookDTO.setName(name);
        bookDTO.setType(type);
        bookDTO.setAuthorId(author.getId());
        bookDTO.setPrice(price);
        bookDTO.setSerialNumber(serialNumber);
        bookDTO.setCreationDate(creationDate);
        bookDTO.setLastUpdated(lastUpdated);
        bookDTO.setAuthorName(author.getName());
        return bookDTO;

    }
}


