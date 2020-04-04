package com.algaworks.algafoodapi.infra;

import static com.algaworks.algafoodapi.domain.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafoodapi.domain.repository.spec.RestauranteSpecs.comNomeContem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager em;
	
	@Lazy
	@Autowired
	private RestauranteRepository restRepo;


	//Esta consulta ficaria mais bem implementada com o @Query direto com a string jpql direto na interface
	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaIni, BigDecimal taxaFim) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Restaurante> criteria = cb.createQuery(Restaurante.class);
		
		Root<Restaurante> root = criteria.from(Restaurante.class);
		
		var predicates = new ArrayList<Predicate>();
		
		if (StringUtils.hasLength(nome)) {
			predicates.add(cb.like(root.get("nome"), "%"+nome+"%"));
		}
		
		if (taxaIni !=null) {
			predicates.add(cb.greaterThanOrEqualTo(root.get("taxaFrete"),taxaIni));
		}
		
		if (taxaFim !=null) {
			predicates.add(cb.lessThanOrEqualTo(root.get("taxaFrete"),taxaFim));
		}
		
	    criteria.where(predicates.toArray(new Predicate[0]));
				
	    TypedQuery<Restaurante> query = em.createQuery(criteria);
	    
	    return query.getResultList();

//		var jpql = "from Restaurante where nome like :nome and taxaFrete between :taxaIni and :taxaFim";
//
//		return em.createQuery(jpql, Restaurante.class).setParameter("nome","%"+nome+"%").setParameter("taxaIni", taxaIni)
//				.setParameter("taxaFim", taxaFim).getResultList();
	}
	
	@Override
	public List<Restaurante> porNomeFreteGratis(String nome) {
		
		if (StringUtils.hasText(nome)) {
			return restRepo.findAll(comFreteGratis().and(comNomeContem(nome)));
		} else {
			return restRepo.findAll(comFreteGratis());
		}
	}
	
		
}
