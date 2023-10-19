package com.example.demo.service;

import com.example.demo.model.entity.Chef;
import com.example.demo.model.entity.PriceHistory;
import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.Tag;
import com.example.demo.reponsitory.ProductReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "products")
public class ProductService {

    private final ProductReponsitory repo;
    private final PriceHistoryService priceHistoryService;


    @Autowired
    public ProductService(ProductReponsitory ProductReponsitory, PriceHistoryService priceHistoryService) {
        this.repo = ProductReponsitory;
        this.priceHistoryService = priceHistoryService;
    }

    public List<Product> ListALl() {
        return repo.getALl();
    }

    public List<Product> FindByIDCategory(Integer ID) {
        return (List<Product>) repo.findAllByID_Category(ID);
    }

    public List<Product> FindByIMeal(Integer Meal) {
        return (List<Product>) repo.findAllByID_Meal(Meal);
    }

    public Product FindByID(Integer ID) {
        Optional<Product> categoryOptional = repo.findById(ID);
        return categoryOptional.orElse(null); // Trả về null nếu không tìm thấy đối tượng
    }

    public void save(Product product) {
        Integer id = product.getId();
        if (id != 0) {
            Product product1 = repo.findById(id).get();
            if (product1.getPrice()!=product.getPrice()){
                PriceHistory initialPriceHistory = new PriceHistory();
                initialPriceHistory.setProduct(repo.findById(id).get());
                initialPriceHistory.setPrice(product.getPrice());
                priceHistoryService.save(initialPriceHistory);
            }
        }
        repo.save(product);
    }

    public Page<Product> findNewestProduct(Pageable pageable) {
        return repo.findNewestProduct(pageable);
    }

    public Page<Product> show5productSpecialDish() {
        Pageable page = PageRequest.of(0, 5);
        return repo.show5productSpecialDish(page);
    }

    public Page<Product> show3NewProduct() {
        Pageable page = PageRequest.of(0, 3);
        return repo.show5productSpecialDish(page);
    }

    public Page<Product> find3ByID_Category(Integer id) {
        Pageable page = PageRequest.of(0, 3);
        return repo.find3ByID_Category(id, page);
    }

    public Integer SizeOrder() {
        return repo.SizeProduct();
    }

    public Integer getQuantityByID(Integer id) {
        return repo.getQuantityByID(id);
    }

    public Page<Product> findByPage(Pageable pageable) {
        return repo.findByPage(pageable);
    }

    public void deleteById(int id) {
        repo.deleteById(id);
    }

    public Integer Size() {
        return repo.Size();
    }

    public int countPage() {
        int page = Size() / 10;
        System.out.println("page" + page);
        page = (repo.count() % 10 == 0 ? page : page + 1);
        System.out.println("page" + page);
        return page;
    }

    public boolean isNumeric(String str) {
        return str.matches("-?\\d+"); // Kiểm tra chuỗi có toàn bộ là số (có thể có dấu trừ ở đầu)
    }

    public Page<Product> findProductByPageAndIdOrName(Pageable pageable, String keyword) {
        if (isNumeric(keyword)) {
            return repo.findByPageAndIdOrName(pageable, Integer.parseInt(keyword), keyword);
        }
        return repo.findByPageAndIdOrName(pageable, -1, keyword);
    }

    public int countByKeyWord(String keyword) {
        int numericValue = isNumeric(keyword) ? Integer.parseInt(keyword) : -1;
        long totalCount = repo.countByKeyWord(numericValue, keyword);
        int totalPages = (int) (totalCount / 10);
        System.out.println("totalCount: " + totalCount);
        if (totalCount % 10 != 0) {
            totalPages++;
        }
        System.out.println("totalPages: " + totalPages);

        return totalPages;
    }




}
