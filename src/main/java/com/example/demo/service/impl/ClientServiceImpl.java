package com.example.demo.service.impl;

import com.example.demo.model.data.DataMail;
import com.example.demo.model.entity.*;
import com.example.demo.service.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {

    private final MailService mailService;

    private final OrderDetailService orderDetailService ;
    private final VoucherSevice voucherSevice;
    private final BookService bookService;

    @Autowired
    public ClientServiceImpl(MailService mailService,OrderDetailService orderDetailService,VoucherSevice voucherSevice,BookService bookService) {
        this.mailService = mailService;
        this.orderDetailService = orderDetailService;
        this.voucherSevice = voucherSevice;
        this.bookService =bookService;

    }

    @Override
    public void createOrder(Order order) {
        try {
            List<OrderDetail> orderDetailList = orderDetailService.ListALlFindByIdOrder(order);
            double total = 0;
            for (OrderDetail o : orderDetailList){
                total+=o.getQuantity()*o.getProduct().getPriceFloat();
            }
            DataMail dataMail = new DataMail();
            dataMail.setTo(order.getEmail());
            Map<String,Object> props = new HashMap<>();
            props.put("order", order);
            props.put("orderDetailList", orderDetailList);
            props.put("total", total);
            dataMail.setProps(props);
            mailService.SendHtmlMailOrder(dataMail, "/email/order");


        } catch (MessagingException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void createSubscribe(Subscribe subscribe) {
        try {
            Voucher voucher = null ;
            voucher=voucherSevice.initVoucher();
            DataMail dataMail = new DataMail();
            dataMail.setTo(subscribe.getEmail());
            Map<String,Object> props = new HashMap<>();
            props.put("subscribe", subscribe);
            props.put("voucher", voucher);
            dataMail.setProps(props);
            mailService.SendHtmlMailSubscribe(dataMail, "/email/subscribe");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void createBook(Book book) {
        try {
            System.out.println(book.getName());
            DataMail dataMail = new DataMail();
            dataMail.setTo(book.getEmail());
            Map<String,Object> props = new HashMap<>();
            props.put("book", book);
            dataMail.setProps(props);
            mailService.SendHtmlMailBook(dataMail, "/email/book");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void createBookCancel(Book book) {
        try {
            System.out.println(book.getName());
            DataMail dataMail = new DataMail();
            dataMail.setTo(book.getEmail());
            Map<String,Object> props = new HashMap<>();
            props.put("book", book);
            dataMail.setProps(props);
            mailService.SendHtmlMailBookCancel(dataMail, "/email/bookCancel");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
