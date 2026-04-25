# Demo Scripts

Esta pasta contem scripts Python para demonstrar a fundacao tecnica do backend em uma apresentacao, post ou gravacao curta.

Objetivo:
- gerar telas limpas de terminal
- evidenciar health check, autenticacao e tratamento de erro
- demonstrar a base funcional sem misturar isso com a futura suite de testes automatizados

## Dependencias

Requer Python 3.10+.

Crie um ambiente virtual e instale os pacotes:

```bash
python3 -m venv .venv
source .venv/bin/activate
pip install -r demo/requirements.txt
```

## Variaveis de ambiente

Minimas para todos os scripts:

```bash
export MINIERP_BASE_URL="http://localhost:8080"
```

Necessarias para o fluxo de login com sucesso:

```bash
export MINIERP_EMAIL="admin@minierp.local"
export MINIERP_PASSWORD="<sua-senha-local>"
```

## Como rodar

Execute a aplicacao Spring Boot primeiro. Depois rode, a partir da raiz do repositorio:

```bash
python3 demo/01_health_check.py
python3 demo/02_auth_flow.py
python3 demo/03_error_flow.py
```

## O que cada script mostra

`01_health_check.py`
- prova que a aplicacao esta viva
- bom para a primeira screenshot

`02_auth_flow.py`
- faz login
- mascara o token
- consulta o usuario autenticado
- bom para mostrar identidade e acesso

`03_error_flow.py`
- acessa endpoint protegido sem token
- tenta acessar com token invalido
- tenta login com credenciais invalidas
- bom para mostrar contrato de erro e comportamento defensivo

## Dica para LinkedIn

Uma sequencia forte de imagens costuma ser:

1. health check
2. login bem-sucedido
3. `/api/auth/me` com roles
4. um erro `401` bem apresentado
