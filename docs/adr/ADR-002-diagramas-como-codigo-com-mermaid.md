# ADR-002: Adotar diagramas como codigo com Mermaid na documentacao

## Status

Accepted

## Contexto

O projeto precisa de diagramas para explicar processo de negocio, estados do dominio, modelo conceitual, arquitetura e deploy. Como o foco do trabalho inclui engenharia de software e DevOps, os diagramas tambem devem seguir principios de versionamento, revisao e manutencao no proprio repositorio.

O uso de imagens binarias soltas tende a dificultar diff, code review e atualizacao sincronizada com o texto.

## Decisao

Adotar Mermaid em arquivos Markdown como formato padrao para os diagramas principais do projeto.

Os diagramas devem:
- ficar proximos do texto que explicam
- ser versionados junto da documentacao
- participar do mesmo fluxo de revisao das mudancas
- evitar duplicidade desnecessaria com imagens exportadas

## Alternativas consideradas

### Imagens exportadas manualmente

Vantagens:
- liberdade visual
- facilidade de reutilizacao em slides

Desvantagens:
- diff ruim
- risco maior de divergencia entre imagem e texto
- manutencao mais custosa

### PlantUML

Vantagens:
- expressividade forte em UML
- boa cobertura de tipos de diagrama

Desvantagens:
- maior friccao para leitura casual no GitHub
- necessidade menor para o escopo atual, dado que Mermaid cobre o essencial

## Consequencias

Consequencias positivas:
- diagramas ficam reviewables no GitHub
- texto e diagrama evoluem na mesma mudanca
- menor custo de manutencao para um projeto individual

Consequencias negativas:
- limitacoes visuais em comparacao com ferramentas graficas dedicadas
- alguns diagramas complexos podem exigir simplificacao

## Revisao futura

Reavaliar se Mermaid continua suficiente quando o projeto crescer em complexidade ou quando houver necessidade de material mais visual para apresentacoes externas.
