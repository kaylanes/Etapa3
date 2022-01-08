package br.edu.ifrn.trabalhopi.controller;

/*
 * Objetivos: Essa classe tem como objetivo principal o acesso os pedidos dos estudantes. 
 * A partir do acesso a esses pedidos, eles conseguem remover a solicitação.
 * 
 * Autores: Kaylane Souza e Raynara Pedro (kaylane.souza@escolar.ifrn.edu.br, freire.pedro@escolar.ifrn.edu.br)
 * 
 * Data de criação: 27/09/2021
 * ##################################
 * Ultima alteração: 06/01/2022
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

import br.edu.ifrn.trabalhopi.dominio.Pedido;
import br.edu.ifrn.trabalhopi.repository.PedidoRepository;
import br.edu.ifrn.trabalhopi.repository.UsuarioRepository;

@Controller
@RequestMapping("/pedidos")
public class BuscarPedidosController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/*
	 * Objetivo: Executar a página html "buscarpedidos"
	 * */
	@GetMapping("/buscap")
	public String entrarBusca() {
		return "usuario/buscarpedidos";
	}
	
	/*
	 * Objetivo: Busca os pedidos dos estudante por meio do nome informado ou aparece todos os pedidos sem informa o nome
	 * */
	@GetMapping("/buscarp")
	public String buscar(@RequestParam(name="nome", required = false) String nome, @RequestParam(name="mostrarTodosDadosp", required=false) Boolean mostrarTodosDadosp, HttpSession sessao, ModelMap model) {
		
		List<Pedido> pedidosEncontrados = pedidoRepository.findByNome(nome);	
		
		model.addAttribute("pedidosEncontrados", pedidosEncontrados);
		
		if (mostrarTodosDadosp != null) {
			model.addAttribute("mostrarTodosDadosp", true);
		}
		
		return "usuario/buscarpedidos";
	}
	
	/*
	 * Objetivo: Responsável por remover o pedido do estudante
	 * */
	@GetMapping("/remover/{id}")
	public String remover(@PathVariable("id") Integer idPedido, HttpSession sessao, RedirectAttributes attr) {
		
		pedidoRepository.deleteById(idPedido);
		attr.addFlashAttribute("msgSucesso", "Pedido removido com sucesso!");
		
		return "redirect:/pedidos/buscarp";
	}
}
