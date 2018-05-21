package br.com.gbessa.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.gbessa.cursomc.domain.Pedido;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido obj);
    
    void sendEmail(SimpleMailMessage msg);
    
}
