CREATE TABLE tb_cliente
(
    id               UUID PRIMARY KEY,
    nome             VARCHAR(50) NOT NULL,
    data_cadastro    TIMESTAMP   NOT NULL,
    numero_documento VARCHAR(20) NOT NULL,
    numero_registro  VARCHAR(20) NOT NULL,
    situacao         VARCHAR(10) NOT NULL,
    tipo_pessoa_enum VARCHAR(20) NOT NULL
);

create table tb_telefone
(
    id         UUID primary key,
    numero     varchar(50),
    principal  BOOLEAN,
    cliente_id UUID,
    foreign key (cliente_id) references tb_cliente (id)
);