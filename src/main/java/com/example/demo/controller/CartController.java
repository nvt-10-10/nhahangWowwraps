package com.example.demo.controller;

import com.example.demo.model.entity.Cart;
import com.example.demo.model.entity.Product;
import com.example.demo.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    private static final String CART_SESSION_KEY = "cartItems";
    private static final String AddressCART_SESSION_KEY = "addressCart";
    private ProductService productService;

    @Autowired
    public CartController(ProductService productService) {
        this.productService = productService;
    }

    private List<Cart> cartItems = new ArrayList<>(); // Giả sử chúng ta sử dụng một danh sách đơn giản để lưu các sản phẩm trong giỏ hàng.

    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
        cartItems = (List<Cart>) session.getAttribute(CART_SESSION_KEY);
        float totals = 0;
        if (cartItems != null) {
            for (int i = 0; i < cartItems.size(); i++) {
                totals += cartItems.get(i).getSubtotal();
            }
        }
        NumberFormat format = new DecimalFormat("$ #,##0.00");

        model.addAttribute("carts", cartItems);
        model.addAttribute("totals", format.format(totals));
        model.addAttribute("total", totals);
        return "cartPage";
    }

    @PostMapping("/addcart")
    public ResponseEntity<String> addToCart(@RequestParam("idProduct") int productId,
                                            @RequestParam("quantity") int quantity,
                                            HttpSession session) {
        // Lấy sản phẩm từ cơ sở dữ liệu bằng productId (giả sử bạn có hàm để lấy sản phẩm từ cơ sở dữ liệu)
        Product product = productService.FindByID(productId);
        if (product == null) {
            // Xử lý trường hợp sản phẩm không tồn tại
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        // Lấy danh sách giỏ hàng hiện tại từ session
        List<Cart> cartItems = (List<Cart>) session.getAttribute(CART_SESSION_KEY);
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        } else {
            System.out.println(cartItems.size());

        }

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        boolean productExistsInCart = false;
        for (Cart cartItem : cartItems) {
            if (cartItem.getProduct().getId() == product.getId()) {
                // Sản phẩm đã tồn tại trong giỏ hàng, chỉ cập nhật số lượng
                cartItem.setQuantity(quantity);
                productExistsInCart = true;
                break;
            }
        }

        if (!productExistsInCart) {
            // Nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm sản phẩm mới vào giỏ hàng
            Cart newCartItem = new Cart();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            cartItems.add(newCartItem);
        }

        // Lưu lại danh sách giỏ hàng mới vào session
        session.setAttribute(CART_SESSION_KEY, cartItems);
        session.setMaxInactiveInterval(60 * 60 * 24 * 31 * 3);
        return ResponseEntity.ok("Product added to cart successfully");
    }


    @PostMapping("/deletecart/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable("id") Integer id, HttpSession session) {
        cartItems = (List<Cart>) session.getAttribute(CART_SESSION_KEY);
        int i = 0;
        for (Cart item : cartItems) {
            if (item.getProduct().getId() == id) {
                cartItems.remove(i);
                break;
            }
            i++;
        }
        return ResponseEntity.ok("Product removed from cart successfully");
    }

    @PostMapping("/updatecart")
    public ResponseEntity<String> updateCart(@RequestParam("productIds") List<Integer> productIds,
                                             @RequestParam("quantities") List<Integer> quantities, HttpSession session) {
        System.out.println("da gou");
        cartItems = (List<Cart>) session.getAttribute(CART_SESSION_KEY);
        int i = 0;
        for (Cart item : cartItems) {
            if (item.getProduct().getId() == productIds.get(i)) {
                cartItems.get(i).setQuantity(quantities.get(i));
            }
            i++;
        }
        return ResponseEntity.ok("Product update from cart successfully");
    }

    @PostMapping("/update-address")
    public ResponseEntity<String> updateAddress(HttpServletRequest request,HttpSession session) {
        // Lấy dữ liệu từ request
        String address = request.getParameter("address");
        session.setAttribute("address",address);
        return ResponseEntity.ok("update thanh cong");
    }

}
