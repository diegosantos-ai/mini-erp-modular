# Observabilidade e Operacao

## 1. Objetivo

Definir a base minima de observabilidade, troubleshooting e operacao para a release `v1.0.0` do Mini-ERP Modular.

Este documento complementa:
- `docs/03-arquitetura.md`
- `docs/04-dados-qualidade.md`
- `docs/05-planejamento-riscos.md`
- `docs/issues-compras-v1.md`
- GitHub Projects

## 2. Escopo da v1.0.0

A primeira release deve priorizar uma operacao simples, rastreavel e demonstravel.

Cobertura minima esperada:
- health check da aplicacao
- logs suficientes para diagnosticar startup, autenticacao e erros de API
- configuracao externa para variaveis sensiveis
- smoke test apos deploy
- runbook curto para problemas frequentes

Fora de escopo inicial:
- alta disponibilidade
- tracing distribuido
- stack completa de metricas com Prometheus/Grafana
- SIEM
- Kubernetes obrigatorio

## 3. Sinais operacionais minimos

### Health check

Endpoint principal:

```text
GET /actuator/health
```

Uso esperado:
- validar execucao local
- validar container
- validar ambiente remoto apos deploy
- apoiar smoke tests

### Logs

Os logs devem permitir responder:
- a aplicacao iniciou corretamente?
- o datasource foi configurado?
- o Flyway executou as migrations?
- houve falha de autenticacao, autorizacao ou validacao?
- uma operacao critica de negocio falhou?

### Auditoria de negocio

Eventos criticos do fluxo de compras devem gerar registro consultavel:
- requisicao submetida
- requisicao aprovada ou rejeitada
- pedido criado
- recebimento registrado
- movimentacao de estoque criada

## 4. Smoke test da v1.0.0

Roteiro minimo apos subir a aplicacao:

1. validar health check
2. autenticar usuario administrador
3. cadastrar fornecedor
4. cadastrar produto
5. criar e submeter requisicao de compra
6. aprovar requisicao
7. gerar pedido
8. registrar recebimento
9. consultar saldo de estoque
10. consultar auditoria

## 5. Runbook inicial

### Aplicacao nao sobe

Verificar:
- variaveis obrigatorias de ambiente
- conectividade com PostgreSQL
- logs de migration do Flyway
- segredo JWT em formato esperado

### Health check nao retorna `UP`

Verificar:
- processo da aplicacao
- porta exposta
- datasource
- logs de startup

### Login falha

Verificar:
- bootstrap do usuario administrador
- senha informada no ambiente
- estado ativo do usuario
- segredo JWT

### Fluxo de compras falha

Verificar:
- produto e fornecedor ativos
- status atual da requisicao
- perfil do usuario autenticado
- regras de transicao do dominio
- registros de auditoria

## 6. Relacao com GitHub Projects

O board continua sendo a fonte operacional de status.

Este documento define a base tecnica de observabilidade e operacao. As implementacoes correspondentes devem ser rastreadas principalmente nos epics:
- `#18` E-08 Containerizacao, infraestrutura e deploy em OVHcloud
- `#19` E-09 Observabilidade, troubleshooting, seguranca e hardening
- `#20` E-10 Documentacao final e demonstracao
