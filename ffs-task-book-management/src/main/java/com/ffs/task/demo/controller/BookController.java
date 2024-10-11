package com.ffs.task.demo.controller;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import com.ffs.task.demo.dtos.BookDTO;
import com.ffs.task.demo.entities.Book;
import com.ffs.task.demo.entities.Type;
import com.ffs.task.demo.service.BookService;
import com.ffs.task.demo.service.JasperReportService;
import com.ffs.task.demo.service.JasperService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {


   private final BookService bookService;

   @Autowired
   private JasperReportService jasperReportService;

   @Autowired
   private JasperService jasperService;



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

   @GetMapping("/filterBooks")
   public String filterBooks(@RequestParam(defaultValue = "pdf") String format,
                                                    @RequestParam Type type,
                                                    @RequestParam Long price,
                                                    @RequestParam int authorId) throws JRException, FileNotFoundException {
      return jasperService.exportReport(format, type, price, authorId);

   }

   @GetMapping("/bookReport/{format}")
   public String generateReport(@PathVariable String format) throws JRException, FileNotFoundException {
       return jasperReportService.exportReport(format);
   }
}
