package com.ffs.task.demo.service;

import com.ffs.task.demo.dtos.BookDTO;
import com.ffs.task.demo.entities.Author;
import com.ffs.task.demo.entities.Book;
import com.ffs.task.demo.repository.AuthorRepository;
import com.ffs.task.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        book.setName(bookDTO.getName());
        book.setType(bookDTO.getType());
        book.setAuthor(optionalAuthor.get());

         bookRepository.save(book);
        return book.getBookDTO();
    }

    public BookDTO updateBook(BookDTO bookDTO) {
        Optional<Book> optionalBook = bookRepository.findById(bookDTO.getId());
        Optional<Author> optionalAuthor = authorRepository.findById(bookDTO.getAuthorId());
        if(optionalBook.isEmpty() || optionalAuthor.isEmpty()){
            throw new RuntimeException("No Book Or Author Found");
        }
        Book book = optionalBook.get();
        Author author = optionalAuthor.get();
        book.setId(bookDTO.getId());
        book.setName(bookDTO.getName());
        book.setType(bookDTO.getType());
        book.setAuthor(author);

        bookRepository.save(book);

        return book.getBookDTO();
    }

    public void deleteBookById(int bookId) {
        bookRepository.deleteById(bookId);
    }

    public List<BookDTO> findAllBooks() {
        return bookRepository.findAll().stream()
                .map(Book::getBookDTO)
                .collect(Collectors.toList());
    }
}
