package br.com.gbessa.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class); 
    
    @Autowired
    private MailSender mailSender;
   
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Override
    public void sendEmail(SimpleMailMessage msg) {
	LOG.info("Realizando envio de Email - Pelo GMAIL (ATENÇÃO!!)");
	mailSender.send(msg);
	LOG.info("Email enviado");
	
    }

    @Override
    public void sendHtmlEmail(MimeMessage mm) {
	LOG.info("Realizando envio de Email em HTML - Pelo GMAIL (ATENÇÃO!!)");
	javaMailSender.send(mm);
	LOG.info("Email enviado");
	
    }

}
