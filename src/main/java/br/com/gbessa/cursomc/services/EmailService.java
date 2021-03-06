package br.com.gbessa.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.gbessa.cursomc.domain.Cliente;
import br.com.gbessa.cursomc.domain.Pedido;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido obj);
    
    void sendEmail(SimpleMailMessage msg);
    
    void sendOrderConfirmationHtmlEmail(Pedido obj);
    
    void sendHtmlEmail(MimeMessage msg);

    void sendNewPassawordEmail(Cliente cliente, String newPass);
    
}
