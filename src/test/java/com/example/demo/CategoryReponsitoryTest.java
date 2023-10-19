package com.example.demo;

import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.Review;
import com.example.demo.model.entity.Ward;
import com.example.demo.reponsitory.*;
import com.example.demo.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class CategoryReponsitoryTest {

    @Autowired
    private CategoryReponsitory repo;
    @Autowired
    private ProductReponsitory reponsitory;
    @Autowired
    private WardReponsitory wardReponsitory;
    @Autowired
    private ReviewReponsitory reviewReponsitory;
    @Autowired
    private ChefReponsitory chefReponsitory;

    @Test
    public void testAddCategory() {
chefReponsitory.deleteById(182);
//        for (int i = 4; i < 10; i++) {
//            Category category = new Category();
//            category.setName("a" + i);
//            Category saveCategory = repo.save(category);
//        }
//        System.out.println(reviewReponsitory.star(5));
//        Pageable page = PageRequest.of(0, 10);
//        reviewReponsitory.findAllByID_Product(6, page);
//        for (Review r : reviewReponsitory.findAll()
//        ) {
//            System.out.println(r.getId());
//
//        }
//        System.out.println(reviewReponsitory.findAllByID_Product(6, page).size());
    }


//    @Test
//    public void TestSize() {
//        List<st>
//
//    }
}
