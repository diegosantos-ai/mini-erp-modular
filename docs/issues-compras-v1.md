# Plano — Release v1.0.0 Mini-ERP de Compras

Documento de planejamento para fechamento da primeira release funcional do projeto.

Este arquivo consolida a decomposicao tecnica da release. A execucao operacional, o status de cada item, prioridade, tamanho, fase e movimentacao de trabalho devem acontecer exclusivamente no GitHub Projects.

## Objetivo da Release

Entregar um MVP funcional de Mini-ERP de Compras com backend Java/Spring Boot, autenticação, banco versionado, fluxo de compras, recebimento, estoque, auditoria, documentação de API, CI e fechamento formal de release.

## Milestone sugerida

`v1.0.0 - Mini-ERP Compras MVP`

## Fonte operacional de verdade

- GitHub Projects é a fonte oficial para status, prioridade e ordem de execucao.
- Este documento descreve o plano final da release, criterios de aceite e agrupamento sugerido.
- Os identificadores `V1-xx` abaixo sao pacotes de trabalho da release, nao numeros reais de issues do GitHub.
- As issues reais devem ser criadas ou atualizadas no GitHub Projects a partir deste plano.
- RH/Folha fica fora da `v1.0.0`; so deve entrar apos Compras estar validado, implantado e demonstravel.

## Mapeamento atual no GitHub Projects

- `#5` Definir stack final do frontend: decisao pendente antes da interface da v1.
- `#13` E-03 Cadastro de produtos e fornecedores: epic para `V1-04` e `V1-05`.
- `#14` E-04 Requisicao de compra e aprovacao: epic para `V1-06` e `V1-07`.
- `#15` E-05 Pedido de compra: epic para `V1-08`.
- `#16` E-06 Recebimento e estoque: epic para `V1-09` e `V1-10`.
- `#17` E-07 Auditoria e dashboard operacional: epic para `V1-11`.
- `#18` E-08 Containerizacao, infraestrutura e deploy em OVHcloud: epic operacional de deploy.
- `#19` E-09 Observabilidade, troubleshooting, seguranca e hardening: epic operacional de observabilidade e hardening.
- `#20` E-10 Documentacao final e demonstracao: epic para fechamento de release e portfolio.
- `#7` Definir ponto de entrada da validacao em Kubernetes: fora do caminho critico da v1.

## Labels sugeridas

- `type: feature`
- `type: docs`
- `type: test`
- `type: refactor`
- `type: chore`
- `area: identity`
- `area: purchasing`
- `area: inventory`
- `area: security`
- `area: devops`
- `area: docs`
- `priority: high`
- `priority: medium`
- `priority: low`

## Checklist macro

- [x] V1-01 Revisar base atual e documentar status tecnico
- [x] V1-02 Configurar PostgreSQL com Docker Compose
- [x] V1-03 Criar testes de autenticacao e autorizacao
- [x] V1-04 Implementar cadastro de fornecedores
- [ ] V1-05 Implementar cadastro de produtos
- [ ] V1-06 Implementar requisicao de compra
- [ ] V1-07 Implementar aprovacao/rejeicao de requisicao
- [ ] V1-08 Implementar pedido de compra
- [ ] V1-09 Implementar recebimento de mercadoria
- [ ] V1-10 Implementar movimentacao de estoque
- [ ] V1-11 Implementar auditoria de acoes criticas
- [ ] V1-12 Adicionar OpenAPI/Swagger
- [x] V1-13 Configurar pipeline CI inicial
- [ ] V1-14 Implementar frontend minimo da v1
- [ ] V1-15 Containerizar e validar execucao local completa
- [ ] V1-16 Fazer deploy inicial em OVHcloud
- [ ] V1-17 Fechar release v1.0.0

---

# V1-01 — Revisar base atual e documentar status tecnico [REALIZADO]

## Tipo

`type: docs`  
`area: docs`  
`priority: high`

## Branch sugerida

`feature/revisao-base-arquitetura`

