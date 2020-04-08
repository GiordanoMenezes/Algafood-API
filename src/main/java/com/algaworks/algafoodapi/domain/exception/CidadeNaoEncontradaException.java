package com.algaworks.algafoodapi.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
		
	public CidadeNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public CidadeNaoEncontradaException(Long id) {
		this(String.format("Não existe cidade com id %d.",id));
	}
	
}
