# Mini-ERP de Compras, Estoque e Recebimento

![Status](https://img.shields.io/badge/status-foundation_in_progress-334155?style=flat-square)
![Java](https://img.shields.io/badge/Java-17-334155?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3-334155?style=flat-square&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-334155?style=flat-square&logo=springsecurity&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-migrations-334155?style=flat-square&logo=flyway&logoColor=white)
![H2](https://img.shields.io/badge/H2-dev_database-334155?style=flat-square)
![Actuator](https://img.shields.io/badge/Actuator-healthcheck-334155?style=flat-square)
![Docker](https://img.shields.io/badge/Docker-roadmap-334155?style=flat-square&logo=docker&logoColor=white)
![Terraform](https://img.shields.io/badge/Terraform-roadmap-334155?style=flat-square&logo=terraform&logoColor=white)
![Ansible](https://img.shields.io/badge/Ansible-roadmap-334155?style=flat-square&logo=ansible&logoColor=white)
![OVHcloud](https://img.shields.io/badge/OVHcloud-target-334155?style=flat-square)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-roadmap-334155?style=flat-square&logo=githubactions&logoColor=white)

Projeto de estudo e portfólio orientado a engenharia de software e DevOps, com foco na construção de um Mini-ERP web para o fluxo de compras, recebimento e controle de estoque.

O objetivo do repositório é demonstrar a evolução de um sistema corporativo pequeno com Java como linguagem principal, combinando documentação, modelagem, backend, segurança, observabilidade, automação e preparação para deploy em nuvem.

## Status

O projeto já saiu da fase exclusivamente documental e entrou na construção da fundação técnica do backend.

Base implementada até aqui:
- aplicação Spring Boot estruturada em estilo de monólito modular
- módulo de identidade e acesso com `User`, `Role` e `RoleName`
- persistência com Spring Data JPA
- migração inicial do banco com Flyway
- autenticação stateless com JWT
- endpoints `POST /api/auth/login` e `GET /api/auth/me`
- validação de entrada com Bean Validation
- tratamento coerente de erros para autenticação e request inválido
- endpoint operacional `GET /actuator/health`
- testes automatizados da fundação de autenticação e health
- automação local com `Makefile`
- pipeline inicial de CI com GitHub Actions

## Escopo do Produto

Fluxo de negócio previsto para o MVP:

```text
Requisição de Compra -> Aprovação -> Pedido de Compra -> Recebimento -> Entrada em Estoque -> Auditoria e Consulta
```

Capacidades-alvo do sistema:
- cadastro de produtos e fornecedores
- requisição de compra com aprovação
- pedido de compra e acompanhamento de recebimento
- movimentação e consulta de estoque
- autenticação, autorização e auditoria
- frontend web acessível
- deploy em nuvem com esteira reprodutível

## O Que Já Existe no Código

O backend atual já demonstra uma base funcional de segurança e operação:
- autenticação com email e senha
- geração e validação de bearer token JWT
- consulta ao usuário autenticado
- bootstrap inicial de perfis e usuário administrador controlado por configuração
- versionamento da estrutura de banco desde o início
- health check para uso futuro em deploy, monitoramento e smoke tests
- contrato consistente de `401` para falhas de autenticação no fluxo protegido
- cobertura automatizada do fluxo mínimo de autenticação

Principais endpoints disponíveis hoje:

```text
POST /api/auth/login
GET  /api/auth/me
GET  /actuator/health
```

## Stack Atual

Implementado neste estágio:
- Java 17
- Spring Boot
- Spring Web
- Spring Security
- Spring Data JPA
- Flyway
- H2
- Bean Validation
- Spring Boot Actuator
- JWT com `jjwt`
- Makefile
- GitHub Actions CI inicial

Planejado para as próximas etapas:
- PostgreSQL
- Docker e Docker Compose
- Terraform
- Ansible
- OVHcloud
- observabilidade mais profunda
- evolução para validação em Kubernetes

## Execução Local

Pré-requisitos:
- Java 17
- Maven

Antes de subir a aplicação localmente, configure as variáveis de ambiente mínimas obrigatórias:

- `JWT_SECRET`: segredo usado para assinar os tokens JWT. Deve ser um valor **Base64 válido** com pelo menos 32 bytes decodificados para HS256.
- `IDENTITY_BOOTSTRAP_ADMIN_ENABLED=true`: habilita a criação do usuário administrador no bootstrap.
- `IDENTITY_BOOTSTRAP_ADMIN_EMAIL`: email do admin inicial.
- `IDENTITY_BOOTSTRAP_ADMIN_PASSWORD`: senha do admin inicial.
- `IDENTITY_BOOTSTRAP_ADMIN_NAME`: nome do admin inicial. Opcional; se omitido, usa `Administrador`.

Exemplo (Linux/macOS):

```bash
export JWT_SECRET="$(openssl rand -base64 32)"
export IDENTITY_BOOTSTRAP_ADMIN_ENABLED=true
export IDENTITY_BOOTSTRAP_ADMIN_EMAIL="admin@minierp.local"
export IDENTITY_BOOTSTRAP_ADMIN_PASSWORD="<defina-uma-senha-local>"
export IDENTITY_BOOTSTRAP_ADMIN_NAME="Administrador"
mvn spring-boot:run
```

Exemplo (PowerShell):

```powershell
$bytes = New-Object byte[] 32
[System.Security.Cryptography.RandomNumberGenerator]::Fill($bytes)
$env:JWT_SECRET = [Convert]::ToBase64String($bytes)
$env:IDENTITY_BOOTSTRAP_ADMIN_ENABLED="true"
$env:IDENTITY_BOOTSTRAP_ADMIN_EMAIL="admin@minierp.local"
$env:IDENTITY_BOOTSTRAP_ADMIN_PASSWORD="<defina-uma-senha-local>"
$env:IDENTITY_BOOTSTRAP_ADMIN_NAME="Administrador"
mvn spring-boot:run
```

> Em ambiente local, gere o `JWT_SECRET` dinamicamente e não versione segredos reais nem senhas de bootstrap.

Health check:

```bash
curl http://localhost:8080/actuator/health
```

Automação local com Make:

```bash
make lint
make run
make test
make ci
```

Pre-commit estruturado:

```bash
pip install pre-commit
make pre-commit-install
make pre-commit-run
```

Login local:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"${IDENTITY_BOOTSTRAP_ADMIN_EMAIL}\",\"password\":\"${IDENTITY_BOOTSTRAP_ADMIN_PASSWORD}\"}"
```

Observação:
o arquivo [application.properties](./src/main/resources/application.properties) não substitui a configuração mínima acima para o fluxo documentado no `README`. O arquivo [application-example.properties](./src/main/resources/application-example.properties) documenta o formato esperado para configuração externa por variáveis de ambiente.

## Foco de Engenharia

O projeto foi desenhado para exercitar:
- operação de aplicações Java/Spring Boot em ambiente real
- organização modular de backend corporativo
- autenticação, autorização e tratamento coerente de erros
- versionamento de banco com migrações
- health check e base operacional mínima
- documentação antes e durante a implementação
- evolução planejada para Docker, infraestrutura como código, CI/CD e cloud

## Documentação

A documentação formal do projeto está centralizada em [docs/README.md](./docs/README.md).

Leitura recomendada:
1. [Visão do Produto](./docs/01-visao-produto.md)
2. [Requisitos e Processos](./docs/02-requisitos-processos.md)
3. [Arquitetura](./docs/03-arquitetura.md)
4. [Dados e Qualidade](./docs/04-dados-qualidade.md)
5. [Planejamento, Priorização e Riscos](./docs/05-planejamento-riscos.md)
6. [Governança de Agentes de IA](./docs/06-governanca-agentes-ia.md)
7. [Observabilidade e Operação](./docs/07-observabilidade-operacao.md)
8. [ADR-001](./docs/adr/ADR-001-monolito-modular-devops-first.md)
9. [ADR-003](./docs/adr/ADR-003-adotar-governanca-tutor-first-para-agentes-de-ia.md)
10. [ADR-004](./docs/adr/ADR-004-adotar-ovhcloud-single-host-com-evolucao-para-kubernetes.md)

## Estrutura do Repositório

```text
.
|-- AGENTS.md
|-- docs/
|-- .github/
|-- Makefile
|-- src/
|   |-- main/
|   |   |-- java/
|   |   `-- resources/
|-- pom.xml
`-- README.md
```

## Próximos Passos

Próxima frente natural de evolução:
- autorização por perfis em endpoints reais de negócio
- início dos módulos de compras, recebimento e estoque
- logs estruturados e runbooks básicos de troubleshooting
- troca do banco de desenvolvimento para algo mais próximo do ambiente-alvo
- containerização e preparação de deploy

O `README.md` da raiz funciona como a vitrine executiva e técnica do repositório.

O `docs/README.md` funciona como o índice da documentação oficial.