## Objetivo

Registrar o estado técnico atual do projeto antes da implementação dos módulos de negócio.

## Contexto

Antes de evoluir o fluxo de compras, é necessário documentar o que já existe, o que está funcionando, quais lacunas permanecem e quais decisões arquiteturais devem orientar a Release `v1.0.0`.

## Escopo

- Revisar estrutura de pacotes.
- Revisar configuração atual de autenticação e autorização.
- Revisar migrations existentes.
- Revisar configurações de ambiente.
- Revisar endpoints já disponíveis.
- Revisar documentação existente.
- Criar documento `docs/status-tecnico.md`.

## Fora de escopo

- Alterar regra de negócio.
- Criar novas entidades de compras.
- Refatorar arquitetura sem necessidade validada.

## Critérios de aceite

- [x] Documento `docs/status-tecnico.md` criado.
- [x] Estado atual do projeto descrito.
- [x] Lacunas da Release `v1.0.0` listadas.
- [x] Decisao registrada: finalizar fluxo de compras antes do modulo RH/Folha.
- [x] Proxima fase tecnica definida.

## Validação

- [x] Documento revisado no repositorio.
- [x] Commit criado com mensagem objetiva.

## Commit sugerido

```bash
git add docs/status-tecnico.md
git commit -m "docs: registra status técnico do mini erp"
```

---

# V1-02 — Configurar PostgreSQL com Docker Compose [REALIZADO]

## Tipo

`type: chore`  
`area: devops`  
`priority: high`

## Branch sugerida

`feature/postgres-docker-compose`

## Objetivo

Configurar PostgreSQL local via Docker Compose para aproximar o ambiente de desenvolvimento de um cenário real de execução.

## Contexto

A aplicação precisa sair de uma configuração baseada apenas em banco em memória e passar a validar migrations, persistência e execução local com PostgreSQL.

## Escopo

- Criar `docker-compose.yml` com PostgreSQL.
- Criar `.env.example`.
- Criar profile local, se necessário.
- Configurar datasource local.
- Garantir execução do Flyway no PostgreSQL.
- Atualizar README com instruções mínimas de execução.

## Fora de escopo

- Deploy em cloud.
- CI/CD.
- Testcontainers.
- Observabilidade avançada.

## Critérios de aceite

- [x] `docker compose up -d` sobe o PostgreSQL.
- [x] Aplicação sobe com profile local.
- [x] Flyway executa migrations no PostgreSQL.
- [x] `/actuator/health` retorna `UP`.
- [x] Login com usuário bootstrap funciona.
- [x] README atualizado com instruções de execução local.

## Validação

```bash
docker compose up -d
mvn clean test
mvn spring-boot:run
curl http://localhost:8080/actuator/health
```

## Commit sugerido

```bash
git add .
git commit -m "chore: configura postgres local com docker compose"
```

---

# V1-03 — Criar testes de autenticacao e autorizacao [REALIZADO]

## Tipo

`type: test`  
`area: identity`  
`area: security`  
`priority: high`

## Branch sugerida

`feature/testes-auth-postgres`

## Objetivo

Criar testes automatizados para validar autenticação, geração de token, endpoint de usuário autenticado e bloqueio de acesso sem credenciais.

## Contexto

Antes de expandir domínio de negócio, o módulo de identidade precisa estar protegido por testes para reduzir regressões nas próximas fases.

## Escopo

- Testar login com credenciais válidas.
- Testar login com credenciais inválidas.
- Testar acesso ao endpoint `/api/auth/me` com token válido.
- Testar bloqueio de endpoint protegido sem token.
- Testar comportamento esperado para usuário inexistente ou senha inválida.

## Fora de escopo

- Testes de compras.
- Testes de estoque.
- Testes de carga.

## Critérios de aceite

- [x] Testes de autenticacao criados.
- [x] Testes de autorizacao basica criados.
- [x] Suite passa com `mvn clean test`.
- [x] Nomes dos testes descrevem comportamento esperado.
- [x] Nenhum segredo real versionado.

