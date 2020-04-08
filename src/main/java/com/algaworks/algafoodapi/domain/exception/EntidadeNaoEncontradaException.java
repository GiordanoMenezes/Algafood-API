package com.algaworks.algafoodapi.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public abstract class EntidadeNaoEncontradaException extends NegocioException {//extends ResponseStatusException {

	private static final long serialVersionUID = 1L;
		

//	public EntidadeNaoEncontradaException(HttpStatus status, String msg) {
//		super(status, msg);
//	}


//	public EntidadeNaoEncontradaException(String msg) {
//		this(HttpStatus.NOT_FOUND, msg);
//	}
	public EntidadeNaoEncontradaException(String msg) {
		super(msg);
	}
	
}
