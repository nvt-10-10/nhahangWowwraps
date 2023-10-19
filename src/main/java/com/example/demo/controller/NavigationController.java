package com.example.demo.controller;

import com.example.demo.model.data.Statistical;
import com.example.demo.model.entity.*;
import com.example.demo.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NavigationController {
    private final CategoryService categoryService;
    private final ProductService productService;

    private final OrderService orderService;

    private final ChefService chefService;

    private final TagService tagService;

    private final ReviewService reviewService;

    private final BookService bookService;



    @Autowired
    public NavigationController(ProductService productService,
                                TagService tagService,
                                ReviewService reviewService,
                                ChefService chefService,
                                CategoryService categoryService,
                                OrderService orderService,
                                BookService bookService

    ) {
        this.productService = productService;
        this.tagService = tagService;
        this.reviewService = reviewService;
        this.chefService = chefService;
        this.categoryService = categoryService;
        this.orderService = orderService;
        this.bookService=bookService;
    }

    @GetMapping("/client")
    public String client(){

        return "client";
    }

    @GetMapping("")
    public String ShowHomePage(Model model, HttpSession session) {


        List<Category> categories = categoryService.ListALl();
        model.addAttribute("categories", categories);
        List<Product> products = productService.ListALl();
        model.addAttribute("products", products);
        List<Product> productSpecial = productService.show5productSpecialDish().getContent();
        model.addAttribute("prodcutSpecials", productSpecial);
        List<Product> new3Product = productService.show3NewProduct().getContent();
        model.addAttribute("new3Products", new3Product);
        List<Chef> chefs = chefService.ListALl();
        model.addAttribute("chefs", chefs);
        Book book = new Book();
        book.setNumberPerson(1);
        Subscribe subscribe = new Subscribe();
        String pageUrl = "Home";
        model.addAttribute("pageUrl", pageUrl);
        model.addAttribute("book", book);
        model.addAttribute("subscribe", subscribe);
        return "index";
    }


    @PostMapping("/checkout")
    public String CheckOutPage(@RequestParam("textVoucher") String textvoucher,
                               @RequestParam("subtotal") String Subtotal,
                               @RequestParam("totals") String totals,
                               @RequestParam("idVoucher") String idVoucher,
                               HttpSession session,
                               Model model) {
        Order order = new Order();
        order.setAddress((String) session.getAttribute("address"));
        model.addAttribute("order", order);

        model.addAttribute("idVoucher", idVoucher);
        model.addAttribute("textvoucher", textvoucher);
        model.addAttribute("subtotal", Subtotal);
        model.addAttribute("totals", totals);
        System.out.println("voucher+" + textvoucher == null);
        return "checkout";
    }

    @GetMapping("/productDetails/{id}")
    public String ShowProductDetailPage(@PathVariable("id") Integer id, Model model, HttpSession session) {
        Product product = productService.FindByID(id);
        model.addAttribute("product", product);
        List<Tag> tags = tagService.findÁllTagByIdProduct(id);
        int countReview = reviewService.SizeReviewByProduct(id);
        int star = 0;
        if (reviewService.star(id) != null)
            star = (int) Math.floor(reviewService.star(id));
        List<Review> reviews = reviewService.find10ReviewByID_Product(id);
        List<Product> products = productService.find3ByID_Category(product.getCategory().getId()).getContent();
        model.addAttribute("products", products);
        String str_tag = "";
        int tagCount = tags.size();
        for (int i = 0; i < tagCount; i++) {
            str_tag += tags.get(i).name + (i < tagCount - 1 ? ", " : " ");
        }

        Review review = (Review) session.getAttribute("userReview");
        if (review == null) {
            review = new Review();
            session.setAttribute("userReview", review);
        }

        model.addAttribute("tags", str_tag);
        model.addAttribute("star", star);
        model.addAttribute("countReview", countReview);
        model.addAttribute("reviews", reviews);
        return "productDetail";
    }

    @GetMapping("/menu")
    public String showMenuPage(Model model) {
        List<Product> BreakfastProducts = productService.FindByIMeal(1);
        List<Product> LunchProducts = productService.FindByIMeal(2);
        List<Product> DinnerProducts = productService.FindByIMeal(3);
        List<Product> StartersProducts = productService.FindByIMeal(4);
        model.addAttribute("BreakfastProducts", BreakfastProducts);
        model.addAttribute("LunchProducts", LunchProducts);
        model.addAttribute("DinnerProducts", DinnerProducts);
        model.addAttribute("StartersProducts", StartersProducts);
        String pageUrl = "Menu";
        model.addAttribute("pageUrl", pageUrl);
        return "menu";
    }


    @GetMapping("/admin")
    public String ShowHomePageAdmin(Model model) {
        var orders = orderService.find10OrderTotals().getContent();
        model.addAttribute("orders", orders);
        String pageUrl = "Home";
        model.addAttribute("pageUrl", pageUrl);
        return "admin/Index";
    }

    @GetMapping("/chef")
    public String showAdminChef(Model model, HttpServletResponse response) {
        Cookie cookie = new Cookie("keyword", null);
        cookie.setMaxAge(3600); // Thời gian sống của cookie (giây)
        cookie.setPath("/adminChef"); // Đường dẫn sẽ có thể truy cập cookie
        response.addCookie(cookie);
        Pageable page = PageRequest.of(0,10);
        List<Chef> chefs = chefService.findProductByPage(page).getContent();
        int pageCount = chefService.countPage();
        Chef chef = new Chef();
        model.addAttribute("chef",chef);
        model.addAttribute("chefs",chefs);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("indexPage" ,0);
        String pageUrl = "Chef";
        model.addAttribute("pageUrl", pageUrl);
       return "admin/chef";
    }

    @GetMapping("category")
    public String showCategory(Model model, HttpServletResponse response) {
        Cookie cookie = new Cookie("keyword", null);
        cookie.setMaxAge(3600); // Thời gian sống của cookie (giây)
        cookie.setPath("/category"); // Đường dẫn sẽ có thể truy cập cookie
        response.addCookie(cookie);
        Pageable page = PageRequest.of(0,10);
        List<Category> categories = categoryService.findNewestCategories(page).getContent();
        int pageCount = categoryService.countPage();
        Category category = new Category();
        model.addAttribute("category",category);
        model.addAttribute("categories",categories);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("indexPage" ,0);
        String pageUrl = "Category";
        model.addAttribute("pageUrl", pageUrl);
        return "admin/category";
    }

    @GetMapping("tag")
    public String showTag(Model model, HttpServletResponse response) {
        Cookie cookie = new Cookie("keyword", null);
        cookie.setMaxAge(3600); // Thời gian sống của cookie (giây)
        cookie.setPath("/tag"); // Đường dẫn sẽ có thể truy cập cookie
        response.addCookie(cookie);
        Pageable page = PageRequest.of(0,10);
        List<Tag> tags = tagService.findAll();

        int pageCount = tagService.countPage();
        Tag tag = new Tag();
        model.addAttribute("tag",tag);
        model.addAttribute("tags",tags);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("indexPage" ,0);
        String pageUrl = "Tag";
        model.addAttribute("pageUrl", pageUrl);
        return "admin/tag";
    }
    @GetMapping("book")
    public String showBook(Model model, HttpServletResponse response) {
        Cookie cookie = new Cookie("keyword", null);
        cookie.setMaxAge(3600); // Thời gian sống của cookie (giây)
        cookie.setPath("/book"); // Đường dẫn sẽ có thể truy cập cookie
        response.addCookie(cookie);
        Pageable page = PageRequest.of(0,10);
        List<Book> books = bookService.findByPage(page).getContent();
        int pageCount = bookService.countPage();
        Book book = new Book();
        model.addAttribute("book",book);
        model.addAttribute("books",books);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("indexPage" ,0);
        String pageUrl = "Book";
        model.addAttribute("pageUrl", pageUrl);
        return "admin/book";
    }

    @GetMapping("order")
    public String showOrder(Model model, HttpServletResponse response) {
        Cookie cookie = new Cookie("keyword", null);
        cookie.setMaxAge(3600); // Thời gian sống của cookie (giây)
        cookie.setPath("/order"); // Đường dẫn sẽ có thể truy cập cookie
        response.addCookie(cookie);
        Pageable page = PageRequest.of(0,10);
        List<Order> orders = orderService.findByPage(page).getContent();
        int pageCount = orderService.countPage();
        Order order = new Order();
        model.addAttribute("order",order);
        model.addAttribute("orders",orders);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("indexPage" ,0);
        String pageUrl = "Order";
        model.addAttribute("pageUrl", pageUrl);
        return "admin/order";
    }

    @GetMapping("product")
    public String showProduct(Model model, HttpServletResponse response, HttpServletRequest request) {

        Cookie cookie = new Cookie("keyword", null);
        cookie.setMaxAge(3600); // Thời gian sống của cookie (giây)
        cookie.setPath("/product"); // Đường dẫn sẽ có thể truy cập cookie
        response.addCookie(cookie);
        Pageable page = PageRequest.of(0,10);
        List<Product> products = productService.findByPage(page).getContent();
        List<Tag> tags = tagService.findByPage(page).getContent();
        List<Category> categories = categoryService.ListALl();
        int pageCount = productService.countPage();
        Product product = new Product();
        model.addAttribute("product",product);
        model.addAttribute("products",products);
        model.addAttribute("tags",tags);
        model.addAttribute("categories",categories);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("indexPage" ,0);
        String pageUrl = "Product";
        model.addAttribute("pageUrl", pageUrl);
        return "admin/product";
    }

    @GetMapping("statistical")
    public String showProduct(Model model) {
        String pageUrl = "Statistical";
        model.addAttribute("pageUrl", pageUrl);
        return "admin/statistical";
    }


//    @GetMapping("/adminCatagory")
//    public String showAdminChef(Model model) {
//        Pageable page = PageRequest.of(0,10);
//        List<Chef> chefs = chefService.findProductByPage(page).getContent();
//        int pageCount = chefService.countPage();
//        Chef chef = new Chef();
//        model.addAttribute("chef",chef);
//        model.addAttribute("chefs",chefs);
//        model.addAttribute("pageCount",pageCount);
//        model.addAttribute("indexPage" ,0);
//        return "Chef";
//    }
}
