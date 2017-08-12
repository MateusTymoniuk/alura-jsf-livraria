package br.com.caelum.livraria.bean;

import javax.faces.bean.ManagedBean;

import br.com.caelum.livraria.modelo.Livro;

@ManagedBean
public class LivroBean {

	Livro livro = new Livro();

	public Livro getLivro() {
		return livro;
	}

	public void gravar() {
		System.out.println("Gravando livro " + getLivro().getTitulo());
	}

}
