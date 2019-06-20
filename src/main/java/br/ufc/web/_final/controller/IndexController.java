package br.ufc.web._final.controller;

import br.ufc.web._final.model.Cliente;
import br.ufc.web._final.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    private ClienteService clienteService;

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("redirect:/prato/listar_cliente");
        return mv;
    }

    @RequestMapping("/logar")
    public ModelAndView logar() {
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }

    @RequestMapping("/user")
    public ModelAndView user() {

        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails user = (UserDetails) auth;

        Cliente cliente = clienteService.serchByEmail(user.getUsername());

        ModelAndView mv;
        if(cliente.getRoles().get(0).getPapel().equals("ROLE_ADMIN"))
            mv = new ModelAndView("redirect:/prato/listar_gerente");
        else
            mv = new ModelAndView("redirect:/prato/listar_cliente");

        return mv;
    }

}
