package com.example.demo.reponsitory;

import com.example.demo.model.entity.Chef;
import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ProductReponsitory extends CrudRepository<Product, Integer> {
    @Query("SELECT p FROM Product p ORDER BY p.id DESC")
    Page<Product> findNewestProduct(Pageable pageable);

    @Query("SELECT p FROM Product p where p.specialDish = true ORDER BY p.id DESC")
    Page<Product> show5productSpecialDish(Pageable pageable);

    @Query("SELECT p FROM Product p where p.category.id=:ID and p.status=true ORDER BY p.id DESC")
    List<Product> findAllByID_Category(@Param("ID") Integer ID);

    @Query("SELECT p FROM Product p where p.category.id=:ID  and p.status=true ORDER BY p.id DESC")
    Page<Product> find3ByID_Category(@Param("ID") Integer ID, Pageable pageable);

    @Query("SELECT p FROM Product p where p.meal = :meal  and p.status=true ORDER BY p.id DESC")
    List<Product> findAllByID_Meal(@Param("meal") Integer meal);

    @Query("SELECT count(p) FROM Product p where   p.status=true")
    Integer SizeProduct();

    @Query("SELECT p.quantity FROM Product p where  p.id=:id")
    Integer getQuantityByID(@Param("id") Integer id);

    @Query("select p from Product  p where  p.status=true ")
    List<Product> getALl();

    @Query("SELECT p FROM Product p where p.status=true order by  p.id desc ")
    Page<Product> findByPage(Pageable pageable);
    @Query("select count(p) from Product  p")
    Integer Size();

    @Query("SELECT p FROM Product p WHERE p.id = :id OR p.name LIKE %:name%")
    Page<Product> findByPageAndIdOrName(Pageable pageable, @Param("id") Integer id, @Param("name") String name);
    @Query("SELECT count(p) FROM Product p WHERE p.id = :id OR p.name LIKE %:name%")
    long countByKeyWord( @Param("id") Integer id, @Param("name") String name);

}