## Validação

```bash
mvn clean test
```

## Commit sugerido

```bash
git add .
git commit -m "test: cobre fluxo de autenticacao e autorizacao"
```

---

# V1-04 — Implementar cadastro de fornecedores [REALIZADO]

## Tipo

`type: feature`  
`area: purchasing`  
`priority: high`

## Branch sugerida

`feature/compras-cadastro-fornecedores`

## Objetivo

Implementar o cadastro de fornecedores como entidade base do fluxo de compras.

## Contexto

O fornecedor será utilizado posteriormente na criação de pedidos de compra e no histórico de aquisição.

## Escopo

- Criar entidade `Supplier`.
- Criar migration de tabela de fornecedores.
- Criar repository.
- Criar service.
- Criar DTOs de request/response.
- Criar controller.
- Criar validações de entrada.
- Implementar ativação/inativação lógica, se aplicável.

## Campos mínimos sugeridos

- `id`
- `name`
- `documentNumber`
- `email`
- `phone`
- `active`
- `createdAt`
- `updatedAt`

## Endpoints sugeridos

```http
POST   /api/suppliers
GET    /api/suppliers
GET    /api/suppliers/{id}
PUT    /api/suppliers/{id}
PATCH  /api/suppliers/{id}/deactivate
```

## Fora de escopo

- Integração externa com cadastro fiscal.
- Validação real de CNPJ em serviço externo.
- Pedido de compra.

## Critérios de aceite

- [x] Fornecedor pode ser cadastrado.
- [x] Fornecedor pode ser consultado por ID.
- [x] Lista de fornecedores retorna registros ativos.
- [x] Fornecedor pode ser atualizado.
- [x] Campos obrigatórios possuem validação.
- [x] Migration criada sem alterar migrations antigas.
- [x] Testes básicos implementados.

## Evidências

- Entidade `Supplier`, repository, service, DTOs e controller criados no módulo `catalog`.
- Migration `V2__create_suppliers_table.sql` adicionada sem alterar migrations anteriores.
- Endpoints protegidos por JWT usando a configuração de segurança existente.
- Testes de integração adicionados para criação, consulta, listagem de ativos, atualização, inativação, validação e duplicidade.
- Validação executada com sucesso: `make ci`.

## Validação

```bash
mvn clean test
```

## Commit sugerido

```bash
git add .
git commit -m "feat: implementa cadastro de fornecedores"
```

---

# V1-05 — Implementar cadastro de produtos

## Tipo

`type: feature`  
`area: purchasing`  
`area: inventory`  
`priority: high`

## Branch sugerida

`feature/compras-cadastro-produtos`

## Objetivo

Implementar o cadastro de produtos como base para requisições de compra, pedidos, recebimentos e movimentações de estoque.

## Contexto

O produto será usado em todo o fluxo operacional da Release `v1.0.0`.

## Escopo

- Criar entidade `Product`.
- Criar migration de tabela de produtos.
- Criar repository.
- Criar service.
- Criar DTOs de request/response.
- Criar controller.
- Criar validações de entrada.
- Implementar ativação/inativação lógica, se aplicável.

## Campos mínimos sugeridos

- `id`
- `sku`
- `name`
- `description`
- `unitOfMeasure`
- `active`
- `createdAt`
- `updatedAt`

## Endpoints sugeridos

```http
POST   /api/products
GET    /api/products
GET    /api/products/{id}
PUT    /api/products/{id}
PATCH  /api/products/{id}/deactivate
```

## Fora de escopo

- Controle de estoque.
- Precificação avançada.
- Categoria de produto.

## Critérios de aceite

- [ ] Produto pode ser cadastrado.
- [ ] Produto pode ser consultado por ID.
- [ ] Lista de produtos retorna registros ativos.
- [ ] Produto pode ser atualizado.
- [ ] SKU não pode ser duplicado.
- [ ] Migration criada sem alterar migrations antigas.
- [ ] Testes básicos implementados.

