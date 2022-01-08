package br.edu.ifrn.trabalhopi.service;

/*
 * Objetivos: Classe para verificar se o login informado está cadastrado
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifrn.trabalhopi.dominio.Usuario;
import br.edu.ifrn.trabalhopi.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = repository.findByMatricula(username)
				.orElseThrow(() ->
					new UsernameNotFoundException("Usuário não encontrado."));
		
		return new User(
				usuario.getMatricula(),
				usuario.getSenha(),
				AuthorityUtils.createAuthorityList(usuario.getPerfil())
		);
		
	}
	

}
