package br.com.algaworks.algaworkscomecandocomspringmvc.repository;

import br.com.algaworks.algaworkscomecandocomspringmvc.model.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Titulos extends JpaRepository<Titulo, Long> {

}
