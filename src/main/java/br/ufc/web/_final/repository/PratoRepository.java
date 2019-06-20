package br.ufc.web._final.repository;

import br.ufc.web._final.model.Prato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PratoRepository extends JpaRepository<Prato, Long> {
}
