package com.algaworks.algafoodapi.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
		
	public CozinhaNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public CozinhaNaoEncontradaException(Long id) {
		this(String.format("Não existe cozinha com id %d.",id));
	}
	
}
