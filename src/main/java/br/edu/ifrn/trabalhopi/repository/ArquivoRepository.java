package br.edu.ifrn.trabalhopi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifrn.trabalhopi.dominio.Arquivo;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long>{

}
