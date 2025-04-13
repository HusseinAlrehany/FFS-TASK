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
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final ModelMapper modelMapper;

    public BookDTO createBook(BookDTO bookDTO) {

       Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(()-> new NotFoundException("No Author Found"));

        if(bookDTO.getName().isBlank()|| bookDTO.getType() == null){
            throw new ArgumentException("Book Name and Type Is Required");
        }
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setName(bookDTO.getName());
        book.setType(bookDTO.getType());
        book.setPrice(bookDTO.getPrice());
        book.setSerialNumber(bookDTO.getSerialNumber());
        book.setAuthor(author);

         bookRepository.save(book);

        return modelMapper.map(book, BookDTO.class);
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
        book.setPrice(bookDTO.getPrice());
        book.setSerialNumber(bookDTO.getSerialNumber());
        book.setAuthor(author);

        bookRepository.save(book);
        return modelMapper.map(book, BookDTO.class);
    }

    public void deleteBookById(int bookId) {
       Book book = bookRepository.findById(bookId).
                orElseThrow(()-> new NotFoundException("No Book Found"));
        bookRepository.deleteById(bookId);
    }

    public List<BookDTO> findAllBooks() {
        //optional.of -> if the value won't be null, but may be empty
        //optional.ofNullable -> if the value might be null
        List<Book> books = bookRepository.findAll();

        if(books.isEmpty()){
           throw new NotFoundException("No Books Found");
        }
        return modelMapper.map(books, new TypeToken<List<BookDTO>>(){}.getType());
    }

    public Page<Book> getAllBooks(int page, int size){

        return bookRepository.findAll(PageRequest.of(page, size));
    }

    public List<BookDTO> filterBookByNameAndPrice(String type,  Long price, int authorId){

          //validating the enum type by passing as string then store it in enum type
          Type bookTypeEnum = Type.fromStringToType(type);

          if(price == null || price < 0){
              throw new ArgumentException("Invalid Price");
          }

          if(authorId < 0 ){
              throw new ArgumentException("Invalid author ID");
          }

        List<Book> filteredBooks = bookRepository.findByTypeAndPriceGreaterThanAndAuthor_Id(bookTypeEnum, price, authorId);

          if(filteredBooks.isEmpty()){
              throw new NotFoundException("No Books Found For That Search Criteria!");
          }

        //new TypeToken<>.getType() used to tell model mapper
        //what is the targeted type should be
        //because java erases the type parameter at runtime
        //make it impossible for model mapper to directly infer the generic type
        return modelMapper.map(filteredBooks, new TypeToken<List<BookDTO>>(){}.getType());
    }

}
