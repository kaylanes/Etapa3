package br.edu.ifrn.trabalhopi.controller;

/*
 * Objetivos: Essa classe tem como objetivo principal o acesso ao cadastro dos administradores. 
 * A partir do acesso, eles conseguem remover o cadastro do sistema.
 * 
 * Autores: Kaylane Souza e Raynara Pedro (kaylane.souza@escolar.ifrn.edu.br, freire.pedro@escolar.ifrn.edu.br)
 * 
 * Data de criação: 27/09/2021
 * ##################################
 * Ultimas alteração: 06/01/2022
 * 
 * Analista: Nickerson
 * Data:
 * Alteração: 
 * 
 * ##################################
 * 
 */
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.trabalhopi.dominio.Usuario;
import br.edu.ifrn.trabalhopi.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class BuscarUsuariosController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/*
	 * Objetivo: Executar a página html "buscar"
	 * */
	@GetMapping("/busca")
	public String entrarBusca() {
		return "usuario/buscar";
	}
	
	/*
	 * Objetivo: Busca os cadastros dos adminstradores por meio do nome informado ou aparece todos os cadastros sem informa o nome
	 * */
	@GetMapping("/buscar")
	public String buscar(@RequestParam(name="nome", required=true) String nome, @RequestParam(name="mostrarTodosDados", required=false) Boolean mostrarTodosDados, HttpSession sessao, ModelMap model) {
	
		List<Usuario> usuariosEncontrados = usuarioRepository.findByNome(nome);
	
		model.addAttribute("usuariosEncontrados", usuariosEncontrados);
	
		if (mostrarTodosDados != null) {
			model.addAttribute("mostrarTodosDados", true);
		}
	
		return "usuario/buscar";
	}
	
	/*
	 * Objetivo: Responsável por remover o cadastro do administrador
	 * */
	@GetMapping("/remover/{id}")
	public String remover(@PathVariable("id") Integer idUsuario, HttpSession sessao, RedirectAttributes attr) {
	
		usuarioRepository.deleteById(idUsuario);
		attr.addFlashAttribute("msgSucesso", "Usuário removido com sucesso!");
	
		return "redirect:/usuarios/buscar";
	}

}