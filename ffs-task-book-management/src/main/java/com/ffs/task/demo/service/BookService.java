package com.ffs.task.demo.service;

import com.ffs.task.demo.dtos.BookDTO;
import com.ffs.task.demo.entities.Author;
import com.ffs.task.demo.entities.Book;
import com.ffs.task.demo.repository.AuthorRepository;
import com.ffs.task.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {


    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;


    public BookDTO createBook(BookDTO bookDTO) {
        Optional<Author> optionalAuthor = authorRepository.findById(bookDTO.getAuthorId());
        if(optionalAuthor.isEmpty()){
            throw new RuntimeException("No Author Found");
        }
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setName(book.getName());
        book.setType(bookDTO.getType());
        book.setAuthor(optionalAuthor.get());

        return book.getBookDTO();
    }
}
