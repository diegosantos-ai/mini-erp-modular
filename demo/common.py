#!/usr/bin/env python3
from __future__ import annotations

import json
import os
import sys
from dataclasses import dataclass
from typing import Any

try:
    import requests
    from rich.console import Console
    from rich.panel import Panel
    from rich.pretty import Pretty
    from rich.table import Table
except ImportError as exc:
    missing_dependency = getattr(exc, "name", "dependency")
    raise SystemExit(
        "Dependencia ausente: "
        f"{missing_dependency}. Instale com: pip install -r demo/requirements.txt"
    ) from exc


console = Console()


@dataclass(frozen=True)
class Settings:
    base_url: str
    email: str | None
    password: str | None
    timeout_seconds: float = 10.0


def load_settings(require_credentials: bool = False) -> Settings:
    base_url = os.getenv("MINIERP_BASE_URL", "http://localhost:8080").rstrip("/")
    email = os.getenv("MINIERP_EMAIL")
    password = os.getenv("MINIERP_PASSWORD")

    if require_credentials and (not email or not password):
        raise SystemExit(
            "Configure MINIERP_EMAIL e MINIERP_PASSWORD antes de rodar este script."
        )

    return Settings(
        base_url=base_url,
        email=email,
        password=password,
    )


def create_session() -> requests.Session:
    session = requests.Session()
    session.headers.update({"Accept": "application/json"})
    return session


def print_banner(title: str, subtitle: str) -> None:
    console.print(Panel.fit(subtitle, title=title, border_style="cyan"))


def print_environment(settings: Settings, include_credentials: bool) -> None:
    table = Table(title="Configuracao da demo", show_header=True, header_style="bold")
    table.add_column("Item", style="cyan")
    table.add_column("Valor", style="white")
    table.add_row("Base URL", settings.base_url)
    table.add_row("Timeout", f"{settings.timeout_seconds:.0f}s")

    if include_credentials:
        table.add_row("Email", settings.email or "<nao configurado>")
        table.add_row("Senha", "<oculta>" if settings.password else "<nao configurada>")

    console.print(table)


def request_json(
    session: requests.Session,
    method: str,
    url: str,
    *,
    timeout_seconds: float,
    **kwargs: Any,
) -> tuple[requests.Response, Any]:
    try:
        response = session.request(method=method, url=url, timeout=timeout_seconds, **kwargs)
    except requests.RequestException as exc:
        raise SystemExit(
            "Falha ao chamar a API. Verifique se a aplicacao Spring Boot esta em execucao "
            f"e acessivel em {url}. Erro original: {exc}"
        ) from exc

    try:
        payload = response.json()
    except ValueError:
        payload = response.text

    return response, payload


def print_response(step: str, response: requests.Response, payload: Any) -> None:
    summary = Table(show_header=False, box=None)
    summary.add_column("Campo", style="cyan")
    summary.add_column("Valor", style="white")
    summary.add_row("Etapa", step)
    summary.add_row("Status HTTP", str(response.status_code))
    summary.add_row("URL", response.url)

    console.print(summary)

    if payload == "":
        console.print(Panel.fit("<corpo vazio>", title="Resposta"))
        return

    console.print(Panel.fit(Pretty(scrub_payload(payload)), title="Resposta"))


def mask_token(token: str) -> str:
    if len(token) <= 18:
        return "<token mascarado>"
    return f"{token[:12]}...{token[-6:]}"


def scrub_payload(payload: Any) -> Any:
    if isinstance(payload, dict):
        cleaned: dict[str, Any] = {}
        for key, value in payload.items():
            normalized_key = key.lower()

            if normalized_key == "token" and isinstance(value, str):
                cleaned[key] = mask_token(value)
                continue

            if "password" in normalized_key:
                cleaned[key] = "<oculto>"
                continue

            cleaned[key] = scrub_payload(value)

        return cleaned

    if isinstance(payload, list):
        return [scrub_payload(item) for item in payload]

    return payload


def print_next_hint(message: str) -> None:
    console.print(Panel.fit(message, title="Dica", border_style="green"))


def exit_with_usage(message: str) -> None:
    console.print(Panel.fit(message, title="Uso", border_style="yellow"))
    sys.exit(1)


def dump_payload_as_json(payload: Any) -> str:
    return json.dumps(scrub_payload(payload), indent=2, ensure_ascii=True)
