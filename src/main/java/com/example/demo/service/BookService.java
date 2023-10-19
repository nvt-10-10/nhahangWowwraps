package com.example.demo.service;

import com.example.demo.model.entity.Book;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Chef;
import com.example.demo.reponsitory.BookRepomsitory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepomsitory repo;


    public BookService(BookRepomsitory repo) {
        this.repo = repo;
    }

    public Book Save(Book book) {
        return repo.save(book);
    }

    public List<Book> findAll() {
        return (List<Book>) repo.findAll();
    }


    public Book FindByID(Integer id) {
        Optional<Book> optionalBook = repo.findById(id);
        return optionalBook.orElse(null); // Trả về null nếu không tìm thấy đối tượng
    }

    public Page<Book> findByPage(Pageable pageable) {
        return repo.findByPage(pageable);
    }

    public boolean isNumeric(String str) {
        return str.matches("-?\\d+"); // Kiểm tra chuỗi có toàn bộ là số (có thể có dấu trừ ở đầu)
    }

    public Page<Book> findProductByPageAndIdOrName(Pageable pageable, String keyword) {
        if (isNumeric(keyword)) {
            return repo.findByPageAndIdOrName(pageable, Integer.parseInt(keyword), keyword);
        }
        return repo.findByPageAndIdOrName(pageable, -1, keyword);
    }

    public int size(){
        return (int)repo.size();
    }

    public int countPage() {
        int page = (int) (repo.size() / 10);
        return (repo.size() % 10 == 0 ? page : page + 1);
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

    public void deleteById(int id) {
        repo.deleteById(id);
    }

}
