package br.edu.ifrn.trabalhopi.controller;

/*
 * Objetivos: Classe responsável pela tela de inicio e login
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

	/*
	 * Objetivo: Executar a página html "inicio"
	 * */
	@GetMapping("/")
	public String inicio() {
		return "inicio";
	}
	
	/*
	 * Objetivo: Executar a página html "inicio"
	 * */
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	/*
	 * Objetivo: Executar a página html "inicio" com mensagem em casos de erro
	 * */
	@GetMapping("/login-error")
	public String loginError(ModelMap model) {
		model.addAttribute("msgErro", "Login ou senha incorrreto.");
		return "login";
	}
	
}