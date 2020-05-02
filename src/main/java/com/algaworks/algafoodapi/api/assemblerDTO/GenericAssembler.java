package com.algaworks.algafoodapi.api.assemblerDTO;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A generic DTO Assembler that uses ModelMapper.
 * 
 * @param <T> Model type to assemble from.
 * @param <S> DTO type to assemble to.
 */

public abstract class GenericAssembler<T, S> {

	@Autowired
	private ModelMapper modelMapper;
	
	protected Class<S> dtoType;

	@SuppressWarnings("unchecked")
	public GenericAssembler() {
		 Type type = getClass().getGenericSuperclass();

	        while (!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != GenericAssembler.class) {
	            if (type instanceof ParameterizedType) {
	                type = ((Class<?>) ((ParameterizedType) type).getRawType()).getGenericSuperclass();
	            } else {
	                type = ((Class<?>) type).getGenericSuperclass();
	            }
	        }
	        this.dtoType = (Class<S>) ((ParameterizedType) type).getActualTypeArguments()[1];
	}

	public S toDTO(T domainObject) {
		return modelMapper.map(domainObject, dtoType);
	}

	public Collection<S> toCollectionDTO(Collection<T> objects) {
		if (objects instanceof List<?>) {
			return objects.stream().map(object -> toDTO(object)).collect(Collectors.toList());
		} else {
			return objects.stream().map(object -> toDTO(object)).collect(Collectors.toSet());
		}
	}
}
