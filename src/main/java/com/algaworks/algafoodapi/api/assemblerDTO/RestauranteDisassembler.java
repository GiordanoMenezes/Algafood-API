package com.algaworks.algafoodapi.api.assemblerDTO;

import org.springframework.stereotype.Component;

import com.algaworks.algafoodapi.api.model.RestauranteInputDTO;
import com.algaworks.algafoodapi.domain.model.Restaurante;

@Component
public class RestauranteDisassembler extends GenericDisassembler<RestauranteInputDTO, Restaurante> {

}
