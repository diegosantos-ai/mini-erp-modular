# ADR-001: Adotar monolito modular com orientacao DevOps desde o inicio

## Status

Proposed

## Contexto

O projeto tem como objetivo principal demonstrar capacidade de engenharia de software e DevOps em um Mini-ERP de Compras + Estoque + Recebimento. O dominio ja possui complexidade suficiente por envolver fluxo de negocio, estados, auditoria, persistencia, seguranca, acessibilidade, deploy e operacao.

Escolher uma arquitetura complexa cedo demais pode aumentar o custo cognitivo e reduzir a capacidade de entregar um fluxo funcional completo.

## Decisao

Adotar inicialmente:
- backend em formato de monolito modular
- modulos de dominio bem separados internamente
- frontend entregue separadamente do backend
- pipeline DevOps tratado como parte do produto desde o inicio

Isso implica priorizar:
- fronteiras claras entre modulos
- automacao de build, testes e deploy
- infraestrutura como codigo
- observabilidade e operacao desde as primeiras iteracoes

## Alternativas consideradas

### Microservicos desde o inicio

Vantagens:
- isolamento forte entre servicos
- narrativa moderna de arquitetura distribuida

Desvantagens:
- aumenta custo operacional e cognitivo
- exige mais infraestrutura, contratos e coordenacao
- nao e a melhor escolha para um MVP individual com foco em aprendizado integral

### Aplicacao unica com pouca modularidade

Vantagens:
- velocidade inicial
- menor esforco estrutural imediato

Desvantagens:
- dificulta evolucao
- prejudica clareza arquitetural
- aumenta risco de acoplamento e manutencao ruim

## Consequencias

Consequencias positivas:
- maior chance de entregar fluxo ponta a ponta
- melhor equilibrio entre simplicidade e organizacao
- menor custo de deploy e operacao no inicio

Consequencias negativas:
- exige disciplina arquitetural interna
- pode demandar refatoracao futura se o sistema crescer muito

## Resultado esperado

Uma arquitetura compreensivel, demonstravel e operacionalmente viavel, que permita foco simultaneo em dominio, qualidade, deploy e observabilidade.

## Revisao futura

Reavaliar esta decisao apos a conclusao do fluxo principal e do primeiro deploy em nuvem.
