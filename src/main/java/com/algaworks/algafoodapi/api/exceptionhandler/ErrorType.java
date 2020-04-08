package com.algaworks.algafoodapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorType {

	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado."),
	DADOS_INVALIDOS("/dados-invalidos", "Dados Inválidos."),
	ENTIDADE_EM_USO("/entidade-em-uso","Entidade em uso."),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio."),
	ERRO_SINTAXE("/erro-de-sintaxe","Erro de Sintaxe no corpo da requisição."),
	PARAMETRO_INVALIDO("/parametro-invalido","Parâmetro informado na URL inválido."),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de Sistema");

	private String uri;

	private String title;

	private ErrorType(String path, String title) {
		this.uri = "https://algafood.com.br" + path;
		this.title = title;
	}

}
