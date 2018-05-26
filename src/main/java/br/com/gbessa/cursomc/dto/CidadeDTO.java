package br.com.gbessa.cursomc.dto;

import java.io.Serializable;

import br.com.gbessa.cursomc.domain.Cidade;
import br.com.gbessa.cursomc.domain.Estado;

public class CidadeDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String nome;
    
    public CidadeDTO() {
	
    }
    
    public CidadeDTO(Cidade obj) {
	this.id = obj.getId();
	this.nome = obj.getNome();
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CidadeDTO(Estado obj) {
	this.id = obj.getId();
	this.nome = obj.getNome();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

      
    
}