## Validação

```bash
mvn clean test
```

## Commit sugerido

```bash
git add .
git commit -m "feat: implementa cadastro de produtos"
```

---

# V1-06 — Implementar requisicao de compra

## Tipo

`type: feature`  
`area: purchasing`  
`priority: high`

## Branch sugerida

`feature/requisicao-compra`

## Objetivo

Implementar a criação e consulta de requisições de compra com itens.

## Contexto

A requisição de compra é o ponto de entrada do fluxo operacional do Mini-ERP.

## Escopo

- Criar entidade `PurchaseRequest`.
- Criar entidade `PurchaseRequestItem`.
- Criar enum `PurchaseRequestStatus`.
- Criar migration das tabelas relacionadas.
- Criar endpoints de criação e consulta.
- Permitir criação em status inicial `DRAFT`.
- Permitir submissão para aprovação.

## Status sugeridos

- `DRAFT`
- `SUBMITTED`
- `APPROVED`
- `REJECTED`
- `CANCELLED`

## Endpoints sugeridos

```http
POST /api/purchase-requests
GET  /api/purchase-requests
GET  /api/purchase-requests/{id}
POST /api/purchase-requests/{id}/submit
```

## Fora de escopo

- Aprovação/rejeição.
- Geração de pedido de compra.
- Recebimento.

## Critérios de aceite

- [ ] Requisição pode ser criada com itens.
- [ ] Requisição nasce como `DRAFT`.
- [ ] Requisição pode ser consultada por ID.
- [ ] Requisição pode ser listada.
- [ ] Requisição `DRAFT` pode ser submetida.
- [ ] Requisição sem itens não pode ser submetida.
- [ ] Itens devem referenciar produtos existentes e ativos.
- [ ] Testes de regra de status implementados.

## Validação

```bash
mvn clean test
```

## Commit sugerido

```bash
git add .
git commit -m "feat: implementa requisicao de compra"
```

---

# V1-07 — Implementar aprovacao/rejeicao de requisicao

## Tipo

`type: feature`  
`area: purchasing`  
`area: security`  
`priority: high`

## Branch sugerida

`feature/aprovacao-requisicao-compra`

## Objetivo

Implementar fluxo de aprovação e rejeição de requisições de compra.

## Contexto

A aprovação adiciona regra de negócio e controle de autorização ao fluxo de compras, tornando o projeto mais próximo de um cenário corporativo.

## Escopo

- Implementar endpoint de aprovação.
- Implementar endpoint de rejeição.
- Validar transições de status.
- Exigir perfil autorizado para aprovar/rejeitar.
- Registrar data, usuário e justificativa da decisão.

## Endpoints sugeridos

```http
POST /api/purchase-requests/{id}/approve
POST /api/purchase-requests/{id}/reject
```

## Fora de escopo

- Workflow multinível.
- Alçadas por valor.
- Notificações.

## Critérios de aceite

- [ ] Apenas requisição `SUBMITTED` pode ser aprovada.
- [ ] Apenas requisição `SUBMITTED` pode ser rejeitada.
- [ ] Usuário sem perfil autorizado não aprova nem rejeita.
- [ ] Aprovação registra usuário responsável.
- [ ] Rejeição registra justificativa.
- [ ] Transição inválida retorna erro de domínio.
- [ ] Testes de autorização e transição criados.

## Validação

```bash
mvn clean test
```

## Commit sugerido

```bash
git add .
git commit -m "feat: implementa aprovacao de requisicoes de compra"
```

---

# V1-08 — Implementar pedido de compra

## Tipo

`type: feature`  
`area: purchasing`  
`priority: high`

## Branch sugerida

`feature/pedido-compra`

## Objetivo

Implementar geração de pedido de compra a partir de uma requisição aprovada.

## Contexto

O pedido de compra formaliza a intenção de aquisição junto ao fornecedor e conecta requisição, fornecedor e itens comprados.

## Escopo

