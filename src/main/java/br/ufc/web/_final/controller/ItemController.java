package br.ufc.web._final.controller;

import br.ufc.web._final.model.Item;
import br.ufc.web._final.model.Pedido;
import br.ufc.web._final.service.ItemService;
import br.ufc.web._final.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private PedidoService pedidoService;

    @RequestMapping("/listar/{id}")
    public ModelAndView listar(@PathVariable Long id) {

        Pedido pedido = pedidoService.serchById(id);

        List<Item> listaItens = itemService.findItem(pedido);

        ModelAndView mv = new ModelAndView("pedido/listar_itens");
        mv.addObject("listaItens", listaItens);
        return mv;

    }

}
