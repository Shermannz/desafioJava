package com.example.desafioJava.dto;

import java.io.Serializable;

import com.example.desafioJava.entities.Endereco;

public class EnderecoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String place;
	private String cep;
	private Integer num;
	private String city;
    private Boolean current;
    private PessoaDTO pessoa;
	
	public EnderecoDTO() {
	}

	public EnderecoDTO(Long id, String place, String cep, Integer num, String city, PessoaDTO pessoa, Boolean current) {
		this.id = id;
		this.place = place;
		this.cep = cep;
		this.num = num;
		this.city = city;
		this.pessoa = pessoa;
		this.current = current;
	}
	
	public EnderecoDTO(Endereco end) {
		id = end.getId();
		place = end.getPlace();
		cep = end.getCep();
		num = end.getNum();
		city = end.getCity();
		current = end.getCurrent();
	    pessoa = new PessoaDTO(end.getPessoa());

	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Boolean getCurrent() {
		return current;
	}

	public void setCurrent(Boolean current) {
		this.current = current;
	}

	public PessoaDTO getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaDTO pessoa) {
		this.pessoa = pessoa;
	}

}
