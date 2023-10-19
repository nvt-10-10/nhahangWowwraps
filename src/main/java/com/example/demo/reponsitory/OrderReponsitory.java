package com.example.demo.reponsitory;

import com.example.demo.model.data.Statistical;
import com.example.demo.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderReponsitory extends CrudRepository<Order,Integer> {
    @Query("SELECT  o, sum(o.intoMoney) as subtotal FROM Order o where o.status=1 group by o.email ORDER BY o.id DESC")
    Page<Order> find10OrderTotals(Pageable pageable);

    @Query("SELECT o FROM Order o where o.status = 0 ORDER BY o.id DESC ")
    Page<Order> findByPage(Pageable pageable);

    @Query("SELECT o FROM Order o WHERE (o.id = :id OR concat(o.lastName,'',o.firstName)  LIKE %:name%) AND o.status = 0 ORDER BY o.id DESC")
    Page<Order> findByPageAndIdOrName(Pageable pageable, @Param("id") Integer id, @Param("name") String name);

    @Query("SELECT count(o) FROM Order o WHERE (o.id = :id OR concat(o.lastName,'',o.firstName)  LIKE %:name%) AND o.status = 0 ")
    long countByKeyWord( @Param("id") Integer id, @Param("name") String name);


    @Query("SELECT count(b) FROM Book b WHERE b.status = 0 ")
    long size();
    @Query("SELECT  MONTH(o.date) as month, SUM(o.intoMoney) as total \n" +
            "FROM Order o\n" +
            "WHERE YEAR(o.date) = YEAR(CURRENT_DATE) and o.status=1\n" +
            "GROUP BY MONTH(o.date)\n" +
            "ORDER BY MONTH(o.date)")
    List<Object[]> getTotalRevenueByMonth();

    @Query("SELECT  o.status as month, count (*) as count1 \n" +
            "FROM Order o\n" +
            "WHERE YEAR(o.date) = YEAR(CURRENT_DATE)\n" +
            " group by  o.status")
    List<Object[]> getOrderByYear();

}
