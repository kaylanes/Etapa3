package br.edu.ifrn.trabalhopi.controller;

/*
 * Objetivos: Essa classe tem como objetivo realizar o download de fotos 
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
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.edu.ifrn.trabalhopi.dominio.Arquivo;
import br.edu.ifrn.trabalhopi.repository.ArquivoRepository;

@Controller
public class DownloadArquivoController {
	
	@Autowired
	private ArquivoRepository arquivoRepository;
	
	/*
	 * Objetivo: Executa o download da imagem
	 * */
	@GetMapping("/download/{idArquivo}")
	public ResponseEntity<?> downloadFile(@PathVariable Long idArquivo, @PathParam("salvar") String salvar) {
		
		Arquivo arquivoBD = arquivoRepository.findById(idArquivo).get();
		
		String texto = (salvar == null || salvar.equals("true")) ? "attachment; filename=\"" + arquivoBD.getNome() + "\"" : "inline; filename=\"" + arquivoBD.getNome() + "\"";
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(arquivoBD.getTipoArquivo())).header(HttpHeaders.CONTENT_DISPOSITION, texto).body(new ByteArrayResource(arquivoBD.getDados()));
	}
	
}
