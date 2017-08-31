package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Autor;

public class AutorDao implements Serializable {

	private static final long serialVersionUID = 1L;
	private DAO<Autor> autorDao;

	@Inject
	EntityManager em;
	
	@PostConstruct
	void init() {
		this.autorDao = new DAO<Autor>(em, Autor.class);
	}
	
	public void adiciona(Autor autor) {
		autorDao.adiciona(autor);
	}

	public void remove(Autor autor) {
		autorDao.remove(autor);
	}

	public void atualiza(Autor autor) {
		autorDao.atualiza(autor);
	}

	public List<Autor> listaTodos() {
		return autorDao.listaTodos();
	}

	public Autor buscaPorId(Integer autorId) {
		return autorDao.buscaPorId(autorId);
	}

	public int contaTodos() {
		return autorDao.contaTodos();
	}

	public int quantidadeDeElementos() {
		return autorDao.quantidadeDeElementos();
	}

}
