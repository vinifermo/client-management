# Desafio Squad

## Essa é uma API desenvolvida com Java 17 e Spring Boot para ser usado como base para o desafio a seguir:
O objetivo principal do desafio é construir uma API REST de criação de Cliente e manipulações em seu telefone.

## Especificação da API
A API possui um endpoint para consulta do endereço e cálculo do frete, que pode ser acessado através da URL:

```bash
POST /clientes
```
## Request
A requisição deve ser feita com um JSON no corpo da mensagem, contendo o os campos necessários.

Exemplo de request:

```bash
{
  "nome": "Vinicius",
  "numeroDocumento" : "06.564.033/0001-40",
  "numeroRegistro": "524.264.777.121",
  "tipoPessoaEnum": "PESSOA_FISICA",
  "telefones": [
    {
      "numero": "(11)99999-9999",
      "principal": true
    }
  ]
}
```

## Response
O servidor responde com um JSON no corpo da mensagem, contendo as informações que foram passadas para criar um cliente.

Lembrando que existe uma validação onde PESSOA_FISICA só pode cadastrar CPF e RG e PESSOA_JURIDICA CNPJ e IE.

Exemplo de response bem sucedido vai ser gerado um header onde poderá ser localizado atrás ve um endpoint de GET / (ID):

```bash
http://localhost:8080/clientes/262866f9-f8ba-450b-af93-f0f91d135a14
```


# Execução do projeto
Para executar o projeto, é necessário ter o Java 17, Docker e o Maven instalados na máquina.

Clone o repositório:
```bash
https://github.com/vinifermo/desafio-squad
```
Não se esqueça de utilizar o Docker para subir uma imagem do PostgresSQL utilizando o comando: 
```bash
docker compose up
```
# Documentação Swagger
A documentação da API pode ser acessada em http://localhost:8080/swagger-ui.html.

# Testes
Os testes unitários foram implementados utilizando JUnit5 e Mockito.
```bash
mvn test
```
