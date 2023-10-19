package com.example.demo.reponsitory;

import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Chef;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface  CategoryReponsitory extends CrudRepository<Category,Integer> {
    @Query("SELECT c FROM Category c WHERE c.name LIKE %:keyword% OR c.id = :id ")
    List<Category> findByKeywordInNameCategory(@Param("keyword") String keyword, @Param("id") int id);
    @Query("SELECT c FROM Category c ORDER BY c.id DESC")
    Page<Category> findNewestCategories(Pageable pageable);
    @Query("SELECT count(c) FROM Category c ")
    Integer SizeCategory();

    @Query("SELECT count(c) FROM Category c  where  c.name=:name")
    Integer CheckName(@Param("name") String name);
    @Query("SELECT c FROM Category c ORDER BY c.id DESC")
    Page<Category> findCategoryByPage(Pageable pageable);

    @Query("SELECT count(c) FROM Category c WHERE c.id = :id OR c.name LIKE %:name%")
    long countByKeyWord( @Param("id") Integer id, @Param("name") String name);
    @Query("SELECT c FROM Category c WHERE c.id = :id OR c.name LIKE %:name%")
    Page<Category> findByPageAndIdOrName(Pageable pageable, @Param("id") Integer id, @Param("name") String name);

}
