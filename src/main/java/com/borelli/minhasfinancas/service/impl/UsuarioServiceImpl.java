package com.borelli.minhasfinancas.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.borelli.minhasfinancas.exception.ErroAutenticacao;
import com.borelli.minhasfinancas.exception.RegraNegocioException;
import com.borelli.minhasfinancas.model.entity.Usuario;
import com.borelli.minhasfinancas.model.repository.UsuarioRepository;
import com.borelli.minhasfinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {	
	 	
	private UsuarioRepository  repository;
	private PasswordEncoder encoder;	
	
	public UsuarioServiceImpl(UsuarioRepository  repository, PasswordEncoder encoder) {
		super();
		this.repository = repository;
		this.encoder = encoder;
	}

	@Override
	public Usuario autenticar(String email, String senha) { 
		Optional<Usuario> usuario = repository.findByEmail(email);
		if(!usuario.isPresent()) {
			throw  new ErroAutenticacao("Usúario não encontrado para o email informado.");
		}
		
		boolean senhasBatem = encoder.matches(senha, usuario.get().getSenha());
		
		if(!senhasBatem) {			
			throw new ErroAutenticacao("Senha inválida.");			
		}
		
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		criptografarSenha(usuario);
		return repository.save(usuario);
	}

	private void criptografarSenha(Usuario usuario) {
		String senha = usuario.getSenha();
		String senhaCripto = encoder.encode(senha);
		usuario.setSenha(senhaCripto);
	}

	@Override
	public void validarEmail(String email) { 
		// TODO Auto-generated method stub
		
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Já existe um usuario cadastrado com esse email.");			
		}
	}

	@Override
 	public Optional<Usuario> obterPorId(Long id) {
 		return repository.findById(id);
	}
}
