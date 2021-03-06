package br.com.gbessa.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gbessa.cursomc.domain.Produto;
import br.com.gbessa.cursomc.dto.ProdutoDTO;
import br.com.gbessa.cursomc.resources.utils.URL;
import br.com.gbessa.cursomc.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @GetMapping()
    public ResponseEntity<Page<ProdutoDTO>> findPage(@RequestParam(name = "nome", defaultValue = "") String nome,
	    @RequestParam(name = "categorias", defaultValue = "") String categorias,
	    @RequestParam(name = "page", defaultValue = "0") Integer page,
	    @RequestParam(name = "linesPerPage", defaultValue = "24") Integer linesPerPage,
	    @RequestParam(name = "orderBy", defaultValue = "nome") String orderBy,
	    @RequestParam(name = "direction", defaultValue = "ASC") String direction) {
	String nomeDecoded = URL.decodeParam(nome);
	List<Integer> ids = URL.decodeListInteger(categorias);
	Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
	Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
	return ResponseEntity.ok().body(listDto);
    }
}
