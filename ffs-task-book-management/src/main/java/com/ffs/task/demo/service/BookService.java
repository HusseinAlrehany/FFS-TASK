package com.ffs.task.demo.service;

import com.ffs.task.demo.dtos.BookDTO;
import com.ffs.task.demo.entities.Author;
import com.ffs.task.demo.entities.Book;
import com.ffs.task.demo.entities.Type;
import com.ffs.task.demo.exception.ArgumentException;
import com.ffs.task.demo.exception.NotFoundException;
import com.ffs.task.demo.repository.AuthorRepository;
import com.ffs.task.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
            throw new NotFoundException("No Author Found");
        }
        if(bookDTO.getName().isBlank()|| bookDTO.getType() == null){
            throw new ArgumentException("Book Name and Type Is Required");
        }
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setName(bookDTO.getName());
        book.setType(bookDTO.getType());
        book.setPrice(bookDTO.getPrice());
        book.setSerialNumber(bookDTO.getSerialNumber());
        book.setAuthor(optionalAuthor.get());

         bookRepository.save(book);
        return book.getBookDTO();
    }

    public BookDTO updateBook(BookDTO bookDTO) {
        Optional<Book> optionalBook = bookRepository.findById(bookDTO.getId());
        Optional<Author> optionalAuthor = authorRepository.findById(bookDTO.getAuthorId());
        if(optionalBook.isEmpty() || optionalAuthor.isEmpty()){
            throw new NotFoundException("No Book Or Author Found");
        }
        Book book = optionalBook.get();
        Author author = optionalAuthor.get();
        book.setId(bookDTO.getId());
        book.setName(bookDTO.getName());
        book.setType(bookDTO.getType());
        book.setSerialNumber(bookDTO.getSerialNumber());
        book.setPrice(bookDTO.getPrice());
        book.setAuthor(author);

        bookRepository.save(book);

        return book.getBookDTO();
    }

    public void deleteBookById(int bookId) {
       Book book = bookRepository.findById(bookId).
                orElseThrow(()-> new NotFoundException("No Book Found"));
        bookRepository.deleteById(bookId);
    }

    public List<BookDTO> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        if(books.isEmpty()){
            throw  new NotFoundException("No Books Found");
        }
        return books.stream()
                .map(Book::getBookDTO)
                .collect(Collectors.toList());
    }

    public Page<Book> getAllBooks(int page, int size){

        return bookRepository.findAll(PageRequest.of(page, size));
    }

    public List<BookDTO> filterBookByNameAndPrice(Type type,  Long price, int authorId){

       if(price == null || price <= 0){
           throw new ArgumentException("price must be positive number");
       }
       if(authorId <= 0){
           throw new ArgumentException("authorId must be positive number");

       }
        List<Book> filteredBooks = bookRepository.findByTypeAndPriceGreaterThanAndAuthor_Id(type, price, authorId);
        if(filteredBooks.isEmpty()){
            throw new NotFoundException("No Books Found");
        }

        return filteredBooks.stream().map(Book::getBookDTO).collect(Collectors.toList());
    }
}
