package com.algaworks.algafoodapi.api.assemblerDTO;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * A generic DTO Disassembler that uses ModelMapper.
 * 
 * @param <T> Input to disassemble from.
 * @param <S> Domain to disassemble to.
 */

public abstract class GenericDisassembler<T, S> {

	@Autowired
	private ModelMapper modelMapper;
	
	protected Class<S> domainType;

	@SuppressWarnings("unchecked")
	public GenericDisassembler() {
		 Type type = getClass().getGenericSuperclass();

	        while (!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != GenericDisassembler.class) {
	            if (type instanceof ParameterizedType) {
	                type = ((Class<?>) ((ParameterizedType) type).getRawType()).getGenericSuperclass();
	            } else {
	                type = ((Class<?>) type).getGenericSuperclass();
	            }
	        }
	        this.domainType = (Class<S>) ((ParameterizedType) type).getActualTypeArguments()[1];
	}

	public S toDomain (T originInput) {
		return modelMapper.map(originInput,domainType);
	}
	

	public void updateObject(T originInput, S objectToUpdate) {
		
		modelMapper.map(originInput, objectToUpdate);
	}


}
