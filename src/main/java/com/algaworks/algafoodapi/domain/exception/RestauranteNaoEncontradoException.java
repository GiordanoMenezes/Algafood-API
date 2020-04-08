package com.algaworks.algafoodapi.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
		
	public RestauranteNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public RestauranteNaoEncontradoException(Long id) {
		this(String.format("Não existe restaurante com id %d.",id));
	}
	
}
