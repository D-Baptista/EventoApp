package com.eventoapp.eventoapp.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Evento implements Serializable {
    
    @NotEmpty
    private String nome;
    @NotEmpty
    private String local, data, horario;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany
    private List<Convidado> convidados;

    public void setConvidados(List<Convidado> convidados) {
        this.convidados = convidados;
    }

    public List<Convidado> getConvidados() {
        return this.convidados;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getLocal() {
        return this.local;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return this.data;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getHorario() {
        return this.horario;
    }
}
