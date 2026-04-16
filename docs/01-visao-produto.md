# Visao do Produto

## 1. Objetivo do documento

Definir a visao inicial do Mini-ERP de Compras + Estoque + Recebimento, delimitando problema, proposta de valor, objetivos, escopo inicial e participantes do sistema.

## 2. Declaracao de produto

O projeto visa construir um Mini-ERP web com backend API, frontend acessivel e pipeline de entrega completo, com enfase em engenharia de software e DevOps. O sistema deve permitir controlar o ciclo basico de compras, recebimento e movimentacao de estoque, com rastreabilidade operacional e suporte a auditoria.

## 3. Problema a resolver

Empresas pequenas e times internos frequentemente precisam controlar compras e estoque por planilhas, mensagens soltas e processos pouco auditaveis. Isso gera:
- baixa rastreabilidade entre solicitacao, aprovacao, pedido e recebimento
- dificuldade para saber saldo, historico e origem de movimentacoes de estoque
- retrabalho operacional e risco de erros manuais
- pouca visibilidade sobre quem aprovou, recebeu ou alterou informacoes

Para fins de aprendizado, o sistema tambem deve servir como laboratorio pratico de:
- modelagem de dominio
- desenho de API e frontend
- conteinerizacao e infraestrutura como codigo
- automacao de testes
- CI/CD, observabilidade e operacao em nuvem

## 4. Objetivos do produto

Objetivos de negocio:
- centralizar o processo de compras e recebimento
- permitir visao consistente de estoque
- manter historico e auditoria das operacoes principais

Objetivos tecnicos:
- demonstrar uma arquitetura coerente para um sistema corporativo pequeno
- evidenciar boas praticas de DevOps desde o inicio do projeto
- permitir deploy reprodutivel, observavel e documentado
- servir como portfolio tecnico com rastreabilidade entre decisao, implementacao e operacao

## 5. Escopo inicial proposto

Escopo MVP:
- cadastro de produtos
- cadastro de fornecedores
- criacao de requisicoes de compra
- aprovacao ou rejeicao de requisicoes
- emissao de pedido de compra
- registro de recebimento
- movimentacao de estoque vinculada ao recebimento
- consulta de saldo e historico de estoque
- autenticacao e autorizacao basica por perfis
- trilha de auditoria para eventos principais
- dashboard operacional inicial

Escopo futuro desejavel:
- notas fiscais e financeiro
- multiplos almoxarifados
- relatorios analiticos mais avancados
- notificacoes
- importacao e exportacao de dados
- indicadores operacionais

Fora de escopo neste momento:
- contabilidade completa
- folha de pagamento
- CRM
- manufatura
- multitenancy
- integracao com ERPs de terceiros

## 6. Principios orientadores

- nenhuma implementacao deve nascer sem contexto e documentacao minima
- simplicidade operacional vale mais do que complexidade prematura
- rastreabilidade e auditabilidade sao requisitos centrais
- acessibilidade web faz parte da qualidade do produto
- automacao deve existir desde cedo, inclusive para ambiente e deploy

## 7. Stakeholders

Stakeholders primarios:
- patrocinador do projeto: responsavel por validar a direcao e o valor do sistema
- engenheiro responsavel pelo produto: define arquitetura, entrega tecnica e operacao
- usuario solicitante: abre requisicoes de compra
- aprovador: analisa e decide sobre requisicoes
- comprador: transforma requisicao aprovada em pedido
- recebedor/almoxarife: registra recebimento e entrada em estoque
- gestor operacional: acompanha dashboards e status do processo

Stakeholders secundarios:
- recrutador ou avaliador tecnico: analisa o projeto como evidencia de capacidade
- time de operacoes futuro: consome documentacao, pipeline e observabilidade

## 8. Perfis de usuario

### Solicitante
- cria requisicoes de compra
- acompanha status da propria solicitacao
- nao aprova requisicoes

### Aprovador
- consulta requisicoes pendentes
- aprova ou rejeita requisicoes
- registra justificativa da decisao

### Comprador
- cria pedidos de compra a partir de requisicoes aprovadas
- acompanha itens pendentes de recebimento

### Almoxarife
- registra recebimento fisico
- informa divergencias de quantidade
- consulta saldo e historico de estoque

### Gestor
- visualiza indicadores operacionais
- consulta trilha de auditoria e gargalos do fluxo

### Administrador
- gerencia usuarios, perfis e parametros basicos
- possui acesso a funcoes de governanca e suporte

## 9. Criterios de sucesso

- fluxo principal de compra ate recebimento executavel sem intervencao manual externa
- trilha de auditoria visivel para eventos criticos
- ambiente local e ambiente em nuvem reproduziveis
- pipeline automatizado com build, testes e deploy
- documentacao suficiente para explicar produto, arquitetura e operacao

## 10. Pontos para validacao

- confirmar se o MVP inclui ou nao faturamento de fornecedor
- confirmar se o estoque sera inicialmente de um unico deposito
- confirmar se o frontend sera SPA desacoplada ou interface server-side
- confirmar profundidade desejada de observabilidade no MVP
