package com.example.springboot.models.enums;

public enum PessoaCargo {
    ADMIN("admin"),
    USER ("user");

    private final String cargo;

    private PessoaCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

}
