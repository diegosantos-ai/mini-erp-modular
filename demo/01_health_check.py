#!/usr/bin/env python3
from __future__ import annotations

from common import create_session, load_settings, print_banner, print_environment, print_next_hint, print_response, request_json


def main() -> None:
    settings = load_settings()
    session = create_session()

    print_banner(
        "Demo 01 - Health Check",
        "Verifica se a aplicacao esta de pe e pronta para uso operacional basico.",
    )
    print_environment(settings, include_credentials=False)

    response, payload = request_json(
        session,
        "GET",
        f"{settings.base_url}/actuator/health",
        timeout_seconds=settings.timeout_seconds,
    )

    print_response("GET /actuator/health", response, payload)

    if response.ok:
        print_next_hint("Essa tela funciona bem como primeira imagem do post: mostra saude e prontidao da aplicacao.")


if __name__ == "__main__":
    main()
