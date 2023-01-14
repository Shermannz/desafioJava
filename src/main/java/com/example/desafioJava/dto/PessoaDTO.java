package com.example.desafioJava.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.desafioJava.entities.Endereco;
import com.example.desafioJava.entities.Pessoa;

public class PessoaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private Instant birthDate;
	
	private List<EnderecoDTO> endereco = new ArrayList<>();
	
	public PessoaDTO() {
	}

	public PessoaDTO(Long id, String name, Instant birthDate) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}
	
	public PessoaDTO(Pessoa pessoa) {
		id = pessoa.getId();
		name = pessoa.getName();
		birthDate = pessoa.getBirthDate();
	}
	public PessoaDTO(Pessoa pessoa, Set<Endereco> endereco2) {
		this(pessoa);
	    endereco2.forEach(x -> this.endereco.add(new EnderecoDTO(x)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Instant birthDate) {
		this.birthDate = birthDate;
	}

	public List<EnderecoDTO> getEndereco() {
		return endereco;
	}

}
