package com.eventoapp.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.eventoapp.eventoapp.models.Evento;
import java.util.List;

public interface EventoRepository extends CrudRepository<Evento, Long> {
    List<Evento> findByNome(String nome);
}
