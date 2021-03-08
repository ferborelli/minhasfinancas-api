package com.borelli.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.borelli.minhasfinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
