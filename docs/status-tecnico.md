# Status Técnico - Mini-ERP de Compras

## Estado atual
- Backend Spring Boot iniciado
- Autenticação JWT implementada
- Módulo identity criado
- Flyway configurado
- Health check disponível
- PostgreSQL local configurado com Docker Compose
- Profile local da aplicação validado com PostgreSQL
- Migração inicial executando no PostgreSQL
- Cadastro de fornecedores implementado
- Testes de integração do cadastro de fornecedores implementados
- Migration V2 de fornecedores aplicada com sucesso no PostgreSQL local

## Lacunas antes da Release 1.0
- Testes automatizados dos módulos de negócio
- Cadastro de produtos
- Requisição de compra
- Aprovação
- Pedido
- Recebimento
- Estoque
- Auditoria
- OpenAPI
- CI

## Decisão
Finalizar o fluxo de compras antes de iniciar RH/Folha.

## Próxima fase
Implementar cadastro de produtos.
