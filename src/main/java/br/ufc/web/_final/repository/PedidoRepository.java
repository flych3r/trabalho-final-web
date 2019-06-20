package br.ufc.web._final.repository;

import br.ufc.web._final.model.Cliente;
import br.ufc.web._final.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByCliente(Cliente cliente);

}
