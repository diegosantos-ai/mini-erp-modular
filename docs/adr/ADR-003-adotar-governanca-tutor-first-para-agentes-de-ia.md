# ADR-003: Adotar governanca tutor-first para agentes de IA

## Status

Accepted

## Contexto

Este projeto tem dois objetivos simultaneos:
- construir um Mini-ERP com foco em engenharia de software e DevOps
- servir como laboratorio de aprendizado deliberado sobre produto, arquitetura, implementacao e operacao

Nesse contexto, um agente de IA usado apenas como executor rapido reduziria a transferencia de conhecimento e aumentaria o risco de decisoes opacas ou mal compreendidas.

Tambem existe a necessidade de manter rastreabilidade entre:
- decisoes
- documentacao
- backlog
- execucao tecnica

## Decisao

Adotar uma governanca `tutor-first`, `human-in-the-loop` e `learning-first` para agentes de IA neste projeto.

Elementos da decisao:
- o modo padrao do projeto passa a ser `Pairing`
- os modos suportados passam a ser `Study`, `Pairing`, `Review` e `Delivery`
- acoes de medio e alto risco exigem explicacao previa e gate humano
- acoes de alto risco exigem aprovacao explicita e explicacao de impacto e rollback
- o repositorio passa a registrar essa governanca em `AGENTS.md` e `docs/06-governanca-agentes-ia.md`
- uma skill reutilizavel do Codex chamada `tutor-pairing-engineering` passa a operacionalizar esse comportamento

## Alternativas consideradas

### Execucao autonoma por padrao

Vantagens:
- maior velocidade operacional
- menor friccao para tarefas simples

Desvantagens:
- menor transferencia de conhecimento
- mais risco de suposicoes nao percebidas
- menor clareza sobre criterios e trade-offs

### Governanca apenas documental, sem skill

Vantagens:
- menos esforco inicial
- nenhuma dependencia de artefato adicional

Desvantagens:
- menor repetibilidade do comportamento
- maior variacao entre sessoes
- mais risco de o contrato nao ser aplicado de forma consistente

## Consequencias

Consequencias positivas:
- melhor alinhamento entre execucao e aprendizado
- mais transparencia sobre risco e decisao
- maior consistencia no uso do agente ao longo do projeto
- melhor capacidade de explicar o processo em contexto profissional

Consequencias negativas:
- aumento de friccao em comparacao com execucao totalmente autonoma
- necessidade de manter governanca e skill sincronizadas
- possivel reducao de velocidade em tarefas que poderiam ser automatizadas de forma mais agressiva

## Resultado esperado

Um fluxo de trabalho em que a IA ajuda a construir o projeto sem ocultar raciocinio, risco ou impacto, e em que cada etapa relevante tambem contribui para a formacao tecnica do responsavel humano.

## Revisao futura

Reavaliar esta decisao quando:
- o projeto entrar em fase de entrega mais acelerada
- houver mais colaboradores humanos no repositorio
- a skill exigir novos modos ou refinamentos operacionais