- Criar entidade `PurchaseOrder`.
- Criar entidade `PurchaseOrderItem`.
- Criar enum `PurchaseOrderStatus`.
- Criar migration das tabelas relacionadas.
- Criar endpoint para gerar pedido a partir de requisição aprovada.
- Criar endpoint de consulta do pedido.

## Status sugeridos

- `CREATED`
- `SENT`
- `PARTIALLY_RECEIVED`
- `RECEIVED`
- `CANCELLED`

## Endpoints sugeridos

```http
POST /api/purchase-orders/from-request/{requestId}
GET  /api/purchase-orders
GET  /api/purchase-orders/{id}
```

## Fora de escopo

- Envio real ao fornecedor.
- Integração externa.
- Recebimento de mercadoria.

## Critérios de aceite

- [ ] Pedido pode ser gerado a partir de requisição aprovada.
- [ ] Pedido não pode ser gerado a partir de requisição pendente, rejeitada ou cancelada.
- [ ] Pedido mantém vínculo com fornecedor e itens.
- [ ] Pedido pode ser consultado por ID.
- [ ] Pedido possui status inicial consistente.
- [ ] Testes de geração de pedido implementados.

## Validação

```bash
mvn clean test
```

## Commit sugerido

```bash
git add .
git commit -m "feat: implementa pedido de compra"
```

---

# V1-09 — Implementar recebimento de mercadoria

## Tipo

`type: feature`  
`area: purchasing`  
`area: inventory`  
`priority: high`

## Branch sugerida

`feature/recebimento-mercadoria`

## Objetivo

Implementar o registro de recebimento de mercadorias vinculadas a pedidos de compra.

## Contexto

O recebimento confirma a chegada física ou operacional dos itens adquiridos e prepara a entrada em estoque.

## Escopo

- Criar entidade `GoodsReceipt`.
- Criar entidade `GoodsReceiptItem`.
- Criar migration das tabelas relacionadas.
- Criar endpoint de registro de recebimento.
- Criar endpoint de consulta de recebimento.
- Validar quantidades recebidas contra itens do pedido.

## Endpoints sugeridos

```http
POST /api/goods-receipts
GET  /api/goods-receipts
GET  /api/goods-receipts/{id}
```

## Fora de escopo

- Conferência fiscal.
- Nota fiscal.
- Integração contábil.

## Critérios de aceite

- [ ] Recebimento pode ser registrado para pedido existente.
- [ ] Quantidade recebida não pode ser negativa ou zero.
- [ ] Recebimento não pode exceder quantidade pedida, salvo decisão explícita documentada.
- [ ] Pedido atualiza status para recebido ou parcialmente recebido.
- [ ] Recebimento pode ser consultado por ID.
- [ ] Testes de recebimento implementados.

## Validação

```bash
mvn clean test
```

## Commit sugerido

```bash
git add .
git commit -m "feat: implementa recebimento de mercadorias"
```

---

# V1-10 — Implementar movimentacao de estoque

## Tipo

`type: feature`  
`area: inventory`  
`priority: high`

## Branch sugerida

`feature/movimentacao-estoque`

## Objetivo

Implementar movimentação de estoque gerada a partir do recebimento de mercadoria.

## Contexto

A entrada de estoque fecha o fluxo mínimo de compras, conectando aquisição, recebimento e saldo disponível.

## Escopo

- Criar entidade `StockMovement`.
- Criar enum `StockMovementType`.
- Criar consulta de saldo por produto.
- Gerar movimentação `INBOUND` ao registrar recebimento.
- Criar endpoint de consulta de movimentos.

## Tipos sugeridos

- `INBOUND`
- `OUTBOUND`
- `ADJUSTMENT`

## Endpoints sugeridos

```http
GET /api/stock/products/{productId}
GET /api/stock/movements
GET /api/stock/movements/{id}
```

## Fora de escopo

- Baixa de estoque por venda.
- Inventário completo.
- Reserva de estoque.

## Critérios de aceite

