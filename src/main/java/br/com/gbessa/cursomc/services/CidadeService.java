package br.com.gbessa.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gbessa.cursomc.domain.Cidade;
import br.com.gbessa.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {

    @Autowired
    CidadeRepository repo;
    
    public List<Cidade> findByEstado(Integer estadoId) {
	return repo.findCidades(estadoId);
    }
    
}
