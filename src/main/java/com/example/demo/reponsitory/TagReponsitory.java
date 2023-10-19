package com.example.demo.reponsitory;


import com.example.demo.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagReponsitory extends CrudRepository<Tag,Integer> {
    @Query("SELECT t  From Tag t inner join ProductTag pt on pt.tag.id = t.id where pt.product.id= :id")
    List<Tag> findÁllTagByIdProduct(@Param("id") Integer id);
    @Query("SELECT t FROM Tag t order by  t.id desc ")
    Page<Tag> findByPage(Pageable pageable);
    @Query("SELECT count(t) FROM Tag t  where  t.name=:name")
    Integer CheckName(@Param("name") String name);
    @Query("SELECT count(t) FROM Tag t WHERE t.id = :id OR t.name LIKE %:name%")
    long countByKeyWord( @Param("id") Integer id, @Param("name") String name);
    @Query("SELECT t FROM Tag t WHERE t.id = :id OR t.name LIKE %:name%")
    Page<Tag> findByPageAndIdOrName(Pageable pageable, @Param("id") Integer id, @Param("name") String name);
    @Query("select count(p) from Tag  p")
    Integer Size();

}
