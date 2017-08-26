package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.LivroDataModel;
import br.com.caelum.livraria.util.RedirectView;

@ManagedBean
@ViewScoped
public class LivroBean {

	private Livro livro = new Livro();
	private Integer livroId;
	private Integer autorId;
	private List<Livro> livros;
	private LivroDataModel livroDataModel = new LivroDataModel();
	
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

	public List<Livro> getLivros() {
		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		if (this.livros == null) {
			this.livros = dao.listaTodos();
		}
		return livros;
	}

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public LivroDataModel getLivroDataModel() {
		return livroDataModel;
	}

	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
		System.out.println("Escrito por: " + autor.getNome());
	}

	public RedirectView formAutor() {
		return new RedirectView("autor");
	}

	public void carregarlivroPeloId() {
		this.livro = new DAO<Livro>(Livro.class).buscaPorId(livroId);
	}

	public void gravar() {
		System.out.println("Gravando livro " + getLivro().getTitulo());

		if (livro.getAutores().isEmpty()) {
			// throw new RuntimeException("O livro deve ter pelo menos um autor");
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("O livro deve conter pelo menos um autor"));
			return;
		}

		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		if (this.livro.getId() == null) {
			dao.adiciona(this.livro);
			this.livros = dao.listaTodos();
		} else {
			dao.atualiza(this.livro);
		}

		this.livro = new Livro();
	}

	public void remover(Livro livro) {
		System.out.println("Removendo livro " + livro);
		new DAO<Livro>(Livro.class).remove(livro);
	}

	public void removeAutorDoLivro(Autor autor) {
		System.out.println("Removendo autor " + autor);
		this.livro.removerAutor(autor);
	}

	public void contemMaisDeTresDigitos(FacesContext fc, UIComponent component, Object value)
			throws ValidatorException {
		String valor = value.toString();
		if (valor.length() < 3) {
			throw new ValidatorException(new FacesMessage("ISBN deve conter mais de 3 dígitos"));
		}
	}

}
