package com.example.demo.service;

import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Chef;
import com.example.demo.model.entity.Tag;
import com.example.demo.reponsitory.TagReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagReponsitory repo;

    public void save(Tag Tag) {
        repo.save(Tag);
    }
    public void deleteByID(Integer id){
        repo.deleteById(id);
    }

    public Tag FindByID(Integer ID) {
        Optional<Tag> categoryOptional = repo.findById(ID);
        return categoryOptional.orElse(null); // Trả về null nếu không tìm thấy đối tượng
    }

    public List<Tag> findAll(){
        return (List<Tag>) repo.findAll();
    }

    public Page<Tag> findByPage(Pageable pageable) {
        return repo.findByPage(pageable);
    }

    public Tag GetByID(int id){
        return  repo.findById(id).get();
    }
    public Integer Size(){
        return  repo.Size();
    }

    public int countPage(){
        int page = Size()/10;
        System.out.println("page"+page);
        page=(repo.count()%10==0?page-1:page+1);
        System.out.println("page"+page);
        return page;
    }

    public boolean checkName(String name){
        if (repo.CheckName(name)==0)
            return true;
        return false;
    }

    public boolean isNumeric(String str) {
        return str.matches("-?\\d+"); // Kiểm tra chuỗi có toàn bộ là số (có thể có dấu trừ ở đầu)
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
    public List<Tag> findÁllTagByIdProduct(Integer id) {
        return repo.findÁllTagByIdProduct(id);
    }

    public Page<Tag> findByPageAndIdOrName(Pageable pageable ,String keyword) {
        if(isNumeric(keyword)){
            return repo.findByPageAndIdOrName(pageable,Integer.parseInt(keyword),keyword);
        }
        return repo.findByPageAndIdOrName(pageable,-1,keyword);
    }
}
