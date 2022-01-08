package br.edu.ifrn.trabalhopi.security;

/*
 * Objetivos: Classe que permite e bloqueia o acesso a outras páginas e direciona para a página inical caso o login esteja correto
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
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.edu.ifrn.trabalhopi.dominio.Usuario;
import br.edu.ifrn.trabalhopi.service.UsuarioService;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioService service;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*http.authorizeRequests().anyRequest().permitAll();*/
		
		http.authorizeRequests()
			.antMatchers("/css/**", "/imagens/**", "/js/**").permitAll()
			.antMatchers("/publico/**").permitAll()
			
			.antMatchers("/usuarios/cadastro", "/usuarios/salvar", "/usuarios/editar/**", "/usuarios/remover/**").hasAnyAuthority(Usuario.ADMIM)
			
			.anyRequest().authenticated()
		.and()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/", true)
			.failureUrl("/login-error")
			.permitAll()
		.and()
			.logout()
			.logoutSuccessUrl("/");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}

}
