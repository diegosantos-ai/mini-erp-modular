# Planejamento, Priorizacao e Riscos

## 1. Objetivo do documento

Organizar a execucao do projeto em epicos e fases, registrar riscos e restricoes, e definir como o trabalho sera acompanhado via GitHub Projects.

## 2. Estrategia de execucao

Direcao geral:
- documentar antes de implementar
- validar escopo minimo e arquitetura
- construir uma fundacao operacional antes de ampliar dominio
- priorizar fluxo ponta a ponta em vez de modulos isolados
- transformar o projeto em evidencia pratica de operacao de aplicacao Java em nuvem

## 3. Epicos iniciais

- `E-01`: Fundacao do repositorio e padroes de engenharia
- `E-02`: Identidade, acesso e perfis
- `E-03`: Cadastro de produtos e fornecedores
- `E-04`: Requisicao de compra e aprovacao
- `E-05`: Pedido de compra
- `E-06`: Recebimento e estoque
- `E-07`: Auditoria e dashboard operacional
- `E-08`: Containerizacao, infraestrutura e deploy em OVHcloud
- `E-09`: Observabilidade, troubleshooting, seguranca e hardening
- `E-10`: Documentacao final e preparo para demonstracao

## 4. Ordem sugerida de priorizacao

1. fundacao documental e arquitetural
2. backlog inicial e configuracao do GitHub Projects
3. estrutura base de build, padroes e automacao local
4. autenticacao e autorizacao
5. fluxo principal de requisicao, aprovacao e pedido
6. recebimento e estoque
7. auditoria e dashboard
8. infraestrutura e deploy em nuvem
9. endurecimento operacional e qualidade

## 5. Milestones sugeridos

### M0 - Planejamento e alinhamento

Saidas esperadas:
- documentacao base aprovada
- backlog inicial definido
- direcao arquitetural validada

### M1 - Fundacao tecnica

Saidas esperadas:
- padroes de projeto definidos
- automacao local inicial por Make
- pipeline de CI inicial
- stack Spring Boot definida e ambiente Linux alvo esclarecido

### M2 - Fluxo principal de compras

Saidas esperadas:
- requisicao, aprovacao e pedido funcionando

### M3 - Recebimento e estoque

Saidas esperadas:
- recebimento registrado e refletido em estoque

### M4 - Operacao e nuvem

Saidas esperadas:
- Docker, Terraform, Ansible e deploy validado na OVHcloud
- base de troubleshooting e observabilidade funcionando

### M5 - Qualidade e apresentacao

Saidas esperadas:
- observabilidade minima
- documentacao final
- roteiro de demonstracao pronto
- trilha de evolucao para Kubernetes documentada e, se viavel, validada em laboratorio

## 6. Uso do GitHub Projects

Orientacao proposta:
- o GitHub Projects sera a fonte operacional de status e priorizacao detalhada
- epicos, milestones e riscos continuam refletidos na documentacao
- cada issue deve estar vinculada a um objetivo claro e a um epic

Campos recomendados no board:
- status
- prioridade
- epic
- milestone
- tipo
- risco
- owner

Fluxo sugerido de status:
`Backlog -> Ready -> In Progress -> Review -> Done`

Tipos de item sugeridos:
- feature
- tech task
- infra
- bug
- doc

Regras operacionais do board:
- `P0` significa bloqueador imediato do proximo passo executavel, nao apenas importancia abstrata
- `P1` significa proxima frente relevante apos os bloqueadores imediatos
- `P2` significa trabalho posterior ou dependente de etapas anteriores
- itens `XL` devem ser tratados como epicos e quebrados antes de entrar em `Ready`
- `Ready` deve conter somente itens pequenos o suficiente e sem bloqueio imediato
- `In Progress` deve refletir trabalho realmente em execucao, idealmente um item ativo por vez

Referencia complementar:
- ver `08-definition-of-done-e-convencoes.md`

## 7. Registro inicial de riscos

- `R-001`: escopo maior do que a capacidade de entrega individual
  Mitigacao: manter MVP estrito e tratar funcionalidades extras como backlog futuro

- `R-002`: excesso de foco em ferramenta e pouco foco no dominio
  Mitigacao: priorizar fluxo ponta a ponta antes de sofisticar stack

- `R-003`: complexidade prematura de arquitetura
  Mitigacao: iniciar com monolito modular e registrar ADRs para evolucao

- `R-004`: baixa rastreabilidade entre decisao e implementacao
  Mitigacao: manter docs, ADRs e backlog sempre conectados

- `R-005`: pipeline e deploy atrasarem o desenvolvimento funcional
  Mitigacao: evoluir a esteira em fatias e com criterios minimos por milestone

- `R-006`: acessibilidade ser tratada tarde demais
  Mitigacao: incluir requisitos e checklist desde o inicio das telas

- `R-007`: custo ou complexidade da nuvem inviabilizar demonstracao
  Mitigacao: preferir topologia simples e baixo custo operacional

## 8. Restricoes conhecidas

- projeto desenvolvido principalmente por uma pessoa em contexto de aprendizado
- necessidade de manter o escopo compatível com portfolio executavel
- Java deve ser a linguagem principal do backend
- o projeto precisa incluir backend, API, frontend, Docker, Terraform, Ansible, CI/CD e Make
- documentacao deve preceder a implementacao relevante

## 9. Dependencias e pre-condicoes

- definicao de stack final do frontend
- configuracao do repositorio e do board no GitHub
- estrategia de segredos para pipeline e deploy
- decisao formal da topologia inicial na OVHcloud

## 10. Criterios para sair do planejamento

- documentos base revisados
- epicos e milestones refletidos no GitHub Projects
- arquitetura candidata validada
- riscos principais compreendidos
- backlog do primeiro milestone preparado

## 11. Evidencias de portfolio alinhadas a vagas de plataforma

O projeto deve produzir evidencias praticas para os seguintes eixos:
- operacao de aplicacao Java/Spring Boot
- pipeline CI/CD com GitHub Actions
- infraestrutura versionada com Terraform
- deploy e configuracao com Ansible
- ambiente Linux em nuvem na OVHcloud
- Docker no MVP e Kubernetes como trilha de evolucao
- observabilidade, troubleshooting e diagnostico tecnico
