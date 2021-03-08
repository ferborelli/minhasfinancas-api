package com.borelli.minhasfinancas.service.impl;

import org.springframework.stereotype.Service;

import com.borelli.minhasfinancas.model.entity.Usuario;
import com.borelli.minhasfinancas.model.repository.UsuarioRepository;
import com.borelli.minhasfinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private UsuarioRepository  repository;	
	
	public UsuarioServiceImpl(UsuarioRepository  repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario salvarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validarEmail(String email) {
		// TODO Auto-generated method stub
		
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			
		}
	}

}