- [ ] Recebimento gera movimento de entrada.
- [ ] Saldo do produto pode ser consultado.
- [ ] Histórico de movimentos pode ser consultado.
- [ ] Movimento mantém vínculo com origem operacional.
- [ ] Não é possível criar movimento inconsistente sem origem permitida.
- [ ] Testes de saldo e movimentação implementados.

## Validação

```bash
mvn clean test
```

## Commit sugerido

```bash
git add .
git commit -m "feat: implementa movimentacao de estoque"
```

---

# V1-11 — Implementar auditoria de acoes criticas

## Tipo

`type: feature`  
`area: security`  
`area: docs`  
`priority: medium`

## Branch sugerida

`feature/auditoria-acoes-criticas`

## Objetivo

Registrar ações críticas do fluxo de compras para garantir rastreabilidade operacional.

## Contexto

Sistemas corporativos precisam permitir investigação de decisões e mudanças relevantes, principalmente em processos de aprovação, recebimento e alteração de status.

## Escopo

- Criar estrutura de auditoria simples.
- Registrar eventos relevantes.
- Registrar usuário responsável.
- Registrar data/hora da ação.
- Registrar entidade afetada.
- Criar endpoint de consulta de histórico, se aplicável.

## Eventos mínimos

- Requisição submetida.
- Requisição aprovada.
- Requisição rejeitada.
- Pedido criado.
- Recebimento registrado.
- Movimento de estoque criado.

## Fora de escopo

- Event sourcing completo.
- Trilha imutável criptográfica.
- Integração com SIEM.

## Critérios de aceite

- [ ] Ações críticas geram registro de auditoria.
- [ ] Auditoria identifica tipo de ação.
- [ ] Auditoria identifica usuário responsável.
- [ ] Auditoria identifica entidade afetada.
- [ ] Auditoria pode ser consultada para investigação.
- [ ] Testes básicos criados.

## Validação

```bash
mvn clean test
```

## Commit sugerido

```bash
git add .
git commit -m "feat: adiciona auditoria de acoes criticas"
```

---

# V1-12 — Adicionar OpenAPI/Swagger

## Tipo

`type: docs`  
`area: docs`  
`priority: medium`

## Branch sugerida

`feature/openapi-swagger`

## Objetivo

Adicionar documentação navegável da API para facilitar validação, demonstração e uso do backend.

## Contexto

A documentação de API melhora a apresentação do projeto como backend corporativo e facilita testes manuais do fluxo de compras.

## Escopo

- Adicionar dependência do Springdoc/OpenAPI.
- Configurar título, descrição e versão da API.
- Documentar autenticação JWT no Swagger.
- Revisar nomes e contratos dos endpoints.
- Atualizar README com URL da documentação.

## Fora de escopo

- Portal externo de documentação.
- SDK client.
- Versionamento público de API.

## Critérios de aceite

- [ ] Swagger UI disponível localmente.
- [ ] OpenAPI JSON disponível localmente.
- [ ] Endpoints principais aparecem documentados.
- [ ] Autenticação JWT está documentada.
- [ ] README indica como acessar a documentação da API.

## Validação

```bash
mvn spring-boot:run
```

Acessar:

```text
http://localhost:8080/swagger-ui.html
```

ou a URL configurada pelo Springdoc.

## Commit sugerido

```bash
git add .
git commit -m "docs: adiciona documentacao openapi da api"
```

---

# V1-13 — Configurar pipeline CI inicial [REALIZADO]

## Tipo

`type: chore`  
`area: devops`  
`priority: high`

## Branch sugerida

`feature/ci-build-test`

## Objetivo

Configurar pipeline de integração contínua para build e testes automatizados do projeto.

## Contexto

A Release `v1.0.0` precisa demonstrar qualidade mínima de engenharia, validação automatizada e segurança no fluxo de mudança.

## Escopo

- Criar workflow GitHub Actions.
- Executar build Maven.
- Executar testes automatizados.
- Validar PRs contra branch principal.
- Adicionar badge de CI no README.

