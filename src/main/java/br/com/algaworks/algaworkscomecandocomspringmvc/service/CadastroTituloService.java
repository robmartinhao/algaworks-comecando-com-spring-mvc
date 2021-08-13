package br.com.algaworks.algaworkscomecandocomspringmvc.service;

import br.com.algaworks.algaworkscomecandocomspringmvc.model.StatusTitulo;
import br.com.algaworks.algaworkscomecandocomspringmvc.model.Titulo;
import br.com.algaworks.algaworkscomecandocomspringmvc.repository.Titulos;
import br.com.algaworks.algaworkscomecandocomspringmvc.repository.filter.TituloFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroTituloService {

    @Autowired
    private Titulos titulos;

    public void salvar(Titulo titulo) {
        try {
            titulos.save(titulo);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Formato de data inválido");
        }
    }

    public void excluir(Long codigo) {
        titulos.deleteById(codigo);
    }

    public String receber(Long codigo) {
        Titulo titulo = titulos.findById(codigo).get();
        titulo.setStatus(StatusTitulo.RECEBIDO);
        titulos.save(titulo);

        return StatusTitulo.RECEBIDO.getDescricao();
    }

    public List<Titulo> filtrar(TituloFilter filtro) {
        String descricao = filtro.getDescricao() == null ? "" : filtro.getDescricao();
        return titulos.findByDescricaoContaining(descricao);
    }
}
