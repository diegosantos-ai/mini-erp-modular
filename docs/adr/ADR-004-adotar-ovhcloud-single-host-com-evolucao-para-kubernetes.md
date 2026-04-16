# ADR-004: Adotar OVHcloud single-host no primeiro deploy com evolucao para Kubernetes

## Status

Accepted

## Contexto

O projeto precisa equilibrar:
- aprendizado pratico de engenharia de plataforma e DevOps
- viabilidade para uma pessoa em contexto de portfolio
- operacao real de uma aplicacao Java/Spring Boot em nuvem
- alinhamento com competencias valorizadas em vagas de plataforma, automacao, cloud e confiabilidade

Tambem existe a restricao favoravel de disponibilidade previa de conta na OVHcloud, o que reduz atrito operacional no primeiro deploy.

## Decisao

Adotar no primeiro deploy:
- OVHcloud Public Cloud como provedor principal
- uma topologia inicial `single-host` em Linux
- Docker Compose como runtime inicial
- Terraform para provisionamento
- Ansible para bootstrap e deploy
- GitHub Actions para pipeline de entrega
- PostgreSQL em container no primeiro deploy

Planejar a evolucao para:
- Managed PostgreSQL da OVHcloud quando o projeto exigir maior maturidade operacional
- OVHcloud Managed Kubernetes Service para validacao de orquestracao e trilha de maturidade

## Alternativas consideradas

### Partir diretamente para Kubernetes

Vantagens:
- aderencia imediata a vagas que pedem orquestracao
- maior proximidade com topologias mais maduras

Desvantagens:
- aumenta custo cognitivo e operacional cedo demais
- prejudica foco em fundacao de build, deploy, observabilidade e troubleshooting

### Usar banco gerenciado desde o primeiro deploy

Vantagens:
- reduz responsabilidade operacional inicial do banco
- aproxima o projeto de uma postura mais production-like

Desvantagens:
- reduz aprendizado sobre runtime, volumes, backup e rede entre servicos
- adiciona custo mais cedo

### Escolher outro provedor cloud no primeiro ciclo

Vantagens:
- maior aderencia nominal a algumas vagas

Desvantagens:
- mais atrito inicial
- menor velocidade para validar o fluxo completo

## Consequencias

Consequencias positivas:
- simplicidade operacional suficiente para um primeiro deploy real
- boa relacao entre aprendizado e viabilidade
- cadeia completa de entrega e operacao mais visivel
- caminho claro de evolucao para servicos gerenciados e Kubernetes

Consequencias negativas:
- primeiro deploy nao representa alta disponibilidade
- ambiente inicial nao demonstra orquestracao completa
- banco no mesmo host aumenta blast radius

## Resultado esperado

Um primeiro ambiente remoto executavel, reproducivel e observavel, capaz de demonstrar operacao de aplicacao Java/Spring Boot com Docker, Terraform, Ansible e GitHub Actions na OVHcloud, sem bloquear a evolucao futura para Kubernetes.

## Revisao futura

Reavaliar esta decisao quando:
- o deploy inicial em nuvem estiver estavel
- o projeto demandar maior resiliencia ou topologia distribuida
- houver necessidade de evidenciar Kubernetes de forma pratica para objetivos profissionais
