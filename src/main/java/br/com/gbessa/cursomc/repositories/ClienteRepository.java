package br.com.gbessa.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.gbessa.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

    //Como usamos o nome do campo, o spring já entende e para a busca para nós    
    @Transactional(readOnly=true)
    Cliente findByEmail(String email); 
    
}
