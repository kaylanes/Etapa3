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

import br.edu.ifrn.trabalhopi.dominio.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer>{

	@Query("select p from Disciplina p where p.nome like %:nome%")
	List<Disciplina> findByNome(@Param("nome") String nome);
	
}
