package com.algaworks.algafoodapi.api.assemblerDTO;

import org.springframework.stereotype.Component;

import com.algaworks.algafoodapi.api.model.EstadoInputDTO;
import com.algaworks.algafoodapi.domain.model.Estado;

@Component
public class EstadoDisassembler extends GenericDisassembler<EstadoInputDTO, Estado> {

}
