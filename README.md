# Mini-ERP de Compras, Estoque e Recebimento

![Status](https://img.shields.io/badge/status-planning-334155?style=flat-square)
![Java](https://img.shields.io/badge/Java-backend-334155?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-platform-334155?style=flat-square&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-data-334155?style=flat-square&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-containers-334155?style=flat-square&logo=docker&logoColor=white)
![OVHcloud](https://img.shields.io/badge/OVHcloud-cloud-334155?style=flat-square)
![Terraform](https://img.shields.io/badge/Terraform-IaC-334155?style=flat-square&logo=terraform&logoColor=white)
![Ansible](https://img.shields.io/badge/Ansible-automation-334155?style=flat-square&logo=ansible&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-CI%2FCD-334155?style=flat-square&logo=githubactions&logoColor=white)
![Kubernetes](https://img.shields.io/badge/Kubernetes-roadmap-334155?style=flat-square&logo=kubernetes&logoColor=white)
![Make](https://img.shields.io/badge/Make-workflows-334155?style=flat-square&logo=gnu&logoColor=white)
![Accessibility](https://img.shields.io/badge/Accessibility-first-334155?style=flat-square)

Projeto de estudo e portfolio orientado a engenharia de software e DevOps, com foco na construcao de um Mini-ERP web para o fluxo de compras, recebimento e controle de estoque.

O objetivo nao e apenas entregar funcionalidade de negocio, mas demonstrar um processo completo de engenharia: documentacao antes da implementacao, modelagem de dominio, API REST em Java com Spring Boot, frontend acessivel, infraestrutura como codigo, automacao, testes, CI/CD e operacao em nuvem.

## Status

O projeto esta em fase de planejamento. Neste momento, o repositorio prioriza alinhamento de produto, requisitos, arquitetura, riscos e estrategia de execucao antes do inicio da implementacao.

## Escopo Inicial

Fluxo principal previsto para o MVP:

```text
Requisicao de Compra -> Aprovacao -> Pedido de Compra -> Recebimento -> Entrada em Estoque -> Auditoria e Consulta
```

Capacidades previstas:
- cadastro de produtos e fornecedores
- requisicao de compra com aprovacao
- pedido de compra e acompanhamento de recebimento
- movimentacao e consulta de estoque
- autenticacao, autorizacao e auditoria
- frontend web acessivel
- deploy em nuvem com esteira reprodutivel

## Foco de Engenharia

O projeto foi desenhado para exercitar:
- operacao de aplicacoes Java/Spring Boot em ambiente real
- arquitetura de sistema corporativo pequeno com Java como linguagem principal
- separacao entre produto, requisitos, arquitetura, dados, testes e riscos
- padronizacao de fluxo local com `Make`
- conteinerizacao com Docker
- provisionamento com Terraform na OVHcloud
- configuracao e deploy com Ansible
- pipeline de CI/CD com GitHub Actions
- observabilidade, troubleshooting, operacao e preocupacoes de acessibilidade desde o inicio
- evolucao planejada de Docker Compose para Kubernetes

## Documentacao

A documentacao formal do projeto esta centralizada em [docs/README.md](./docs/README.md).

Leitura recomendada:
1. [Visao do Produto](./docs/01-visao-produto.md)
2. [Requisitos e Processos](./docs/02-requisitos-processos.md)
3. [Arquitetura](./docs/03-arquitetura.md)
4. [Dados e Qualidade](./docs/04-dados-qualidade.md)
5. [Planejamento, Priorizacao e Riscos](./docs/05-planejamento-riscos.md)
6. [Governanca de Agentes de IA](./docs/06-governanca-agentes-ia.md)
7. [ADR-001](./docs/adr/ADR-001-monolito-modular-devops-first.md)
8. [ADR-003](./docs/adr/ADR-003-adotar-governanca-tutor-first-para-agentes-de-ia.md)
9. [ADR-004](./docs/adr/ADR-004-adotar-ovhcloud-single-host-com-evolucao-para-kubernetes.md)

## Organizacao do Repositorio

```text
.
|-- AGENTS.md
|-- docs/
|-- application/
|-- entities/
|-- README.md
`-- roadmap-aprendizado.md
```

## Observacao

O `README.md` da raiz funciona como a vitrine do repositorio: apresenta o projeto, o objetivo tecnico e os links principais.

O `docs/README.md` funciona como indice da documentacao oficial: organiza os artefatos de produto, requisitos, arquitetura, qualidade, planejamento e ADRs.

O `AGENTS.md` registra o contrato operacional para agentes de IA neste repositorio.
