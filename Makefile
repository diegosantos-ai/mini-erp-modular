SHELL := /bin/bash

MVN ?= mvn
MAVEN_FLAGS ?= -B -ntp

.PHONY: help clean test package run ci

help:
	@echo "Targets disponiveis:"
	@echo "  make clean   - limpa artefatos locais do Maven"
	@echo "  make test    - executa a suite automatizada"
	@echo "  make package - gera o artefato da aplicacao"
	@echo "  make run     - sobe a aplicacao Spring Boot localmente"
	@echo "  make ci      - executa o fluxo minimo usado pela pipeline"

clean:
	$(MVN) $(MAVEN_FLAGS) clean

test:
	$(MVN) $(MAVEN_FLAGS) test

package:
	$(MVN) $(MAVEN_FLAGS) clean package

run:
	$(MVN) spring-boot:run

ci:
	$(MVN) $(MAVEN_FLAGS) clean test
