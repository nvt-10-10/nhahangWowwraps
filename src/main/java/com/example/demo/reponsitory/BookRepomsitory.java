package com.example.demo.reponsitory;

import com.example.demo.model.entity.Book;
import com.example.demo.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepomsitory extends CrudRepository<Book,Integer> {
    @Query("SELECT b FROM Book b where b.status = 0 ORDER BY b.id DESC ")
    Page<Book> findByPage(Pageable pageable);

    @Query("SELECT b FROM Book b WHERE (b.id = :id OR b.name LIKE %:name%) AND b.status = 0 ORDER BY b.id DESC")
    Page<Book> findByPageAndIdOrName(Pageable pageable, @Param("id") Integer id, @Param("name") String name);

    @Query("SELECT count(b) FROM Book b WHERE (b.id = :id OR b.name LIKE %:name%) AND b.status = 0 ")
    long countByKeyWord( @Param("id") Integer id, @Param("name") String name);


    @Query("SELECT count(b) FROM Book b WHERE b.status = 0 ")
    long size();

    @Query("select month(b.date) as month , count (*) as count from  Book  b WHERE b.status = 1 and  YEAR(b.date) = YEAR(CURRENT_DATE) GROUP BY MONTH(b.date) ORDER BY MONTH(b.date) ")
    List<Object[]> getTotalBookByMonth();

    @Query("select b.status as month , count (*) as count from  Book  b WHERE  YEAR(b.date) = YEAR(CURRENT_DATE) GROUP BY b.status ORDER BY b.status ")
    List<Object[]> getBookByYear();
}
