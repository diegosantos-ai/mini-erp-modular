#!/usr/bin/env python3
from __future__ import annotations

from common import create_session, load_settings, print_banner, print_environment, print_next_hint, print_response, request_json


def main() -> None:
    settings = load_settings(require_credentials=True)
    session = create_session()

    print_banner(
        "Demo 02 - Fluxo de Autenticacao",
        "Executa login com JWT e, na sequencia, consulta o usuario autenticado.",
    )
    print_environment(settings, include_credentials=True)

    login_response, login_payload = request_json(
        session,
        "POST",
        f"{settings.base_url}/api/auth/login",
        timeout_seconds=settings.timeout_seconds,
        json={
            "email": settings.email,
            "password": settings.password,
        },
    )

    print_response("POST /api/auth/login", login_response, login_payload)

    if not login_response.ok:
        raise SystemExit("O login falhou. Revise MINIERP_EMAIL e MINIERP_PASSWORD.")

    token = login_payload.get("token") if isinstance(login_payload, dict) else None
    if not token:
        raise SystemExit("A API respondeu sem token. Verifique o contrato do endpoint de login.")

    session.headers["Authorization"] = f"Bearer {token}"

    me_response, me_payload = request_json(
        session,
        "GET",
        f"{settings.base_url}/api/auth/me",
        timeout_seconds=settings.timeout_seconds,
    )

    print_response("GET /api/auth/me", me_response, me_payload)

    if me_response.ok:
        print_next_hint(
            "Essa tela mostra bem a fundacao de identidade: login, emissao de JWT e leitura do usuario autenticado."
        )


if __name__ == "__main__":
    main()
