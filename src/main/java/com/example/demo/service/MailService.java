package com.example.demo.service;

import com.example.demo.model.data.DataMail;
import jakarta.mail.MessagingException;

public interface MailService {
    void SendHtmlMailOrder(DataMail dataMail,String templateName) throws MessagingException;
    void SendHtmlMailSubscribe(DataMail dataMail,String templateName) throws MessagingException;
    void SendHtmlMailBook(DataMail dataMail,String templateName) throws MessagingException;
    void SendHtmlMailBookCancel(DataMail dataMail,String templateName) throws MessagingException;

}
