package com.algaworks.algafoodapi.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.algaworks.algafoodapi.domain.model.ENUM.StatusPedido;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 10)
	private String codigo;
	
	@CreationTimestamp
	@Column(nullable=false,columnDefinition = "datetime")
	private LocalDateTime dataCriacao;
	
	@Column(columnDefinition = "datetime")
	private LocalDateTime dataConfirmacao;
	
	@Column(columnDefinition = "datetime")
	private LocalDateTime dataEntrega;
	
	@Column(columnDefinition = "datetime")
	private LocalDateTime dataCancelamento;
	
	@Enumerated(EnumType.STRING)
	private StatusPedido status;
	
	@Column(nullable=false)
	private BigDecimal subTotal;
	
	@Column(nullable=false)
	private BigDecimal taxaFrete;
	
	@Column(nullable=false)
	private BigDecimal total;
	
	@Embedded
	private Endereco enderecoEntrega;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cliente_id",nullable=false)
	private Usuario cliente;
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="formapagto_id", nullable=false)
	private FormaPagamento formaPagto;
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="restaurante_id", nullable=false)
	private Restaurante restaurante;
	
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens = new ArrayList<>();
}
