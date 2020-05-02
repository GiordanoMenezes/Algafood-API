package com.algaworks.algafoodapi.api.assemblerDTO;

import org.springframework.stereotype.Component;

import com.algaworks.algafoodapi.api.model.CidadeInputDTO;
import com.algaworks.algafoodapi.domain.model.Cidade;

@Component
public class CidadeDisassembler extends GenericDisassembler<CidadeInputDTO, Cidade> {

}
