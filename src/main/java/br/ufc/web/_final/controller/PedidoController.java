package br.ufc.web._final.controller;

import br.ufc.web._final.model.Cliente;
import br.ufc.web._final.model.Item;
import br.ufc.web._final.model.Pedido;
import br.ufc.web._final.service.ClienteService;
import br.ufc.web._final.service.ItemService;
import br.ufc.web._final.service.PedidoService;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ClienteService clienteService;

    @RequestMapping("/salvar")
    public ModelAndView salvar(HttpSession session) {

        Iterable<Item> cart = (Iterable<Item>) session.getAttribute("carrinho");
        if(IterableUtils.size(cart) < 1)
            return new ModelAndView("redirect:/prato/listar_cliente");

        Pedido pedido = new Pedido();

        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails user = (UserDetails) auth;

        Cliente cliente = clienteService.serchByEmail(user.getUsername());

        pedido.setCliente(cliente);
        pedido.setTotal(0.0);
        pedidoService.save(pedido);

        Double total = 0.0;
        for (Item item : cart) {

            item.setPedido(pedido);
            total += item.getPreco() * item.getQuantidade();
        }

        itemService.saveAll(cart);

        pedido.setTotal(total);
        pedido.setPendente(1);
        pedidoService.save(pedido);

        session.invalidate();

        ModelAndView mv = new ModelAndView("redirect:/pedido/listar");
        return mv;
    }

    @RequestMapping("/listar")
    public ModelAndView listar() {

        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails user = (UserDetails) auth;

        Cliente cliente = clienteService.serchByEmail(user.getUsername());

        List<Pedido> listaPedidos = pedidoService.findCliente(cliente);

        ModelAndView mv = new ModelAndView("pedido/listar_pedidos");
        mv.addObject("listaPedidos", listaPedidos);
        return mv;
    }

    @RequestMapping("/listar_pendente")
    public ModelAndView listarPendente() {

        List<Pedido> listaPedidos = pedidoService.findAll();

        ModelAndView mv = new ModelAndView("pedido/listar_pendente");
        mv.addObject("listaPedidos", listaPedidos);
        return mv;
    }

    @RequestMapping("/enviar/{id}")
    public ModelAndView enviar(@PathVariable Long id) {

        Pedido pedido = pedidoService.serchById(id);

        pedido.setPendente(0);
        pedidoService.save(pedido);

//        ModelAndView mv = new ModelAndView("redirect:pedido/listar_pendente");
        return listarPendente();
    }

}
