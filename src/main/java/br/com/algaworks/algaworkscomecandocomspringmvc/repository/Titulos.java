package br.com.algaworks.algaworkscomecandocomspringmvc.repository;

import br.com.algaworks.algaworkscomecandocomspringmvc.model.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Titulos extends JpaRepository<Titulo, Long> {

    public List<Titulo> findByDescricaoContaining(String descricao);
}
