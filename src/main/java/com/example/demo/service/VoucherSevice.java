package com.example.demo.service;

import com.example.demo.model.entity.Voucher;
import com.example.demo.reponsitory.VoucherReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherSevice {
    private  final VoucherReponsitory repo;
    @Autowired
    public VoucherSevice(VoucherReponsitory repo){
        this.repo= repo;
    }
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

    public Voucher save(Voucher voucher){
        return  repo.save(voucher);
    }
    public List<Voucher> ListALl() {
        return (List<Voucher>) repo.findAll();
    }


    public Voucher FindByID(Integer ID) {
        Optional<Voucher> categoryOptional = repo.findById(ID);
        return categoryOptional.orElse(null); // Trả về null nếu không tìm thấy đối tượng
    }

    public void AddVoucher(Voucher Voucher){
        repo.save(Voucher);
    }

    public void UpdateVoucher(Voucher Voucher){
        repo.save(Voucher);
    }
    public List<Voucher> findByKeywordInCodeReview(String code) {
        return  repo.findByKeywordInCodeReview(code);
    }

    public Integer CategorySize(){
        return  repo.SizeCategory();
    }

    public Voucher checkcode(String code) {
        return repo.checkCode(code);
    }

    public Voucher initVoucher(){
        String code="";
        do {
            code =generateRandomString(8);
        } while (repo.InitCode(code)>0);
        Voucher voucher = new Voucher();
        voucher.setCode(code.toUpperCase());
        voucher.setStatus(true);
        voucher.setValue(15F);
        voucher.setUnit("%");
        voucher.setEndDate();
        return save(voucher);
    }
}
