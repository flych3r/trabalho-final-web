package br.ufc.web._final.controller;

import br.ufc.web._final.model.Prato;
import br.ufc.web._final.service.PratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/prato")
public class PratoController {

    @Autowired
    private PratoService pratoService;

    @RequestMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView mv = new ModelAndView("gerente/cadastro_prato");
        mv.addObject("prato", new Prato());
        return mv;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@Validated Prato prato, BindingResult result, @RequestParam(value = "imagem") MultipartFile imagem) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("gerente/cadastrar_prato");
            return mv;
        }
        pratoService.save(prato, imagem);
        ModelAndView mv = new ModelAndView("redirect:/prato/listar_gerente");
        return mv;
    }

    @GetMapping("/listar_gerente")
    public ModelAndView listarGerente() {
        List<Prato> listaPratos = pratoService.listPratos();
        ModelAndView mv = new ModelAndView("gerente/listar_pratos");
        mv.addObject("listaPratos", listaPratos);

        return mv;
    }

    @GetMapping("/listar_cliente")
    public ModelAndView listarCliente() {
        List<Prato> listaPratos = pratoService.listPratos();
        ModelAndView mv = new ModelAndView("cliente/listar_pratos");
        mv.addObject("listaPratos", listaPratos);

        return mv;
    }

    @RequestMapping("/excluir/{id}")
    public ModelAndView excluir(@PathVariable Long id) {
        pratoService.delete(id);
        ModelAndView mv = new ModelAndView("redirect:/prato/listar_gerente");
        return mv;
    }

    @RequestMapping("/atualizar/{id}")
    public ModelAndView atualizar(@PathVariable Long id) {
        Prato prato = pratoService.serchById(id);
        ModelAndView mv = new ModelAndView("gerente/cadastro_prato");
        mv.addObject("prato", prato);
        return mv;
    }

}