package br.ufc.web._final.service;

import br.ufc.web._final.model.Cliente;
import br.ufc.web._final.model.Pedido;
import br.ufc.web._final.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public void save(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    public List<Pedido> listPedidos() {
        return pedidoRepository.findAll();
    }

    public void delete(Long id) {
        pedidoRepository.deleteById(id);
    }

    public Pedido serchById(Long id) {
        return pedidoRepository.getOne(id);
    }

    public List<Pedido> findCliente(Cliente cliente) {
        return pedidoRepository.findByCliente(cliente);
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }
}
