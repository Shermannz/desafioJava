package com.example.desafioJava.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.desafioJava.dto.EnderecoDTO;
import com.example.desafioJava.dto.PessoaDTO;
import com.example.desafioJava.services.exception.ResourceNotFoundException;

@Transactional
@SpringBootTest
public class EnderecoServiceIT {

	@Autowired
	private EnderecoService service;
	
	private long existingId;
	private long nonExistingId;
	private EnderecoDTO dto;
	
	@BeforeEach
	void setUp() throws Exception{
		 existingId = 1L;
		 nonExistingId = 50L;
		 dto = new EnderecoDTO(null,"rua abcd","1234",221,"Florianopolis",
				 new PessoaDTO(existingId,"alexandro",Instant.parse("1990-05-05T20:00:00Z")),true);
	}
	
	@Test
	public void deleteIfItDoesNotWork() {
		assertThrows(ResourceNotFoundException.class, () -> {
			service.deleteById(nonExistingId);
		});
	}
	
	@Test
	public void deleteIfItWorks() {
		assertDoesNotThrow(() -> {
			service.deleteById(existingId);
		});
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
		 assertNotNull(dto);
	}
	
	@Test 
	public void findAllIfItWorks() {
		List<EnderecoDTO> list = service.findAll();
		 assertNotNull(list);
	}
	
	
}
