package br.edu.ifrn.trabalhopi.controller;

/*
* Objetivos: Essa é uma classe que para abrir uma tela com dois cards para o estudante
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

	
	@Controller
	@RequestMapping("/pedidos")
	public class PedidoRealizadoController {
	
		/*
		 * Objetivo: Executar a página html "pedidorealizado"
		 * */
		@GetMapping("/realizado")
		public String pedidoFeito() {
		return "usuario/pedidorealizado";
}



}