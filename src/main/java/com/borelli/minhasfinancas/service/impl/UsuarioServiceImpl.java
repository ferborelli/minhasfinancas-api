package com.borelli.minhasfinancas.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.borelli.minhasfinancas.exception.RegraNegocioException;
import com.borelli.minhasfinancas.model.entity.Usuario;
import com.borelli.minhasfinancas.model.repository.UsuarioRepository;
import com.borelli.minhasfinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {	
	 	
	private UsuarioRepository  repository;	
	
	public UsuarioServiceImpl(UsuarioRepository  repository) {
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);
		if(!usuario.isPresent()) {
			throw  new RegraNegocioException("Usúario não encontrado para o email informado.");
		}
		
		if(!usuario.get().getSenha().equals(senha)) {
			throw new RegraNegocioException("Senha inválida.");			
		}
		
		return usuario.get();
	}

	@Override
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		// TODO Auto-generated method stub
		
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Já existe um usuario cadastrado com esse email.");			
		}
	}

 	public Optional<Usuario> obterPorId(Long id) {
 		return repository.findById(id);
	}
}
