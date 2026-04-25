# Definition of Done e Convencoes de Branch e PR

## 1. Objetivo do documento

Formalizar o contrato operacional de entrega do repositorio para reduzir ambiguidade sobre quando um trabalho pode entrar em execucao, ser considerado pronto e seguir para merge.

Este documento complementa:
- `AGENTS.md`
- `docs/05-planejamento-riscos.md`
- `docs/06-governanca-agentes-ia.md`
- GitHub Projects do repositorio

## 2. Principios

- toda mudanca deve nascer de um item rastreavel
- branches devem ser curtas, especificas e descartaveis
- `develop` e a branch de integracao do trabalho corrente
- `main` representa a linha mais estavel e demonstravel do projeto
- item grande demais nao entra em execucao sem quebrar escopo
- pronto significa implementado, verificado, explicado e rastreado

## 3. Estrategia de branches

Fluxo padrao:
1. atualizar `develop`
2. criar branch curta a partir de `develop`
3. trabalhar em escopo unico
4. abrir PR para `develop`
5. promover `develop` para `main` apenas quando fizer sentido consolidar uma entrega estavel

Regra geral:
- nao desenvolver diretamente em `develop`
- nao usar `main` como branch de trabalho cotidiano
- evitar branches longas e multifuncionais

Padroes de nome recomendados:
- `feature/<tema-curto>`
- `fix/<tema-curto>`
- `docs/<tema-curto>`
- `chore/<tema-curto>`
- `refactor/<tema-curto>`
- `test/<tema-curto>`
- `infra/<tema-curto>`

Exemplos:
- `feature/frontend-stack-decision`
- `docs/dod-convencoes-branch-pr`
- `infra/docker-compose-local`

## 4. Convencoes de PR

Todo PR deve:
- ter escopo unico e explicavel
- referenciar issue ou objetivo claro do board
- informar o que mudou, por que mudou e como validar
- destacar riscos, pendencias e trade-offs quando existirem

Direcao de merge padrao:
- `feature/*`, `fix/*`, `docs/*`, `chore/*`, `refactor/*`, `test/*`, `infra/*` -> `develop`
- `develop` -> `main` somente para consolidacao de marco estavel

Quando usar Draft PR:
- escopo ainda incompleto
- validacao ainda em andamento
- quando for util revisar direcao antes do merge

## 5. Significado dos status no board

- `Backlog`: item conhecido, mas ainda nao pronto para execucao
- `Ready`: item pequeno o suficiente, claro o suficiente e sem bloqueio imediato
- `In Progress`: item efetivamente em execucao
- `In Review`: item concluido tecnicamente e aguardando revisao final
- `Done`: item validado, documentado quando necessario e encerrado

Regra operacional:
- manter no maximo 1 item ativo em `In Progress` por vez, salvo excecao consciente
- `Ready` deve conter poucos itens, priorizados e executaveis

## 6. Semantica de prioridade e tamanho

Prioridade:
- `P0`: bloqueador imediato do proximo passo do projeto
- `P1`: proxima frente relevante apos os bloqueadores imediatos
- `P2`: frente posterior ou dependente de etapas anteriores

Tamanho:
- `XS` e `S`: candidatos naturais a `Ready`
- `M`: aceitavel em `Ready` se tiver escopo claro
- `L`: revisar se da para quebrar antes de executar
- `XL`: tratar como epic; nao deve ir direto para `In Progress`

Regra para `XL`:
- todo item `XL` deve ser desdobrado em issues menores antes de entrar em execucao

## 7. Criterios minimos para um item entrar em Ready

Um item so deve sair de `Backlog` quando:
- o objetivo estiver claro
- a dependencia principal estiver resolvida
- o criterio de aceite estiver compreensivel
- o tamanho estiver adequado para execucao
- a referencia documental estiver identificada quando aplicavel

## 8. Definition of Done geral

Um item pode ser considerado pronto quando:
- atende ao objetivo definido
- teve o escopo realmente concluido, e nao parcialmente deslocado
- a rastreabilidade foi mantida entre codigo, documentacao e board quando aplicavel
- nao deixa segredo hardcoded, configuracao improvisada ou passo manual oculto
- foi validado por meio compativel com o tipo de mudanca
- a descricao do PR ou do fechamento explica como verificar

## 9. Definition of Done por tipo

### Backend

- codigo organizado de acordo com a arquitetura atual do projeto
- validacoes e contratos HTTP coerentes com o caso de uso
- erros relevantes tratados de forma consistente
- configuracao externa quando houver parametro sensivel
- testes automatizados adicionados ou atualizados quando a mudanca alterar comportamento

### Frontend

- fluxo implementado com semantica HTML adequada
- navegacao por teclado e foco visivel considerados
- labels, mensagens de erro e estados basicos de carregamento previstos
- integracao coerente com o contrato da API
- comportamento minimo em desktop e mobile verificado

### Infra e DevOps

- automacao versionada no repositorio
- configuracoes sensiveis externalizadas
- execucao reprodutivel e com verificacao minima documentada
- impacto operacional, rollback ou diagnostico explicados quando relevante

### Documentacao

- texto coerente com o estado real do projeto
- links e referencias atualizados
- artefato oficial ajustado, sem depender apenas de anotacao pessoal
- impacto no board e na narrativa tecnica refletido quando necessario

## 10. Checklist minimo de revisao antes do merge

- o titulo do PR representa o escopo real
- existe referencia a issue ou objetivo rastreavel
- o diff nao mistura temas desconexos
- validacoes locais e/ou CI passaram
- nao ha segredos, credenciais ou artefatos indevidos
- README, docs e board foram atualizados quando a mudanca exigia isso

## 11. Relacao com AGENTS e GitHub Projects

Este documento define o contrato de entrega do repositorio.

`AGENTS.md` continua definindo como agentes de IA devem se comportar.

O GitHub Projects continua sendo a camada operacional de status e prioridade.

Quando houver duvida:
1. `AGENTS.md` orienta comportamento do agente
2. este documento orienta colaboracao e conclusao de trabalho
3. o board reflete a execucao operacional
