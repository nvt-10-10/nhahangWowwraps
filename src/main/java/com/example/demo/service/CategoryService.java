package com.example.demo.service;

import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Chef;
import com.example.demo.reponsitory.CategoryReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryReponsitory repo;

    @Autowired
    public CategoryService(CategoryReponsitory categoryRepository) {
        this.repo = categoryRepository;
    }

    public void save(Category category){
        repo.save(category);
    }
    public void deleteByID(Integer id){
        repo.deleteById(id);
    }
    public Category GetByID(int id){
        return  repo.findById(id).get();
    }

    public List<Category> ListALl() {
        return (List<Category>) repo.findAll();
    }

    public List<Category> findByKeywordInNameCategory(String key) {
        if (key.matches("-?\\d+")) {
            // Nếu key là số, thực hiện tìm kiếm theo ID_Category
            return repo.findByKeywordInNameCategory("", Integer.parseInt(key));
        } else {
            // Nếu key không phải là số, thực hiện tìm kiếm theo name_Category
            return repo.findByKeywordInNameCategory(key, -1);
        }
    }

    public Page<Category> findProductByPage(Pageable pageable) {
        return repo.findCategoryByPage(pageable);
    }

    public Category FindByID(Integer ID) {
        Optional<Category> categoryOptional = repo.findById(ID);
        return categoryOptional.orElse(null); // Trả về null nếu không tìm thấy đối tượng
    }

    public boolean checkName(String name){
        if (repo.CheckName(name)==0)
            return true;
        return false;
    }

    public void AddCategory(Category category){
        repo.save(category);
    }

    public void UpdateCategory(Category category){
        repo.save(category);

    }
    public Page<Category> findNewestCategories(Pageable pageable) {
        return repo.findNewestCategories(pageable);
    }

    public Integer CategorySize(){
        return  repo.SizeCategory();
    }

    public int countPage(){
        int page = (int) (CategorySize()/10);
        return (repo.count()%10==0?page:page+1);
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

    public Page<Category> findByPageAndIdOrName(Pageable pageable ,String keyword) {
        if(isNumeric(keyword)){
            return repo.findByPageAndIdOrName(pageable,Integer.parseInt(keyword),keyword);
        }
        return repo.findByPageAndIdOrName(pageable,-1,keyword);
    }
}
