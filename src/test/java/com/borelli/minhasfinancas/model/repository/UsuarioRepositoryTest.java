package com.borelli.minhasfinancas.model.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.borelli.minhasfinancas.model.entity.Usuario;

@ExtendWith( SpringExtension.class )
@ActiveProfiles("test")
public class UsuarioRepositoryTest {	 
	
	@Autowired
	UsuarioRepository repository;
	
	@Test
	public void deveVerificarAExistenciaEmail() {
		//cenario
		Usuario usuario = Usuario.builder().nome("usuario").email("usuario@email.com").build();		
		
		repository.save(usuario);
		
		//ação // execução
		boolean result = repository.existsByEmail("usuario@email.com");
		
		
		//verificação
		Assertions.assertThat(result).isTrue();
		
	}	

}
