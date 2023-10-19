package com.example.demo.reponsitory;

import com.example.demo.model.entity.Chef;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ChefReponsitory extends CrudRepository<Chef,Integer> {
    @Query("SELECT c FROM Chef c ORDER BY c.id DESC")
    Page<Chef> findProductByPage(Pageable pageable);

    @Query("select count(c) FROM Chef c")
    long count();

    @Query("SELECT c FROM Chef c WHERE c.id = :id OR c.name LIKE %:name%")
    Page<Chef> findByPageAndIdOrName(Pageable pageable, @Param("id") Integer id, @Param("name") String name);

    @Query("SELECT count(c) FROM Chef c WHERE c.id = :id OR c.name LIKE %:name%")
    long countByKeyWord( @Param("id") Integer id, @Param("name") String name);

}
