package br.com.gbessa.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService{

    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class); // Final para ficar 1 logger para todas as instâncias
    
    @Override
    public void sendEmail(SimpleMailMessage msg) {
	LOG.info("Simulando envio de Email");
	LOG.info(msg.toString());
	LOG.info("Email enviado");
    }
   
    @Override
    public void sendHtmlEmail(MimeMessage mm) {
	LOG.info("Simulando envio de Email em HTML");
	LOG.info(mm.toString());
	LOG.info("Email enviado");
    }

}
