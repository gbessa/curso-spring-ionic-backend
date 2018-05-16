package br.com.gbessa.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gbessa.cursomc.domain.Categoria;
import br.com.gbessa.cursomc.repositories.CategoriaRepository;
import br.com.gbessa.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria buscar(Integer id) {
	Optional<Categoria> obj = repo.findById(id);
	return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }
    
    public Categoria insert(Categoria obj) {
	obj.setId(null); //Para garantir que o save vai inserir e não atualizar, caso algum id seja mandado
	return repo.save(obj);
    }

}
