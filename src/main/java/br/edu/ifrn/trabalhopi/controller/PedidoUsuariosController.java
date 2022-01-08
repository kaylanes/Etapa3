package br.edu.ifrn.trabalhopi.controller;

/*
 * Objetivos: Essa classe tem como objetivo principal o cadastro de pedidos dos estudantes, a validação dos campos,
 * edição dos pedidos e um relacionamento entre disciplinas
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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.edu.ifrn.trabalhopi.dominio.Pedido;
import br.edu.ifrn.trabalhopi.dominio.Disciplina;
import br.edu.ifrn.trabalhopi.dto.AutocompleteDTO;
import br.edu.ifrn.trabalhopi.repository.DisciplinaRepository;
import br.edu.ifrn.trabalhopi.repository.PedidoRepository;
@Controller
@RequestMapping("/pedidos")
public class PedidoUsuariosController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private DisciplinaRepository disciplinaRepository;
	
	/*
	 * Objetivo: Executar a página html "cadastrorealizado"
	 * */
	@GetMapping("/cadastropedido")
	public String entrarPedido(ModelMap model) {
		model.addAttribute("pedido", new Pedido());
		
		return "usuario/cadastropedido";		
	}
	
	/*
	 * Objetivo: Realiza validação dos campos do pedido
	 * */
	private List<String> validarDados(Pedido pedido) {
		List<String> msgs = new ArrayList<>();
		
		if (pedido.getNome() == null || pedido.getNome().isEmpty()) {
			msgs.add("O campo nome é obrigatório.");
		}
		
		if (pedido.getMatricula() == null || pedido.getMatricula().isEmpty()) {
			msgs.add("O campo matrícula é obrigatório.");
		
		} else if (pedido.getMatricula().length() < 14) {
			msgs.add("A matrícula é composta por 14 números.");
		}
		
		if (pedido.getCurso() == null || pedido.getCurso().isEmpty()) {
			msgs.add("O campo curso é obrigatório.");
		}
		
		if (pedido.getMotivo() == null || pedido.getMotivo().isEmpty()) {
			msgs.add("Explicar o motivo o obrigatório.");
		}
		
		return msgs;
	}
	
	/*
	 * Objetivo: Salva o pedido do estudante
	 * */
	@PostMapping("/salvar")
	public String salvar(Pedido pedido, RedirectAttributes attr, ModelMap model, HttpSession sessao) {
		List<String> msgValidacao = validarDados(pedido);
		
		if (!msgValidacao.isEmpty()) {
			model.addAttribute("msgsErro", msgValidacao);
			
			return "usuario/cadastropedido";
	}
		pedidoRepository.save(pedido);
		
		attr.addFlashAttribute("msgSucesso", "Operação realizada com sucesso!");
		
		return "redirect:/pedidos/cadastropedido";
	}
	
	/*
	 * Objetivo: Realiza a edição do pedido do estudante caso ele deseje
	 * */
	@GetMapping("/editar/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idPedido, ModelMap model, HttpSession sessao) {
		Pedido p = pedidoRepository.findById(idPedido).get();
		
		model.addAttribute("pedido", p);
		
		return "/usuario/cadastropedido";
	}
	
	/*
	 * Objetivo: Relacionamente entre disciplinas 
	 * */
	@GetMapping("autocompleteDisciplinas")
	@Transactional(readOnly = true)
	@ResponseBody
	public List<AutocompleteDTO> autocompleteDisciplinas(@RequestParam("term") String termo){
		List<Disciplina> disciplinas = disciplinaRepository.findByNome(termo);
		
		List<AutocompleteDTO> resultados = new ArrayList<>();
		
		disciplinas.forEach(p -> resultados.add(new AutocompleteDTO(p.getNome(), p.getId())));
		
		return resultados;
	}
}
