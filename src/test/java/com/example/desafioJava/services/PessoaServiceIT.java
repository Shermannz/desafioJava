package com.example.desafioJava.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.desafioJava.dto.PessoaDTO;
import com.example.desafioJava.repositories.PessoaRepository;
import com.example.desafioJava.services.exception.ResourceNotFoundException;

@Transactional
@SpringBootTest
public class PessoaServiceIT {

	@Autowired
	private PessoaService service;
	
	@Autowired
	private PessoaRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private PessoaDTO dto;
	
	@BeforeEach
	void setUp() throws Exception{
		 existingId = 1L;
		 nonExistingId = 50L;
		 dto = new PessoaDTO(null,"alexandro",Instant.parse("1990-05-05T20:00:00Z"));
	}
	
	@Test
	public void deleteIfItDoesNotWork() {
		assertThrows(ResourceNotFoundException.class, () -> {
			service.deleteById(nonExistingId);
		});
	}
	
	@Test
	public void deleteIfItWorks() {
		 service.deleteById(existingId);
			assertEquals(2, repository.count());
	}
	
	@Test
	public void updateIfItDoesNotWork() {
		assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingId,dto);
		});
	}
	
	@Test
	public void updateIfItWorks() {
		dto = service.update(existingId,dto);
		 assertNotNull(dto);
	}
	
	@Test
	public void insertIfItWorks() {
		dto = service.insert(dto);
		 assertEquals(4L, dto.getId());
		  assertNotNull(dto);
	}
	@Test
	public void findByIdIfItDoesNotWork() {
		assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
	}
	
	@Test
	public void findByIdIfItWorks() {
		dto = service.findById(existingId);
		 assertEquals("Alex",dto.getName());
		  assertNotNull(dto);
	}
	
	@Test 
	public void findAllIfItWorks() {
		List<PessoaDTO> list = service.findAll();
		 assertEquals("Alex",list.get(0).getName());
		  assertNotNull(list);
	}
	
	
}