## Fora de escopo

- Deploy automatizado.
- Publicação de imagem Docker.
- Infraestrutura cloud.

## Critérios de aceite

- [x] Workflow criado em `.github/workflows/ci.yml`.
- [x] Pipeline executa em push e pull request.
- [x] Pipeline executa validacoes automatizadas via `make ci`.
- [x] Pipeline falha se testes ou validacoes falharem.
- [ ] Badge do CI adicionado ao README.

## Validação

- [ ] Abrir PR e confirmar execução do workflow.
- [ ] Conferir status do pipeline no GitHub Actions.

## Commit sugerido

```bash
git add .
git commit -m "ci: adiciona pipeline de build e testes"
```

---

# V1-14 — Implementar frontend minimo da v1

## Tipo

`type: feature`  
`area: frontend`  
`priority: high`

## Branch sugerida

`feature/frontend-compras-mvp`

## Objetivo

Criar uma interface web minima para demonstrar o fluxo de compras sem depender apenas de Swagger, curl ou scripts locais.

## Contexto

O produto foi planejado como Mini-ERP web. A API e suficiente para validar regras tecnicas, mas o portfolio fica mais forte se o fluxo principal puder ser demonstrado em uma interface simples e coerente.

## Escopo

- Definir stack final do frontend no GitHub Projects antes da implementacao.
- Criar tela de login.
- Criar telas basicas para fornecedores e produtos.
- Criar fluxo de requisicao, aprovacao, pedido e recebimento.
- Criar consulta minima de estoque.
- Tratar estados basicos de carregamento, erro e sucesso.

## Fora de escopo

- Dashboard analitico avancado.
- Design system completo.
- Multitenancy.

## Criterios de aceite

- [ ] Stack do frontend decidida e refletida no board.
- [ ] Login funcional usando JWT.
- [ ] Fluxo principal de compras demonstravel pela interface.
- [ ] Erros principais apresentados de forma compreensivel.
- [ ] README documenta como executar frontend e backend localmente.

## Validacao

```bash
mvn clean test
```

Executar o frontend conforme stack definida e validar manualmente o fluxo principal.

## Commit sugerido

```bash
git add .
git commit -m "feat: adiciona frontend minimo do fluxo de compras"
```

---

# V1-15 — Containerizar e validar execucao local completa

## Tipo

`type: chore`  
`area: devops`  
`priority: high`

## Branch sugerida

`infra/containerizacao-local`

## Objetivo

Permitir que backend, frontend e PostgreSQL sejam executados localmente de forma reprodutivel.

## Contexto

Antes do deploy em nuvem, o projeto precisa provar que a aplicacao roda como stack integrada fora do ambiente puramente Maven/H2.

## Escopo

- Criar Dockerfile do backend.
- Criar Dockerfile do frontend quando aplicavel.
- Ajustar Docker Compose para stack completa.
- Externalizar configuracoes sensiveis.
- Documentar comandos de subida, health check e parada da stack.

## Fora de escopo

- Provisionamento cloud.
- Publicacao em registry.
- Kubernetes.

## Criterios de aceite

- [ ] Stack local sobe com Docker Compose.
- [ ] Backend conecta no PostgreSQL.
- [ ] Flyway executa no container.
- [ ] Frontend acessa a API local.
- [ ] Health check retorna `UP`.
- [ ] README atualizado com execucao local via containers.

## Validacao

```bash
docker compose up -d --build
curl http://localhost:8080/actuator/health
mvn clean test
```

## Commit sugerido

```bash
git add .
git commit -m "infra: containeriza stack local do mini erp"
```

---

# V1-16 — Fazer deploy inicial em OVHcloud

## Tipo

`type: infra`  
`area: devops`  
`priority: high`

## Branch sugerida

`infra/deploy-ovhcloud-v1`

## Objetivo

Implantar a primeira versao demonstravel em OVHcloud usando a topologia single-host definida para a v1.

## Contexto

