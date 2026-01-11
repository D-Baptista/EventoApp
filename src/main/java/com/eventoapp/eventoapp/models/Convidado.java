package com.eventoapp.eventoapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;


@Entity
public class Convidado {
    @Id
    @NotEmpty
    private String bi;
    
    @NotEmpty
    private String nomeConvidado;

    @ManyToOne
    private Evento evento;

    public String getBi() {
        return this.bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public String getNomeConvidado() {
        return this.nomeConvidado;
    }

    public void setNomeConvidado(String nomeConvidado) {
        this.nomeConvidado = nomeConvidado;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Evento getEvento() {
        return this.evento;
    }
}
