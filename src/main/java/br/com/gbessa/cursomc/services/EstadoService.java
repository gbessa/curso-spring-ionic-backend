package br.com.gbessa.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gbessa.cursomc.domain.Estado;
import br.com.gbessa.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {

    @Autowired
    EstadoRepository repo;
    
    public List<Estado> findAll() {
	return repo.findAllByOrderByNome();
    }
    
}
