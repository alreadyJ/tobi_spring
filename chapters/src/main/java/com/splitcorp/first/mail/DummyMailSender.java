package com.splitcorp.first.mail;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class DummyMailSender implements MailSender {
    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void send(SimpleMailMessage simpleMailMessage) throws MailException {

    }

    public void send(SimpleMailMessage[] simpleMailMessages) throws MailException {

    }
}
