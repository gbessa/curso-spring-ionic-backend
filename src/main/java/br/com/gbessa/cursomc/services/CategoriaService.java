package br.com.gbessa.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.gbessa.cursomc.domain.Categoria;
import br.com.gbessa.cursomc.dto.CategoriaDTO;
import br.com.gbessa.cursomc.repositories.CategoriaRepository;
import br.com.gbessa.cursomc.services.exceptions.DataIntegrityException;
import br.com.gbessa.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria find(Integer id) {
	Optional<Categoria> obj = repo.findById(id);
	return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }
    
    public List<Categoria> findAll() {
	return repo.findAll();
    }

    public Categoria insert(Categoria obj) {
	obj.setId(null); // Para garantir que o save vai inserir e não atualizar, caso algum id seja
			 // mandado
	return repo.save(obj);
    }

    public Categoria update(Categoria obj) {
	find(obj.getId());
	return repo.save(obj);
    }

    public void delete(Integer id) {
	find(id);
	try {
	    repo.deleteById(id);	    
	}
	catch (DataIntegrityViolationException e) {
	    throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
	}
    }
    
    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
	
	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
	return repo.findAll(pageRequest);
	
    }
    
    public Categoria fromDTO(CategoriaDTO objDto) {
	return new Categoria(objDto.getId(), objDto.getNome());
    }
}
