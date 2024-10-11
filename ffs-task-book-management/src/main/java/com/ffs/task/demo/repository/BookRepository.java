package com.ffs.task.demo.repository;

import com.ffs.task.demo.entities.Book;
import com.ffs.task.demo.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>, PagingAndSortingRepository<Book, Integer> {

    //@Query("SELECT b FROM Book b WHERE b.id = :id AND b.name = :name AND b.price > :price")
    //List<Book> filterBooksByIdNameAndPrice(@Param("id") int id, @Param("name") String name, @Param("price") Long price);

    List<Book> findByTypeAndPriceGreaterThanAndAuthor_Id(Type type, Long price, int authorId);


}
