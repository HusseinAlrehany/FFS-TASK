package com.ffs.task.demo.controller;

import com.ffs.task.demo.dtos.BookDTO;
import com.ffs.task.demo.service.BookService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {

   private final BookService bookService;


   @PostMapping("/books")
   public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO){

          return ResponseEntity.status(HttpStatus.CREATED).body(
                  bookService.createBook(bookDTO)
          );
      }

   @PutMapping("/books")
   public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO){

      return ResponseEntity.ok(bookService.updateBook(bookDTO));
   }
   @DeleteMapping("/books/{bookId}")
   public ResponseEntity<Void> deleteBook(@PathVariable int  bookId){
      bookService.deleteBookById(bookId);
      return ResponseEntity.noContent().build();
   }

   @GetMapping("/books")
   public ResponseEntity<List<BookDTO>> findAllBooks(){
      return ResponseEntity.ok(bookService.findAllBooks());
   }
}
