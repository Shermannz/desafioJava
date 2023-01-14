package com.example.desafioJava.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.desafioJava.dto.PessoaDTO;
import com.example.desafioJava.entities.Pessoa;
import com.example.desafioJava.repositories.PessoaRepository;
import com.example.desafioJava.services.exception.DatabaseException;
import com.example.desafioJava.services.exception.ResourceNotFoundException;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;

	@Transactional(readOnly = true)
	public List<PessoaDTO> findAll() {
		List<Pessoa> list = repository.findAll();
		return list.stream().map(x -> new PessoaDTO(x)).collect(Collectors.toList());
	}

	@Transactional
	public PessoaDTO findById(Long id) {
		Optional<Pessoa> optional = repository.findById(id);
		Pessoa pessoa = optional.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new PessoaDTO(pessoa, pessoa.getEndereco());
	}

	@Transactional
	public PessoaDTO insert(PessoaDTO dto) {
		Pessoa entity = new Pessoa();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);

		return new PessoaDTO(entity, entity.getEndereco());
	}

	@Transactional
	public PessoaDTO update(Long id, PessoaDTO dto) {
		try {
			Pessoa entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new PessoaDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void deleteById(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	private void copyDtoToEntity(PessoaDTO dto, Pessoa entity) {
		entity.setName(dto.getName());
		entity.setBirthDate(dto.getBirthDate());
		entity.getEndereco().clear();
	}
}
