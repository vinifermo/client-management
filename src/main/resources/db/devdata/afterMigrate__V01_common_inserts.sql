INSERT INTO tb_cliente (id, nome, data_cadastro, numero_documento, numero_registro, situacao, tipo_pessoa_enum)
VALUES ('7fcd9e88-2138-4c71-bf16-c7d1c62fb34b', 'João da Silva', '2023-04-05 10:00:00', '667.217.700-08','25.955.736-5', 'ATIVO',
        'PESSOA_FISICA') ON CONFLICT DO NOTHING;

INSERT INTO tb_telefone (id, numero, principal, cliente_id)
VALUES ('5a8b8347-0695-425d-92f1-1b81bb90f5e5', '321-1234',true, '7fcd9e88-2138-4c71-bf16-c7d1c62fb34b'),
('f727c5ea-23e7-419e-b388-9589477699e3', '425-1214',false, '7fcd9e88-2138-4c71-bf16-c7d1c62fb34b'),
('93cc31a2-1bcc-4026-8b81-5ab188805504', '875-1931',false, '7fcd9e88-2138-4c71-bf16-c7d1c62fb34b') ON CONFLICT DO NOTHING;