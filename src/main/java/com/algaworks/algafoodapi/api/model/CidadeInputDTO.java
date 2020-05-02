package com.algaworks.algafoodapi.api.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInputDTO {
	
	@NotBlank
	private String nome;
	
	@NotNull
	@Valid
	private EstadoIdDTO estado;

}
