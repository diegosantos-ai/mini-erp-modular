#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(git rev-parse --show-toplevel)"
cd "$ROOT_DIR"

declare -a findings=()

collect_findings() {
  local description="$1"
  local pattern="$2"
  local output=""

  output="$(git grep -nI -E "$pattern" -- . || true)"

  if [[ -z "$output" ]]; then
    return
  fi

  while IFS= read -r line; do
    [[ -z "$line" ]] && continue

    if [[ "$line" == *"secret-scan: allow"* ]]; then
      continue
    fi

    findings+=("${description}|${line}")
  done <<< "$output"
}

collect_findings "Senha de exemplo de alto risco encontrada" 'Admin@123'
collect_findings "JWT secret hardcoded em propriedade sensivel" 'security\.jwt\.secret=[A-Za-z0-9+/=]{32,}'
collect_findings "JWT_SECRET com valor literal em vez de placeholder ou comando" '\bJWT_SECRET\b[^=\n]*=\s*["'\''][^$<][^"'\'']{8,}["'\'']'
collect_findings "Senha de bootstrap hardcoded em variavel de ambiente" '\bIDENTITY_BOOTSTRAP_ADMIN_PASSWORD\b[^=\n]*=\s*["'\''][^$<][^"'\'']{8,}["'\'']'
collect_findings "Constante *_PASSWORD hardcoded detectada" '\b[A-Z0-9_]*PASSWORD\b\s*=\s*["'\''][^<][^"'\'']{8,}["'\'']'

if [[ ${#findings[@]} -eq 0 ]]; then
  echo "Secret scan OK: nenhum hardcoded secret critico foi identificado."
  exit 0
fi

echo "Falha no lint de segredos. Revise os itens abaixo:"
echo

for finding in "${findings[@]}"; do
  description="${finding%%|*}"
  location="${finding#*|}"
  echo "- ${description}"
  echo "  ${location}"
done

echo
echo "Dica:"
echo "- use variaveis de ambiente, placeholders ou valores gerados dinamicamente"
echo "- se o valor for benigno e exclusivamente didatico, documente o motivo e use o comentario 'secret-scan: allow'"

exit 1
