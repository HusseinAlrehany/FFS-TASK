package com.ffs.task.demo.controller;

import com.ffs.task.demo.dtos.BookDTO;
import com.ffs.task.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {


   private final BookService bookService;

   @PostMapping("/books")
   public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO){

      return ResponseEntity.ok(bookService.createBook(bookDTO));
   }

}
