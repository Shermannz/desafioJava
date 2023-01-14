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

import com.example.desafioJava.dto.EnderecoDTO;
import com.example.desafioJava.entities.Endereco;
import com.example.desafioJava.repositories.EnderecoRepository;
import com.example.desafioJava.services.exception.DatabaseException;
import com.example.desafioJava.services.exception.ResourceNotFoundException;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository repository;

	@Transactional(readOnly = true)
	public List<EnderecoDTO> findAll() {
		List<Endereco> list = repository.findAll();
		return list.stream().map(x -> new EnderecoDTO(x)).collect(Collectors.toList());
	}

	@Transactional
	public EnderecoDTO findById(Long id) {
		Optional<Endereco> optional = repository.findById(id);
		Endereco endereco = optional.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new EnderecoDTO(endereco);
	}

	@Transactional
	public EnderecoDTO insert(EnderecoDTO dto) {
		Endereco entity = new Endereco();
		copyDtoToEntity(dto, entity);
		Endereco p = repository.getReferenceById(dto.getPessoa().getId());
		entity.setPessoa(p.getPessoa());
		entity = repository.save(entity);

		return new EnderecoDTO(entity);
	}

	@Transactional
	public EnderecoDTO update(Long id, EnderecoDTO dto) {
		try {
			Endereco entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new EnderecoDTO(entity);
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

	private void copyDtoToEntity(EnderecoDTO dto, Endereco entity) {
		entity.setPlace(dto.getPlace());
		entity.setCep(dto.getCep());
		entity.setCity(dto.getCity());
		entity.setNum(dto.getNum());
		entity.setCurrent(dto.getCurrent());

	}
}
