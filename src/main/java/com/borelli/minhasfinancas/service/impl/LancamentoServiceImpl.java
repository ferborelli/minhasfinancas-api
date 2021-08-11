package com.borelli.minhasfinancas.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.borelli.minhasfinancas.exception.RegraNegocioException;
import com.borelli.minhasfinancas.model.entity.Lancamento;
import com.borelli.minhasfinancas.model.enums.StatusLancamento;
import com.borelli.minhasfinancas.model.enums.TipoLancamento;
import com.borelli.minhasfinancas.model.repository.LancamentoRepository;
import com.borelli.minhasfinancas.service.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService {
	
	private LancamentoRepository repository;	

	public LancamentoServiceImpl(LancamentoRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		validar(lancamento);
		lancamento.setStatus(StatusLancamento.PENDENTE);
 		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public Lancamento Atualizar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		validar(lancamento);
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public void deletar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		repository.delete(lancamento);
	}
			 

	@Override
	@Transactional()
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		Example example = Example.of( lancamentoFiltro , 
									  ExampleMatcher.matching()
									  .withIgnoreCase()
									  .withStringMatcher( StringMatcher.CONTAINING));
		
 		return repository.findAll(example);
	}

	@Override
	public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
		lancamento.setStatus(status);
		Atualizar(lancamento);
		
	}

 	public void validar(Lancamento lancamento) {

		if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("") ) {
			throw new RegraNegocioException("Informe uma descrição válida.");
		}
		
		if(lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() > 12 ) {
			throw new RegraNegocioException("Mês invalido.");
		}
		
		if(lancamento.getAno() == null || lancamento.getAno().toString().length() != 4 ) {
			throw new RegraNegocioException("Ano invalido.");
		}
		
		if(lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null ) {
			throw new RegraNegocioException("Informe um usuário.");
		}
		
		if(lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1 ) {
			throw new RegraNegocioException("Informar um valor válido.");
		}
		
		if (lancamento.getTipo() == null ) {
			throw new RegraNegocioException("Informar tipo lançamento.");
		}
		
	}

	@Override
	public Optional<Lancamento> obterPorId(Long id) {
 		return repository.findById(id);
	}

	@Override
	@Transactional 
	public BigDecimal obterSaldoPorUsuario(Long id) {

		BigDecimal receitas = repository.obterSaldoPorLancamentoEUsuario(id, TipoLancamento.RECEITA);
		BigDecimal despesas = repository.obterSaldoPorLancamentoEUsuario(id, TipoLancamento.DESPESA);

		if(receitas == null ) {
			receitas = BigDecimal.ZERO;
		}
		
		if(despesas == null ) {
			despesas = BigDecimal.ZERO;
		}
		
		return receitas.subtract(despesas);
	}

	 
	
	 
}
