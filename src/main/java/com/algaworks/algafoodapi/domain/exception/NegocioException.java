package com.algaworks.algafoodapi.domain.exception;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NegocioException extends RuntimeException {//extends ResponseStatusException {

	private static final long serialVersionUID = 1L;
		
	public NegocioException(String msg) {
		super(msg);
	}
	
}