O deploy real fecha a narrativa de engenharia e DevOps do projeto: aplicacao Java, banco, containerizacao, automacao, health check, troubleshooting e demonstracao publica/controlada.

## Escopo

- Provisionar ou registrar infraestrutura minima definida para a v1.
- Preparar automacao de bootstrap/deploy com Ansible quando aplicavel.
- Configurar variaveis de ambiente e segredos fora do repositorio.
- Executar deploy da stack.
- Validar health check e fluxo minimo.
- Registrar runbook curto de operacao.

## Fora de escopo

- Alta disponibilidade.
- Kubernetes.
- Banco gerenciado.
- Escalabilidade horizontal.

## Criterios de aceite

- [ ] Ambiente remoto executa a stack.
- [ ] Aplicacao responde health check.
- [ ] Fluxo minimo de login e compras funciona no ambiente remoto.
- [ ] Segredos nao ficam versionados.
- [ ] Runbook minimo documentado.
- [ ] GitHub Projects atualizado com status real da entrega.

## Validacao

```bash
curl <url-do-ambiente>/actuator/health
```

Executar roteiro de smoke test documentado.

## Commit sugerido

```bash
git add .
git commit -m "infra: adiciona deploy inicial em ovhcloud"
```

---

# V1-17 — Fechar release v1.0.0

## Tipo

`type: docs`  
`area: docs`  
`area: devops`  
`priority: high`

## Branch sugerida

`release/v1.0.0`

## Objetivo

Fechar formalmente a primeira release funcional do Mini-ERP de Compras.

## Contexto

Após concluir o fluxo de compras, o projeto deve ser preparado para apresentação pública como case de portfólio.

## Escopo

- Revisar README.
- Adicionar visão de arquitetura.
- Adicionar fluxo funcional da aplicação.
- Adicionar instruções de execução local.
- Adicionar instruções de validação.
- Adicionar changelog.
- Criar tag `v1.0.0`.
- Registrar próximos passos para `v1.1.0 - RH/Folha Simplificado`.

## Fora de escopo

- Implementar módulo RH/Folha.
- Novas funcionalidades fora de Compras.
- Microserviços.

## Critérios de aceite

- [ ] Fluxo de compras completo implementado.
- [ ] Testes passam localmente.
- [ ] Pipeline CI passa no GitHub Actions.
- [ ] Deploy inicial validado.
- [ ] README atualizado e orientado a portfólio.
- [ ] `CHANGELOG.md` criado ou atualizado.
- [ ] Tag `v1.0.0` criada.
- [ ] Próxima release documentada.

## Validação

```bash
mvn clean test
git status
git tag v1.0.0
```

## Commit sugerido

```bash
git add .
git commit -m "docs: prepara release v1.0.0 do mini erp"
git tag v1.0.0
```

---

# Próxima release planejada

## v1.1.0 — RH/Folha Simplificado

Após o fechamento do fluxo de compras, iniciar a evolução do projeto com um módulo de RH/Folha simplificado para reforçar aderência a sistemas corporativos, domínio de gestão de pessoas, regras de negócio, auditoria, segurança e arquitetura modular.

Regra de governanca:
- nao iniciar RH/Folha antes de `v1.0.0` estar validada e implantada
- criar novas issues reais no GitHub Projects somente depois do fechamento da release de Compras
- nao reutilizar numeros de issues ja existentes no repositorio; os IDs abaixo sao apenas sugestoes de escopo

Escopo inicial sugerido:

- RH-01 Modelar módulo RH/Folha e registrar ADR.
- RH-02 Implementar cadastro de colaborador.
- RH-03 Implementar cargos, departamentos e contratos.
- RH-04 Implementar rubricas de folha.
- RH-05 Implementar eventos de folha.
- RH-06 Implementar cálculo simplificado.
- RH-07 Implementar fechamento de folha.
- RH-08 Implementar auditoria da folha.
- RH-09 Adicionar testes de regras de folha.
- RH-10 Atualizar README e portfólio com case RH/Folha.
