package com.borelli.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.borelli.minhasfinancas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	//Optional<Usuario> findByEmail(String email);
	
	boolean existsByEmail(String email);

}
