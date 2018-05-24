package br.com.gbessa.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gbessa.cursomc.domain.Cliente;
import br.com.gbessa.cursomc.dto.CategoriaDTO;
import br.com.gbessa.cursomc.dto.ClienteDTO;
import br.com.gbessa.cursomc.dto.ClienteNewDTO;
import br.com.gbessa.cursomc.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> find(@PathVariable Integer id) {
	Cliente obj = service.find(id);
	return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<ClienteDTO>> findAll() {
	List<Cliente> list = service.findAll();
	List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj))
		.collect(Collectors.toList());
 	return ResponseEntity.ok().body(listDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/page")
    public ResponseEntity<Page<ClienteDTO>> findPage(
	    @RequestParam(name = "page", defaultValue = "0") Integer page,
	    @RequestParam(name = "linesPerPage", defaultValue = "24") Integer linesPerPage,
	    @RequestParam(name = "orderBy", defaultValue = "nome") String orderBy,
	    @RequestParam(name = "direction", defaultValue = "ASC") String direction) {
	Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
	Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
	return ResponseEntity.ok().body(listDto);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) { // Essa anotação é para converter o JSON em objeto java
	Cliente obj = service.fromDTO(objDto);
	obj = service.insert(obj);
	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
	return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
	Cliente obj = service.fromDTO(objDto);
	obj.setId(id); // só para garantir rs
	obj = service.update(obj);
	return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
	service.delete(id);
	return ResponseEntity.noContent().build();
    }
}
