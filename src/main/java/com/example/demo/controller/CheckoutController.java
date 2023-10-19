package com.example.demo.controller;

import com.example.demo.model.entity.*;
import com.example.demo.service.*;
import com.example.demo.service.impl.ClientServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CheckoutController {
    private final VoucherSevice voucherSevice;
    private final OrderService orderService;

    private final OrderDetailService orderDetailService;

    private final ClientServiceImpl clientService;
    private final ProductService productService;

    //    private Or
    @Autowired
    public CheckoutController(VoucherSevice voucherSevice, OrderService orderService, OrderDetailService orderDetailService, ProductService productService, ClientServiceImpl clientService) {
        this.orderService = orderService;
        this.voucherSevice = voucherSevice;
        this.orderDetailService = orderDetailService;
        this.productService = productService;
        this.clientService = clientService;
    }


    @PostMapping("/addOrder")
    public String addOder(Order order, @RequestParam("idVoucher") String idVoucher, HttpSession session, HttpServletResponse response) {

        List<Cart> carts = (List<Cart>) session.getAttribute("cartItems");
        double total = 0;
        for (Cart cart : carts
        ) {
            total += cart.getSubtotal();
        }
        if (idVoucher != null && idVoucher.length() > 0) {
            Voucher voucher = voucherSevice.FindByID(Integer.parseInt(idVoucher));
            if (voucher.getUnit().equals("%")) {
                total -= total * voucher.getValue() / 100;
            } else {
                total -= total * voucher.getValue();
            }
            order.setVoucher(voucher);

        }
        order.setIntoMoney(total);
        Order order1 = orderService.save(order);
        for (Cart cart : carts
        ) {
            Product product = cart.getProduct();
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product);
            orderDetail.setQuantity(cart.getQuantity());
            orderDetail.setOrder(order1);
            product.setQuantity(product.getQuantity() - cart.getQuantity());
            productService.save(product);
            orderDetailService.AddOrder_Detail(orderDetail);
        }

        session.removeAttribute("cartItems");
        Cookie cookie = new Cookie("updateDataListOrder", "true");
        cookie.setMaxAge(5); // Thời gian sống của cookie (giây)
        cookie.setPath("/order"); // Đường dẫn sẽ có thể truy cập cookie
        response.addCookie(cookie);
        return "redirect:/menu";
    }
}
