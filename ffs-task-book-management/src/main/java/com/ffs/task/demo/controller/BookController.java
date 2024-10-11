package com.ffs.task.demo.controller;

import com.ffs.task.demo.dtos.BookDTO;
import com.ffs.task.demo.entities.Book;
import com.ffs.task.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {


   private final BookService bookService;



   @PostMapping("/createBook")
   public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO){

          return ResponseEntity.status(HttpStatus.CREATED).body(
                  bookService.createBook(bookDTO)
          );
      }

   @PutMapping("/updateBook")
   public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO){

      return ResponseEntity.ok(bookService.updateBook(bookDTO));
   }
   @DeleteMapping("/deleteBook/{bookId}")
   public ResponseEntity<Void> deleteBook(@PathVariable int  bookId){
      bookService.deleteBookById(bookId);
      return ResponseEntity.noContent().build();
   }

   @GetMapping("/getBooks")
   public ResponseEntity<List<BookDTO>> findAllBooks(){
      return ResponseEntity.ok(bookService.findAllBooks());
   }


   @GetMapping("/getBooksPaginated")
   public ResponseEntity<Page<Book>> getAllBooks(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size){

      return ResponseEntity.ok(bookService.getAllBooks(page, size));

   }
}
