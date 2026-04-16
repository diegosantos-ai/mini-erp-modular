# Arquitetura

## 1. Objetivo do documento

Descrever a arquitetura candidata do sistema, com foco em separacao de responsabilidades, operabilidade, entrega continua e clareza para evolucao futura.

## 2. Direcionadores arquiteturais

- foco em aprendizado pratico de engenharia de software e DevOps
- simplicidade operacional sem perder qualidade tecnica
- rastreabilidade de eventos de negocio
- baixo acoplamento entre modulos de dominio
- facilidade de deploy local e em nuvem
- automacao como parte do produto tecnico

## 3. Visao de arquitetura candidata

Direcao inicial proposta:
- backend em Java como linguagem principal
- API HTTP para operacoes do dominio
- frontend web separado do backend em termos de entrega
- banco relacional para persistencia transacional
- conteinerizacao com Docker
- provisionamento com Terraform
- configuracao e deploy com Ansible
- automacao local por Make
- pipeline de CI/CD no GitHub

Decisao candidata principal:
- adotar um monolito modular no backend, evitando microservicos no inicio

Referencia:
- [ADR-001-monolito-modular-devops-first.md](./adr/ADR-001-monolito-modular-devops-first.md)

## 4. Visao logica

Modulos de negocio propostos:
- identidade e acesso
- cadastro de produtos
- cadastro de fornecedores
- requisicao de compra
- aprovacao
- pedido de compra
- recebimento
- estoque
- auditoria
- dashboard e relatorios operacionais

Camadas sugeridas:
- `frontend`: experiencia do usuario, acessibilidade e navegacao
- `api`: contratos HTTP, autenticacao e validacao de entrada
- `application`: casos de uso e orquestracao
- `domain`: regras de negocio e entidades
- `infrastructure`: persistencia, mensageria futura, observabilidade e integracoes

## 5. Visao de contexto

```text
[Usuario Web]
    |
    v
[Frontend Web]
    |
    v
[API Backend Java]
    |
    +--> [Banco de Dados Relacional]
    +--> [Logs / Metrics / Tracing]
```

## 6. Visao de deploy candidata

Arquitetura operacional inicial sugerida:
- um ambiente local com containers para desenvolvimento
- um ambiente remoto em nuvem para validacao e demonstracao
- um reverse proxy na borda
- frontend e backend empacotados e entregues separadamente
- banco executado em servico gerenciado ou container dedicado, decisao ainda pendente

Possivel topologia inicial:

```text
Internet
  |
Reverse Proxy
  |
  +--> Frontend Container
  |
  +--> Backend Container
          |
          +--> PostgreSQL
```

## 7. Ambientes

Ambientes desejados:
- `local`: desenvolvimento e testes rapidos
- `dev`: integracao tecnica
- `staging`: validacao de release e demonstracao
- `prod`: ambiente final de portfolio ou apresentacao

Observacao:
- a quantidade final de ambientes pode ser reduzida para manter viabilidade, mas a esteira deve suportar ao menos `local` e `dev`

## 8. DevOps e operacao

### Build e automacao local

Responsabilidades do `Makefile`:
- padronizar build, testes e execucao local
- encapsular comandos de Docker, Terraform e Ansible
- reduzir variacao de uso entre ambientes

Comandos esperados futuramente:
- `make dev`
- `make test`
- `make lint`
- `make build`
- `make docker-build`
- `make infra-plan`
- `make infra-apply`
- `make deploy`
- `make smoke`

### CI/CD

Esteira desejada:
1. validacao de formato e qualidade
2. build do backend e do frontend
3. execucao de testes automatizados
4. empacotamento em imagens Docker
5. publicacao de artefatos
6. deploy automatizado em ambiente alvo
7. smoke tests pos-deploy

### Observabilidade

Capacidades desejadas:
- logs estruturados
- health checks
- metricas basicas de aplicacao e infraestrutura
- rastreamento basico de fluxo tecnico
- correlacao de eventos de auditoria com acoes de negocio

### Seguranca

Minimos esperados:
- autenticacao
- autorizacao baseada em perfis
- segregacao de configuracoes por ambiente
- tratamento seguro de segredos
- protecao basica de endpoints administrativos

### Acessibilidade

O frontend deve considerar desde o inicio:
- navegacao por teclado
- foco visivel
- formularios com labels e mensagens de erro adequadas
- semantica de tabelas e controles
- contraste e nao dependencia exclusiva de cor

## 9. Decisoes em aberto

- framework do frontend
- provedor de nuvem principal
- banco em container ou servico gerenciado no primeiro deploy
- profundidade de observabilidade do MVP
- estrategia de autenticacao inicial

## 10. Criterios de aceitacao arquitetural

- a arquitetura deve ser explicavel de forma simples
- o sistema deve poder ser executado localmente e em nuvem com o mesmo modelo conceitual
- o deploy deve ser reprodutivel
- modulos devem permitir evolucao sem acoplamento excessivo
- requisitos de operacao devem estar presentes desde o MVP
