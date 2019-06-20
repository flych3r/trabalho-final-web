package br.ufc.web._final.repository;

import br.ufc.web._final.model.Item;
import br.ufc.web._final.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByPedido(Pedido pedido);

}
