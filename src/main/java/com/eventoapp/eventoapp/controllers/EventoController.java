package com.eventoapp.eventoapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventoapp.eventoapp.models.Convidado;
import com.eventoapp.eventoapp.models.Evento;
import com.eventoapp.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.eventoapp.repository.EventoRepository;

import jakarta.validation.Valid;

@Controller
public class EventoController {

    @Autowired
    private EventoRepository er;

    @Autowired
    private ConvidadoRepository cr;

    // Rota para abrir formulário VAZIO
    @GetMapping("/cadastrarEvento")
    public ModelAndView form() {
        ModelAndView mv = new ModelAndView("evento/formEvento");
        mv.addObject("evento", new Evento());
        return mv;
    }

    // Rota para abrir formulário PREENCHIDO (Edição)
    @GetMapping("/editarEvento/{id}")
    public ModelAndView editar(@PathVariable("id") long id, RedirectAttributes atributes) {

        Optional<Evento> evento = er.findById(id);

        ModelAndView mv;

        if (evento.isEmpty()) {
            mv = new ModelAndView("redirect:/eventos");
            atributes.addFlashAttribute("message", "Não foi encontrado nenhum evento com esse ID");
        } else {
            mv = new ModelAndView("evento/formEvento");
            mv.addObject("evento", evento.get());
        }
        return mv;
    }

    // Rota ÚNICA para salvar (POST)
    @PostMapping("/salvar")
    public String salvar(@Valid Evento evento, BindingResult result, Model model) {

        if (result.hasErrors()) {
            // Retornamos o nome do arquivo HTML diretamente (sem 'redirect:')
            // Isso mantém o objeto 'evento' preenchido e exibe as mensagens de erro
            model.addAttribute("message", "Preencha todos os campos obrigatórios!");
            return "evento/formEvento"; // Nome do arquivo em templates/evento/formEvento.html
        }

        try {
            er.save(evento);
            model.addAttribute("message", "Evento salvo com sucesso!");
        } catch (Exception e) {
            model.addAttribute("message", "Erro ao acessar o banco de dados.");
            return "evento/formEvento";
        }

        // Se quiser que ele fique na página após o sucesso com os campos limpos:
        model.addAttribute("evento", new Evento());
        return "evento/formEvento";
    }

    @RequestMapping("/eventos")
    public ModelAndView listarEventos() {
        ModelAndView mv = new ModelAndView("index");
        Iterable<Evento> ev = er.findAll();
        mv.addObject("eventos", ev);
        return mv;
    }

    @GetMapping("/detalheEvento/{id}")
    public ModelAndView detalheEvento(@PathVariable("id") long id) {
        Optional<Evento> evento = er.findById(id);
        ModelAndView mv = new ModelAndView("/evento/detalhesEvento");
        mv.addObject("evento", evento.get());

        Iterable<Convidado> convidados = cr.findByEvento(evento.get());

        mv.addObject("convidados", convidados);
        return mv;
    }

    @RequestMapping(value = "/detalheEvento/{id}", method = RequestMethod.POST)
    public String detalheEventoPost(@PathVariable("id") long id, @Valid Convidado convidado, BindingResult result,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("message", "Verifique os campos obrigatórios!");
            return "redirect:/detalheEvento/{id}";
        }

        Optional<Evento> evento = er.findById(id);

        convidado.setEvento(evento.get());
        cr.save(convidado);

        attributes.addFlashAttribute("message", "Convidado adicionado com sucesso!");

        return "redirect:/detalheEvento/{id}";
    }

    @RequestMapping("/apagarEvento/{id}")
    public String apagarEvento(@PathVariable("id") long id) {

        Optional<Evento> evento = er.findById(id);
        if (!evento.isEmpty()) {

            Iterable<Convidado> convidados = cr.findByEvento(evento.get());

            for (Convidado c : convidados) {
                cr.delete(c);
            }

            er.delete(evento.get());
        }
        return "redirect:/eventos";
    }

    @RequestMapping("/apagarConvidado/{bi}")
    public String apagarConvidado(@PathVariable("bi") String bi) {

        List<Convidado> convidado = cr.findByBi(bi);

        if (!convidado.isEmpty()) {
            cr.delete(convidado.get(0));
        }

        Optional<Evento> evento = er.findById(convidado.get(0).getEvento().getId());

        if (!evento.isEmpty()) {

            return "redirect:/detalheEvento/" + evento.get().getId();
        } else {
            return "redirect:/eventos";
        }
    }

}
