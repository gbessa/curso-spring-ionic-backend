package br.com.gbessa.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.gbessa.cursomc.domain.Cliente;
import br.com.gbessa.cursomc.dto.ClienteNewDTO;
import br.com.gbessa.cursomc.enums.TipoCliente;
import br.com.gbessa.cursomc.repositories.ClienteRepository;
import br.com.gbessa.cursomc.resources.exceptions.FieldMessage;
import br.com.gbessa.cursomc.services.validation.utils.BR;

public class ClienteInsertValidador implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository repo;
    
    @Override
    public void initialize(ClienteInsert ann) {
	
    }
        
    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
	
	List<FieldMessage> list = new ArrayList<>();
	
	if (objDto.getTipo() == TipoCliente.PESSOAFISICA.getCod() && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
	    list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido"));
	}
	if (objDto.getTipo() == TipoCliente.PESSOAJURIDICA.getCod() && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
	    list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido"));
	}
	
	Cliente aux = repo.findByEmail(objDto.getEmail());
	if (aux != null) {
	    list.add(new FieldMessage("email", "E-mail já existente"));
	}
	
	for (FieldMessage  e : list) {
	    context.disableDefaultConstraintViolation();
	    context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
	    	.addConstraintViolation();
	}
	
	return list.isEmpty();
    }

}
