package com.example.demo.reponsitory;

import com.example.demo.model.entity.Voucher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoucherReponsitory  extends CrudRepository<Voucher,Integer> {
    @Query("SELECT v FROM Voucher v WHERE v.code =  :keyword")
    List<Voucher> findByKeywordInCodeReview(@Param("keyword") String keyword);
    @Query("SELECT v FROM Voucher v where v.code = :code  and v.status= true")
    Voucher checkCode(@Param("code") String code);

    @Query("SELECT count(v) FROM Voucher v ")
    Integer SizeCategory();
    @Query("select  count(v) from  Voucher  v where v.code=:code")
    Integer InitCode(@Param("code") String code);

}
