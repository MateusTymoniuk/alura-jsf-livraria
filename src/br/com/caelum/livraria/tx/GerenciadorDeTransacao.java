package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transacional
@Interceptor
public class GerenciadorDeTransacao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Inject
	EntityManager manager;
	
	@AroundInvoke
	public Object executaTX(InvocationContext contexto) {
		// abre transacao
		manager.getTransaction().begin();
		Object resultado = null;

		try {
			resultado = contexto.proceed();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println("Fazendo o rollback da transação");
			e.printStackTrace();
		}

		// commita a transacao
		manager.getTransaction().commit();
		
		return resultado;
	}
}
