package com.algaworks.algafoodapi.api.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoInputDTO {
	
	@NotBlank
	private String nome;

}
