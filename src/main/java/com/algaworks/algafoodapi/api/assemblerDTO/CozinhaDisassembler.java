package com.algaworks.algafoodapi.api.assemblerDTO;

import org.springframework.stereotype.Component;

import com.algaworks.algafoodapi.api.model.CozinhaInputDTO;
import com.algaworks.algafoodapi.domain.model.Cozinha;

@Component
public class CozinhaDisassembler extends GenericDisassembler<CozinhaInputDTO, Cozinha> {

}
