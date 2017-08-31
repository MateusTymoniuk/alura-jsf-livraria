package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.tx.Log;
import br.com.caelum.livraria.tx.Transacional;
import br.com.caelum.livraria.util.RedirectView;

@Named
@ViewScoped
public class AutorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Autor autor = new Autor();
	private Integer autorId;
	
	@Inject
	private AutorDao autorDao;
	
	public Autor getAutor() {
		return autor;
	}
	
	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	
	public Integer getAutorId() {
		return autorId;
	}
	
	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	@Transacional
	@Log
	public void carregarAutorPeloId() {
		this.autor =  this.autorDao.buscaPorId(autorId);
	}
	
	@Transacional
	@Log
	public List<Autor> getAutores() {
		return this.autorDao.listaTodos();
	}

	@Transacional
	@Log
	public RedirectView gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if(this.autor.getId() == null) {
			this.autorDao.adiciona(this.autor);
		} else {
			this.autorDao.atualiza(this.autor);
		}
		
		this.autor = new Autor();
		
		return new RedirectView("livro");
	}
	
	@Transacional
	@Log
	public void remover(Autor autor) {
		System.out.println("Removendo autor " + autor);
		this.autorDao.remove(autor);
	}
	
}
