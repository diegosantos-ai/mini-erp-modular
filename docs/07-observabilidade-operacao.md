# Observabilidade e Operacao

## 1. Objetivo do documento

Definir a estrategia inicial de observabilidade, diagnostico e troubleshooting do MVP, conectando a implementacao atual do backend com a futura operacao em ambiente Linux e OVHcloud.

## 2. Principios adotados

- observabilidade faz parte da arquitetura, nao entra apenas no deploy
- o MVP deve ser operavel mesmo antes de existir uma stack completa de monitoramento
- logs, health checks e testes de smoke devem produzir evidencias claras para diagnostico
- o ambiente remoto deve privilegiar simplicidade operacional e baixo custo
- toda capacidade operacional precisa ser explicavel e reproduzivel

## 3. Estado atual da fundacao tecnica

Capacidades ja presentes no repositorio:
- endpoint `GET /actuator/health`
- autenticacao JWT com respostas `401` coerentes no fluxo protegido
- tratamento padronizado de erros de autenticacao e validacao
- testes automatizados da fundacao de autenticacao e health check
- automacao local com `Makefile`
- pipeline inicial de CI com build e testes

Limite atual:
- ainda nao existe agregacao centralizada de logs
- ainda nao existem metricas de negocio ou dashboards
- ainda nao existe tracing distribuido
- ainda nao existem runbooks versionados fora deste documento

## 4. Estrategia inicial do MVP

### 4.1 Logs

Direcao inicial:
- a aplicacao deve escrever logs em `stdout`
- o container e o runtime do host serao a fonte primaria de coleta
- erros de autenticacao, falhas de startup e exceptions precisam ser compreensiveis
- logs devem permitir diagnostico de configuracao, porta, conectividade e falha de request

Padrao operacional inicial:
- ambiente local: leitura direta no terminal
- ambiente remoto inicial: leitura via logs do processo ou do container
- proxy reverso: logs de acesso e erro separados da aplicacao

Evolucao planejada:
- adotar logs estruturados em JSON
- adicionar correlacao basica por request
- separar eventos tecnicos de eventos de auditoria de negocio

### 4.2 Health checks

Direcao inicial:
- `GET /actuator/health` sera o endpoint minimo de saude tecnica
- smoke tests locais, CI e deploy devem validar esse endpoint
- health check deve ser usado como evidencia de startup bem-sucedido

Uso esperado:
- local: validacao rapida apos `make run`
- pipeline: smoke tecnico antes de ampliar a esteira
- deploy em OVHcloud: verificacao de disponibilidade da aplicacao

Evolucao planejada:
- diferenciar readiness e liveness quando a topologia exigir
- incluir dependencias relevantes, como banco, em checagens mais detalhadas
- preparar probes para a trilha futura em Kubernetes

### 4.3 Metricas

Direcao inicial:
- comecar por metricas tecnicas simples antes de metricas de negocio
- priorizar disponibilidade, taxa de erro e tempo de resposta
- evitar expor endpoints operacionais desnecessarios em ambiente publico sem protecao

Metrica minima desejada no proximo passo:
- latencia basica de requests
- contagem de erros HTTP por classe
- tempo de inicializacao da aplicacao
- consumo basico de recursos do host e dos containers

Evolucao planejada:
- integrar Micrometer com stack de coleta adequada ao ambiente
- definir paines simples para health, erros e latencia
- introduzir metricas de dominio quando os modulos de compras e estoque avancarem

## 5. Troubleshooting inicial

### 5.1 Startup

Checklist inicial:
- validar `JWT_SECRET` e configuracoes obrigatorias
- verificar se a migracao Flyway executou com sucesso
- confirmar abertura da porta HTTP esperada
- chamar `GET /actuator/health`

### 5.2 Autenticacao

Checklist inicial:
- validar se o usuario existe e esta ativo
- confirmar se o login retorna token
- testar `/api/auth/me` com e sem token
- revisar mensagens `401` para diferenciar token invalido de usuario inativo

### 5.3 Ambiente e rede

Checklist inicial:
- conferir bind de porta e endereco
- validar comunicacao entre aplicacao, banco e proxy reverso
- revisar DNS, cabecalhos e TLS quando o deploy remoto entrar em operacao

### 5.4 Performance basica

Checklist inicial:
- observar tempo de resposta do login e do health check
- revisar logs em caso de timeout ou lentidao
- acompanhar consumo de memoria e CPU do processo Java no host

## 6. Evidencias operacionais esperadas

O projeto deve ser capaz de demonstrar:
- aplicacao Spring Boot subindo com configuracao valida
- health check respondendo em ambiente local e em nuvem
- falhas de autenticacao e configuracao sendo diagnosticaveis
- pipeline impedindo promocao de base quebrada
- trilha clara de evolucao para monitoramento mais profundo

## 7. Proximos incrementos recomendados

1. adicionar runbook curto de startup e autenticacao no repositorio
2. introduzir logs estruturados
3. ampliar smoke tests pos-deploy
4. definir stack de coleta para ambiente OVHcloud
5. preparar readiness/liveness para a fase de Kubernetes
