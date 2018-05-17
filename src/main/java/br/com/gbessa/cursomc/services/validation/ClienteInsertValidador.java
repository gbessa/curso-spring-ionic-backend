package br.com.gbessa.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.gbessa.cursomc.dto.ClienteNewDTO;
import br.com.gbessa.cursomc.enums.TipoCliente;
import br.com.gbessa.cursomc.resources.exceptions.FieldMessage;
import br.com.gbessa.cursomc.services.validation.utils.BR;

public class ClienteInsertValidador implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

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
	
	for (FieldMessage  e : list) {
	    context.disableDefaultConstraintViolation();
	    context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFiledName())
	    	.addConstraintViolation();
	}
	
	return list.isEmpty();
    }

}
