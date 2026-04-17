#!/usr/bin/env python3
from __future__ import annotations

from common import create_session, load_settings, print_banner, print_environment, print_next_hint, print_response, request_json


def main() -> None:
    settings = load_settings()
    session = create_session()

    print_banner(
        "Demo 03 - Fluxos de Erro",
        "Mostra como a API se comporta quando autenticacao falha ou esta ausente.",
    )
    print_environment(settings, include_credentials=False)

    no_token_response, no_token_payload = request_json(
        session,
        "GET",
        f"{settings.base_url}/api/auth/me",
        timeout_seconds=settings.timeout_seconds,
    )
    print_response("GET /api/auth/me sem token", no_token_response, no_token_payload)

    invalid_token_session = create_session()
    invalid_token_session.headers["Authorization"] = "Bearer token-invalido-para-demo"
    invalid_token_response, invalid_token_payload = request_json(
        invalid_token_session,
        "GET",
        f"{settings.base_url}/api/auth/me",
        timeout_seconds=settings.timeout_seconds,
    )
    print_response(
        "GET /api/auth/me com token invalido",
        invalid_token_response,
        invalid_token_payload,
    )

    invalid_login_email = settings.email or "naoexiste@minierp.local"
    invalid_login_response, invalid_login_payload = request_json(
        session,
        "POST",
        f"{settings.base_url}/api/auth/login",
        timeout_seconds=settings.timeout_seconds,
        json={
            "email": invalid_login_email,
            "password": "senha-errada-para-demo",
        },
    )
    print_response(
        "POST /api/auth/login com credenciais invalidas",
        invalid_login_response,
        invalid_login_payload,
    )

    print_next_hint(
        "Use esta tela para reforcar que a base nao tem apenas happy path: ela tambem trata falhas e responde de forma consistente."
    )


if __name__ == "__main__":
    main()
