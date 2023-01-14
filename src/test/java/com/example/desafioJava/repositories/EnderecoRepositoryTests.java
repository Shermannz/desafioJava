package com.example.desafioJava.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.example.desafioJava.entities.Endereco;
import com.example.desafioJava.entities.Pessoa;

@DataJpaTest
public class EnderecoRepositoryTests {

	@Autowired
	private EnderecoRepository repository;
	
	private Long existingId;
	private Long nonExistingId;
	private Endereco p;
	private Optional<Endereco> pp;
	
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		p = new Endereco(null,"rua abc","1012600",120,"Sao paulo",new Pessoa(),true);
	}
	
	@Test
	public void deleteIfItDoesNotWorkWork() {
		 assertThrows(EmptyResultDataAccessException.class,() -> { 
			repository.deleteById(nonExistingId);
		});
	}
	
	@Test
	public void deleteIfItWorks() {
		repository.deleteById(existingId);
		 pp = repository.findById(existingId);
		
		 assertTrue(pp.isEmpty());
	}
	
	@Test
	public void updateIfItDoesNotWorkWork() {
		 assertThrows(EntityNotFoundException.class,() -> { 
		   assertNull(repository.getReferenceById(nonExistingId));
		});
	}
	
	@Test
	public void updateIfItWorks() {
		p = repository.getReferenceById(existingId);
		  assertEquals(true, p.getCurrent());
		
	}
	
	@Test
	public void insertIfItWorks() {
		assertNotNull(repository.save(p));
	}
	
	@Test
	public void findByIdIfItWorks() {
		pp = repository.findById(existingId);
		  assertNotNull(pp);
	}
	
	@Test
	public void findByIdIfItDoesNotWork() {
		pp = repository.findById(nonExistingId);
		  assertTrue(pp.isEmpty());
	}
	
	@Test
	public void findAllIfItWorks() {
		List<Endereco> endereco = repository.findAll();
		  assertNotNull(endereco);
	}
	
}
