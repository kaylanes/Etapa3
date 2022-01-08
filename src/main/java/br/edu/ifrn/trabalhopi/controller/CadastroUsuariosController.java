package br.edu.ifrn.trabalhopi.controller;

/*
 * Objetivos: Essa classe tem como objetivo principal cadastrar administradores, realizar avalidação dos campos eeditar o cadastro 
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.trabalhopi.dominio.Arquivo;
import br.edu.ifrn.trabalhopi.dominio.Usuario;
import br.edu.ifrn.trabalhopi.repository.ArquivoRepository;
import br.edu.ifrn.trabalhopi.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class CadastroUsuariosController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ArquivoRepository arquivoRepository;
	
	/*
	 * Objetivo: Executar a página html "cadastro"
	 * */
	@GetMapping("/cadastro")
	public String entrarCadastro(ModelMap model) {
		model.addAttribute("usuario", new Usuario());
		return "usuario/cadastro";
	}
	
	/*
	 * Objetivo: Realizar a validação dos campos dos administradores
	 * */
	private List<String> validarDados(Usuario usuario) {
		List<String> msgs = new ArrayList<>();
		
		if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
			msgs.add("O campo nome é obrigatório.");
		} else if (usuario.getNome().length() < 2) {
			msgs.add("O campo nome deve ter pelo menos 2 caracteres.");
		}
		
		if (usuario.getMatricula() == null || usuario.getMatricula().isEmpty()) {
			msgs.add("O campo matrícula é obrigatório.");
		} else if (usuario.getMatricula().length() < 14) {
			msgs.add("A matrícula é composta por 14 números.");
		}
		
		if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
			msgs.add("O campo e-mail é obrigatório.");
		}
		
		if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
			msgs.add("O campo senha é obrigatório.");
		} else if (usuario.getSenha().length() < 4) {
			msgs.add("Sua senha precisa ter no mínimo 4 dígitos");
		}
		
		if (usuario.getConfirmar() == null || usuario.getConfirmar().isEmpty()) {
			msgs.add("O campo confirmar é obrigatório.");
		}
		
		if(usuario.getSenha().equals(usuario.getConfirmar())) {
			
		} else {
			msgs.add("As senhas informadas não conferem. Tente novamente.");
		}
		
		return msgs;
	}
	
	/*
	 * Objetivo: Salva o cadastro do administrador e criptografa a senha
	 * */
	@PostMapping("/salvar")
	@Transactional(readOnly = false)
	public String salvar(Usuario usuario, ModelMap model, RedirectAttributes attr, @RequestParam("file") MultipartFile arquivo, HttpSession sessao) {
		
		List<String> msgValidacao = validarDados(usuario);
		
		if (!msgValidacao.isEmpty()) {
			model.addAttribute("msgsErro", msgValidacao);
			return "usuario/cadastro";
		}
		
		try {
			
			if (arquivo != null && !arquivo.isEmpty()) {
				String nomeArquivo = StringUtils.cleanPath(arquivo.getOriginalFilename());
				
				Arquivo arquivoBD = new Arquivo(null, nomeArquivo, arquivo.getContentType(), arquivo.getBytes());
			
				arquivoRepository.save(arquivoBD);
				
				if (usuario.getFoto() != null && usuario.getFoto().getId() != null && usuario.getFoto().getId() > 0) {
					
					arquivoRepository.delete(usuario.getFoto());
				}
				
				usuario.setFoto(arquivoBD);
				
			} else {
				usuario.setFoto(null);
			}
			
			String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
			usuario.setSenha(senhaCriptografada);

		usuarioRepository.save(usuario);
		attr.addFlashAttribute("msgSucesso", "Operação realizada com sucesso!");
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/usuarios/cadastro";
	}
	
	/*
	 * Objetivo: Para o administrador realizar uma edição
	 * */
	@GetMapping("/editar/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idUsuario, ModelMap model, HttpSession sessao) {
		
		Usuario u = usuarioRepository.findById(idUsuario).get();
		
		model.addAttribute("usuario", u);
		
		return "/usuario/cadastro";
	}

}
