
create table item_pedido 
(id bigint not null auto_increment, 
observacao varchar(255), 
preco_total decimal(11,2), 
preco_unitario decimal(11,2), 
quantidade integer not null, 
pedido_id bigint not null, 
produto_id bigint, 
primary key (id)
) engine=InnoDB default charset=utf8;


create table pedido 
(id bigint not null auto_increment, 
codigo varchar(10) not null, 
data_cancelamento datetime, 
data_confirmacao datetime, 
data_criacao datetime not null, 
data_entrega datetime, 
endereco_bairro varchar(100), 
endereco_cep varchar(20), 
endereco_complemento varchar(60), 
endereco_logradouro varchar(100) not null, 
endereco_numero varchar(10) not null, 
status varchar(20), 
sub_total decimal(19,2) not null, 
taxa_frete decimal(19,2) not null, 
total decimal(19,2) not null, 
cliente_id bigint not null, 
endereco_cidade_id bigint, 
formapagto_id bigint not null, 
restaurante_id bigint not null, 
primary key (id)
) engine=InnoDB  default charset=utf8;


alter table pedido add constraint UK_pedido_codigo unique (codigo);
alter table item_pedido add constraint  UK_item_pedido_produto unique (pedido_id, produto_id);

alter table item_pedido add constraint FK_itemPedido_pedido foreign key (pedido_id) references pedido (id);
alter table item_pedido add constraint FK_itemPedido_produto foreign key (produto_id) references produto (id);
alter table pedido add constraint FK_pedido_cliente foreign key (cliente_id) references usuario (id);
alter table pedido add constraint FK_pedido_cidade foreign key (endereco_cidade_id) references cidade (id);
alter table pedido add constraint FK_pedido_formaPagto foreign key (formapagto_id) references forma_pagamento (id);
alter table pedido add constraint FK_pedido_restaurante foreign key (restaurante_id) references restaurante (id);