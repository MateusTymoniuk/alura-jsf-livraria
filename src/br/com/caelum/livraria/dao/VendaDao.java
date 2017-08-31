package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Venda;

public class VendaDao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Inject
	EntityManager manager;
	private DAO<Venda> vendaDao;
	
	@PostConstruct
	void init() {
		this.vendaDao = new DAO<Venda>(manager, Venda.class);
	}

	public List<Venda> getTotalVendas() {
		return this.vendaDao.listaTodos();
	}
	
}
