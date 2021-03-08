package com.borelli.minhasfinancas.service;

import java.util.List;

import com.borelli.minhasfinancas.model.entity.Lancamento;
import com.borelli.minhasfinancas.model.enums.StatusLancamento;

public interface LancamentoService {
	
	Lancamento salvar(Lancamento lancamento);
	Lancamento Atualizar(Lancamento lancamento);
	void deletar(Lancamento lancamento);
	List<Lancamento> buscar( Lancamento lancamentoFiltro );
	void atualizarStatus( Lancamento lancamento, StatusLancamento status);

}
