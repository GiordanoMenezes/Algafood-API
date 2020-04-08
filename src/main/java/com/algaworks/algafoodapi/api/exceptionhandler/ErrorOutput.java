package com.algaworks.algafoodapi.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Builder
@Getter
public class ErrorOutput {
	
	private Integer status;
	
	private String type;
	
	private String title;
	
	private String detail;
	
	private LocalDateTime datetime;
	
	private List<Field> fields;
	
	@Getter
	@Builder
	public static class Field {
		String fieldname;
		
		String message;
	}

}
