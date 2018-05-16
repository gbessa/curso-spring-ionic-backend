package br.com.gbessa.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gbessa.cursomc.domain.Categoria;
import br.com.gbessa.cursomc.dto.CategoriaDTO;
import br.com.gbessa.cursomc.services.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @GetMapping()
    public ResponseEntity<List<CategoriaDTO>> findAll() {
	List<Categoria> list = service.findAll();
	List<CategoriaDTO> listDto = list.stream().map(categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());
	return ResponseEntity.ok().body(listDto);
    }
    
    //@RequestMapping(value = "/{id}", method=RequestMethod.GET)
    @GetMapping(value="/{id}")
    public ResponseEntity<Categoria> find(@PathVariable Integer id) {
	Categoria obj = service.find(id);
	return ResponseEntity.ok().body(obj);
    }
    
    //@RequestMapping(method=RequestMethod.POST)
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody Categoria obj) { //Essa anotação é para converter o JSON em objeto java
	obj = service.insert(obj);
	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
	return ResponseEntity.created(uri).build();
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id) {
	obj.setId(id); //só para garantir rs
	obj = service.update(obj);
	return ResponseEntity.noContent().build();
    }
    
    //@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
	service.delete(id);
	return ResponseEntity.noContent().build();
    }
    
   
}
