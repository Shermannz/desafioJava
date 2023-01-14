package com.example.desafioJava.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.desafioJava.dto.EnderecoDTO;
import com.example.desafioJava.dto.PessoaDTO;
import com.example.desafioJava.entities.Endereco;
import com.example.desafioJava.entities.Pessoa;
import com.example.desafioJava.repositories.EnderecoRepository;
import com.example.desafioJava.services.exception.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class EnderecoServiceTests {

	@InjectMocks
	private EnderecoService service;

	@Mock
	private EnderecoRepository repository;

	private long existingId;
	private long nonExistingId;
	private Endereco endereco;
	private EnderecoDTO dto;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 2L;
		endereco = new Endereco(null, "rua abcd", "1234", 221, "Florianopolis",
				new Pessoa(existingId, "alexandro", Instant.parse("1990-05-05T20:00:00Z")), true);

		dto = new EnderecoDTO(null, "rua abcd", "1234", 221, "Florianopolis",
				new PessoaDTO(existingId, "alexandro", Instant.parse("1990-05-05T20:00:00Z")), true);

		when(repository.findById(existingId)).thenReturn(Optional.of(endereco));
		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		when(repository.findAll()).thenReturn(List.of(endereco));

		when(repository.getReferenceById(existingId)).thenReturn(endereco);
		when(repository.getReferenceById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

		when(repository.save(any())).thenReturn(endereco);

		doNothing().when(repository).deleteById(existingId);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		;

	}

	@Test
	public void deleteIfItDoesNotWork() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.deleteById(nonExistingId);
		});
	}

	@Test
	public void deleteIfItWorks() {
		Assertions.assertDoesNotThrow(() -> {
			service.deleteById(existingId);
		});
	}

	@Test
	public void updateIfItDoesNotWork() {
		assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingId, dto);
		});
	}

	@Test
	public void updateIfItWorks() {
		dto = service.update(existingId, dto);
		assertNotNull(dto);
	}

	@Test
	public void insertIfItWorks() {
		dto = service.insert(dto);
		assertNotNull(dto);

	}

	@Test
	public void findByIdIfItDoesNotWork() {
		assertThrows(RuntimeException.class, () -> {
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
