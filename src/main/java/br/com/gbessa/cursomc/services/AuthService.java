package br.com.gbessa.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.gbessa.cursomc.domain.Cliente;
import br.com.gbessa.cursomc.repositories.ClienteRepository;
import br.com.gbessa.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    private Random rand = new Random();

    public void sendNewPassword(String email) {

	Cliente cliente = clienteRepository.findByEmail(email);
	if (cliente == null) {
	    throw new ObjectNotFoundException("Email não encontrado");
	}

	String newPass = newPassword();
	cliente.setSenha(bCryptPasswordEncoder.encode(newPass));

	clienteRepository.save(cliente);

	emailService.sendNewPassawordEmail(cliente, newPass);

    }

    private String newPassword() {
	char[] vet = new char[10];
	for (int i = 0; i < 10; i++) {
	    vet[i] = randomChar();
	}
	return new String(vet);
    }

    private char randomChar() {
	int opt = rand.nextInt(3);
	switch (opt) {
	case 0: // gera digito
	    return (char) (rand.nextInt(10) + 48);
	    
	case 1: // gera letra maiuscula
	    return (char) (rand.nextInt(26) + 25);
	    
	case 2: // gera letra maiuscula
	    return (char) (rand.nextInt(26) + 97);
	    
	default:
	    return (char) 0;	    
	}
    }

}
