package com.example.demo.service;

import com.example.demo.model.entity.Chef;
import com.example.demo.reponsitory.ChefReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ChefService {

    private ChefReponsitory repo;
    @Autowired
    public ChefService(ChefReponsitory ChefReponsitory){
        this.repo=ChefReponsitory;
    }

    public List<Chef> ListALl() {
        return (List<Chef>) repo.findAll();
    }

    public Optional<Chef> FindByID(Integer ID) {
        return  repo.findById(ID);

    }

    public void save(Chef Chef){
        repo.save(Chef);
    }

    public void deleteById(Integer id){
        System.out.println("id: "+id);
        repo.deleteById(id);
    }

    public Page<Chef> findProductByPage(Pageable pageable) {
        return repo.findProductByPage(pageable);
    }
    public boolean isNumeric(String str) {
        return str.matches("-?\\d+"); // Kiểm tra chuỗi có toàn bộ là số (có thể có dấu trừ ở đầu)
    }
    public Page<Chef> findProductByPageAndIdOrName(Pageable pageable ,String keyword) {
        if(isNumeric(keyword)){
            return repo.findByPageAndIdOrName(pageable,Integer.parseInt(keyword),keyword);
        }
        return repo.findByPageAndIdOrName(pageable,-1,keyword);
    }
    public int countPage(){
        int page = (int) (repo.count()/10);
        return (repo.count()%10==0?page:page+1);
    }
    public int countByKeyWord(String keyword) {
        int numericValue = isNumeric(keyword) ? Integer.parseInt(keyword) : -1;
        long totalCount = repo.countByKeyWord(numericValue, keyword);
        int totalPages = (int) (totalCount / 10);
        System.out.println("totalCount: "+totalCount);
        if (totalCount % 10 != 0) {
            totalPages++;
        }
        System.out.println("totalPages: "+totalPages);

        return totalPages;
    }

}
