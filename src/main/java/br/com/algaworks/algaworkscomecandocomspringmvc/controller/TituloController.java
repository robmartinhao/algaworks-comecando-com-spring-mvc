package br.com.algaworks.algaworkscomecandocomspringmvc.controller;

import br.com.algaworks.algaworkscomecandocomspringmvc.model.StatusTitulo;
import br.com.algaworks.algaworkscomecandocomspringmvc.model.Titulo;
import br.com.algaworks.algaworkscomecandocomspringmvc.repository.Titulos;
import br.com.algaworks.algaworkscomecandocomspringmvc.service.CadastroTituloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/titulos")
public class TituloController {

    private static final String CADASTRO_VIEW = "CadastroTitulo";

    @Autowired
    private Titulos titulos;

    @Autowired
    private CadastroTituloService cadastroTituloService;

    @RequestMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
        mv.addObject(new Titulo());
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {

        ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
        if (errors.hasErrors()) {
            return CADASTRO_VIEW;
        }
        try{
            cadastroTituloService.salvar(titulo);
            attributes.addFlashAttribute("mensagem", "Título salvo com sucesso!");
        } catch (DataIntegrityViolationException e) {
            errors.rejectValue("dataVencimento", null, "Formato de data inválido");
            return CADASTRO_VIEW;
        }

        return "redirect:/titulos/novo";
    }

    @RequestMapping
    public ModelAndView pesquisar() {
        List<Titulo> todosTitulos = titulos.findAll();
        ModelAndView mv = new ModelAndView("PesquisaTitulos");
        mv.addObject("titulos", todosTitulos);
        return mv;
    }

    @RequestMapping("{codigo}")
    public ModelAndView edicao(@PathVariable Long codigo) {
        Titulo titulo = titulos.findById(codigo).orElse(null);
        ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
        mv.addObject(titulo);
        return mv;
    }

    @RequestMapping(value = "{codigo}", method = RequestMethod.DELETE)
    public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
        cadastroTituloService.excluir(codigo);
        attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
        return "redirect:/titulos";
    }

    @RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
    public @ResponseBody String receber(@PathVariable Long codigo) {
        return cadastroTituloService.receber(codigo);

    }

    @ModelAttribute("todosStatus")
    public List<StatusTitulo> todosStatusTitulo() {
        return Arrays.asList(StatusTitulo.values());
    }
}
