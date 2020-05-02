package com.algaworks.algafoodapi.api.model;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaIdDTO {

	@NotNull
	private Long id;
	
}
