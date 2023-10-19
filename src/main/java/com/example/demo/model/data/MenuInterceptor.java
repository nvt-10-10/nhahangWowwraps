package com.example.demo.model.data;


import com.example.demo.model.data.MenuItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;
@Component
public class MenuInterceptor implements HandlerInterceptor {

    private static final String MENU_SESSION_KEY = "menuItems";
    private static final String MENUADMIN_SESSION_KEY = "menuAdminItems";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Khởi tạo danh sách MenuItem và thêm các mục menu vào danh sách
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Home", "/"));
        menuItems.add(new MenuItem("Menu", "/menu"));
        menuItems.add(new MenuItem("Blog", "#!"));
        menuItems.add(new MenuItem("Pages", "#!"));
        menuItems.add(new MenuItem("Contact", "#!"));

        // Lưu danh sách MenuItem vào session
        HttpSession session = request.getSession();
        session.setAttribute(MENU_SESSION_KEY, menuItems);
        session.setMaxInactiveInterval(60*60*24*31*12);

        List<MenuItem> menuItems1 = new ArrayList<>();
        menuItems1.add(new MenuItem("Home", "admin"));
        menuItems1.add(new MenuItem("Chef", "chef"));
        menuItems1.add(new MenuItem("Tag", "tag"));
        menuItems1.add(new MenuItem("Category", "category"));
        menuItems1.add(new MenuItem("Product", "product"));
        menuItems1.add(new MenuItem("Book", "book"));
        menuItems1.add(new MenuItem("Order", "order"));
        menuItems1.add(new MenuItem("Statistical", "statistical"));

        session.setAttribute(MENUADMIN_SESSION_KEY, menuItems1);
        session.setMaxInactiveInterval(60*60*24*31*12);
        return true;
    }
}