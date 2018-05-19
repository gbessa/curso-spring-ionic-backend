package br.com.gbessa.cursomc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gbessa.cursomc.domain.PagamentoComBoleto;
import br.com.gbessa.cursomc.domain.PagamentoComCartao;

@Configuration
public class JacksonConfig {

    // Esse código é meio que uma exigencia do Jackson para conseguir instanciar uma classe abstrata atraves das subclasses    
    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {	
	Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
	  public void configure(ObjectMapper objectMapper) {
	    objectMapper.registerSubtypes(PagamentoComBoleto.class);
	    objectMapper.registerSubtypes(PagamentoComCartao.class);
	    super.configure(objectMapper);
	  }
	};
	return builder;
    }

}
