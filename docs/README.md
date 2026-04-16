# Documentacao do Projeto

Este diretorio concentra a documentacao oficial do Mini-ERP de Compras + Estoque + Recebimento.

Objetivos desta documentacao:
- alinhar entendimento antes de qualquer implementacao
- registrar decisoes e reduzir suposicoes
- dar rastreabilidade entre produto, arquitetura, backlog, riscos e testes
- servir de base para execucao tecnica, operacao e apresentacao do projeto

Estado atual:
- versao inicial para revisao
- conteudo orientado a planejamento
- decisoes arquiteturais marcadas como `Proposed` quando ainda dependem de validacao

Principios de uso:
- a documentacao deve ser viva e evoluir junto com o projeto
- backlog operacional detalhado fica no GitHub Projects
- ADRs registram decisoes arquiteturais relevantes e seu contexto
- `roadmap-aprendizado.md` permanece como material pessoal de estudo e apresentacao

Leitura sugerida:
1. [01-visao-produto.md](./01-visao-produto.md)
2. [02-requisitos-processos.md](./02-requisitos-processos.md)
3. [03-arquitetura.md](./03-arquitetura.md)
4. [04-dados-qualidade.md](./04-dados-qualidade.md)
5. [05-planejamento-riscos.md](./05-planejamento-riscos.md)
6. [adr/ADR-001-monolito-modular-devops-first.md](./adr/ADR-001-monolito-modular-devops-first.md)

Mapa dos artefatos:
- `01-visao-produto.md`: contexto do produto, objetivos, escopo, stakeholders e perfis de usuario
- `02-requisitos-processos.md`: requisitos funcionais e nao funcionais, casos de uso e fluxo de negocio
- `03-arquitetura.md`: visao tecnica, modulos, deployment, operacao e preocupacoes DevOps
- `04-dados-qualidade.md`: modelo de dados conceitual, estrategia de testes e criterios de aceite
- `05-planejamento-riscos.md`: epicos, priorizacao, milestones, riscos, restricoes e dependencias
- `adr/`: decisoes arquiteturais relevantes

Convencoes:
- IDs de requisito: `RF-xxx` e `RNF-xxx`
- IDs de caso de uso: `UC-xxx`
- IDs de caso de teste: `CT-xxx`
- IDs de risco: `R-xxx`
- IDs de ADR: `ADR-xxx`
