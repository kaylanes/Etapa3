package br.edu.ifrn.trabalhopi.repository;

/*
 * Objetivos: Classe para consultas no MySQL
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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.trabalhopi.dominio.Pedido;
import br.edu.ifrn.trabalhopi.dominio.Usuario;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

	@Query("select u from Pedido u where u.nome like %:nome%")
	List<Pedido> findByNome(@Param("nome") String nome);
	
	@Query("select u from Pedido u where u.matricula like %:matricula%")
	List<Pedido> findByUsuario(@Param("matricula") String matricula);

}
