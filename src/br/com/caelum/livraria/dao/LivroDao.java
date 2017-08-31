package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Livro;

public class LivroDao implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	private DAO<Livro> livroDao;
	@Inject
	EntityManager em;
	
	@PostConstruct
	void init() {
		this.livroDao = new DAO<Livro>(em, Livro.class);
	}

	public void adiciona(Livro livro) {
		this.livroDao.adiciona(livro);
	}

	public void remove(Livro livro) {
		this.livroDao.remove(livro);
	}

	public void atualiza(Livro livro) {
		this.livroDao.atualiza(livro);
	}

	public List<Livro> listaTodos() {
		return this.livroDao.listaTodos();
	}

	public Livro buscaPorId(Integer livroId) {
		return this.livroDao.buscaPorId(livroId);
	}

	public int contaTodos() {
		return this.livroDao.contaTodos();
	}

	public List<Livro> listaTodosPaginada(int firstResult, int maxResults, String coluna, String valor) {
		return this.livroDao.listaTodosPaginada(firstResult, maxResults, coluna, valor);
	}

	public int quantidadeDeElementos() {
		return this.livroDao.quantidadeDeElementos();
	}

}
