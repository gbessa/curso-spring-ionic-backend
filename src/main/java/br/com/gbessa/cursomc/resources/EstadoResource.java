package br.com.gbessa.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gbessa.cursomc.domain.Cidade;
import br.com.gbessa.cursomc.domain.Estado;
import br.com.gbessa.cursomc.dto.CidadeDTO;
import br.com.gbessa.cursomc.dto.EstadoDTO;
import br.com.gbessa.cursomc.services.CidadeService;
import br.com.gbessa.cursomc.services.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoResource {

    @Autowired
    private EstadoService service;
    
    @Autowired
    private CidadeService cidadeService;
    
    @GetMapping()
    public ResponseEntity<List<EstadoDTO>> findAll() {
	List<Estado> list = service.findAll();
	List<EstadoDTO> listDto = list.stream().map(obj -> new EstadoDTO(obj))
		.collect(Collectors.toList());
	return ResponseEntity.ok().body(listDto);
    }
    
    @GetMapping(value="/{estadoId}/cidades")
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) {
	List<Cidade> list = cidadeService.findByEstado(estadoId);
	List<CidadeDTO> listDto = list.stream().map(obj -> new CidadeDTO(obj))
		.collect(Collectors.toList());
	return ResponseEntity.ok().body(listDto);
    }
}
