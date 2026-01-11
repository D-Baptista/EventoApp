package com.eventoapp.eventoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.eventoapp.eventoapp.models.Evento;
import com.eventoapp.eventoapp.repository.EventoRepository;

@Controller
public class IndexController {

    @Autowired
    private EventoRepository er;

    @RequestMapping("/")
    public ModelAndView listarEventos() {
        ModelAndView mv = new ModelAndView("index");
        Iterable<Evento> ev = er.findAll();
        mv.addObject("eventos", ev);
        return mv;
    }
}
