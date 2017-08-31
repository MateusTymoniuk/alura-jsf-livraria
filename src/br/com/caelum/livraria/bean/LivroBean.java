package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.tx.Log;
import br.com.caelum.livraria.tx.Transacional;
import br.com.caelum.livraria.util.RedirectView;

@Named
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Livro livro = new Livro();
	private Integer livroId;
	private Integer autorId;
	private List<Livro> livros;

	@Inject
	LivroDao livroDao;

	@Inject
	AutorDao autorDao;
	
	@Inject
	FacesContext context;

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	@Log
	public List<Livro> getLivros() {
		if (livros == null) {
			preencheListaDeLivros();
		}
		return livros;
	}
	
	@Transacional
	@Log
	public void preencheListaDeLivros() {
		livros = livroDao.listaTodos();
	}

	@Transacional
	@Log
	public List<Autor> getAutores() {
		return autorDao.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return livro.getAutores();
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	@Transacional
	@Log
	public void gravarAutor() {
		Autor autor = autorDao.buscaPorId(this.autorId);
		livro.adicionaAutor(autor);
		System.out.println("Escrito por: " + autor.getNome());
	}

	public RedirectView formAutor() {
		return new RedirectView("autor");
	}

	@Transacional
	@Log
	public void carregar(Livro livro) {
		this.livro = livroDao.buscaPorId(livro.getId());
	}

	@Transacional
	@Log
	public void gravar() {
		System.out.println("Gravando livro " + getLivro().getTitulo());

		if (livro.getAutores().isEmpty()) {
			// throw new RuntimeException("O livro deve ter pelo menos um autor");
			context.addMessage("autor",
					new FacesMessage("O livro deve conter pelo menos um autor"));
			return;
		}

		if (livro.getId() == null) {
			livroDao.adiciona(livro);
			livros = livroDao.listaTodos();
		} else {
			livroDao.atualiza(livro);
		}

		livro = new Livro();
	}

	@Transacional
	@Log
	public void remover(Livro livro) {
		System.out.println("Removendo livro " + livro);
		livroDao.remove(livro);
		preencheListaDeLivros();
	}

	public void removeAutorDoLivro(Autor autor) {
		System.out.println("Removendo autor " + autor);
		livro.removerAutor(autor);
	}

	public void contemMaisDeTresDigitos(FacesContext fc, UIComponent component, Object value)
			throws ValidatorException {
		String valor = value.toString();
		if (valor.length() < 3) {
			throw new ValidatorException(new FacesMessage("ISBN deve conter mais de 3 dígitos"));
		}
	}

}
