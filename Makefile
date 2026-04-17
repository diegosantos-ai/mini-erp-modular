SHELL := /bin/bash

MVN ?= mvn
MAVEN_FLAGS ?= -B -ntp
PRE_COMMIT ?= pre-commit

.PHONY: help clean lint lint-java lint-secrets test package run ci pre-commit-install pre-commit-run

help:
	@echo "Targets disponiveis:"
	@echo "  make clean   - limpa artefatos locais do Maven"
	@echo "  make lint    - executa validacoes rapidas de codigo e segredos"
	@echo "  make lint-java - compila o codigo sem rodar os testes"
	@echo "  make lint-secrets - procura credenciais e segredos hardcoded"
	@echo "  make test    - executa a suite automatizada"
	@echo "  make package - gera o artefato da aplicacao"
	@echo "  make run     - sobe a aplicacao Spring Boot localmente"
	@echo "  make ci      - executa o fluxo minimo usado pela pipeline"
	@echo "  make pre-commit-install - instala hooks locais de pre-commit e pre-push"
	@echo "  make pre-commit-run - executa os hooks em todos os arquivos"

clean:
	$(MVN) $(MAVEN_FLAGS) clean

lint: lint-secrets lint-java

lint-java:
	$(MVN) $(MAVEN_FLAGS) -DskipTests compile

lint-secrets:
	bash scripts/check-no-hardcoded-secrets.sh

test:
	$(MVN) $(MAVEN_FLAGS) test

package:
	$(MVN) $(MAVEN_FLAGS) clean package

run:
	$(MVN) spring-boot:run

ci:
	$(MAKE) lint
	$(MVN) $(MAVEN_FLAGS) clean test

pre-commit-install:
	$(PRE_COMMIT) install --hook-type pre-commit --hook-type pre-push

pre-commit-run:
	$(PRE_COMMIT) run --all-files
