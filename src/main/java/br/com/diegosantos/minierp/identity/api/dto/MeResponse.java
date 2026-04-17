package br.com.diegosantos.minierp.identity.api.dto;

import java.util.Set;

public class MeResponse {

    private Long id;
    private String nome;
    private String email;
    private Set<String> roles;

    public MeResponse(Long id, String nome, String email, Set<String> roles) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getRoles() {
        return roles;
    }
}