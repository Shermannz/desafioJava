package com.example.desafioJava.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.desafioJava.dto.PessoaDTO;
import com.example.desafioJava.services.PessoaService;
import com.example.desafioJava.services.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PessoaResource.class)
public class PessoaResourceTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private PessoaService service;
	
	private long existingId;
	private long nonExistingId;
	private PessoaDTO dto;
	
	@BeforeEach
	void setUp() throws Exception{
		 existingId = 1L;
		 nonExistingId = 50L;
		 dto = new PessoaDTO(null,"alexandro",Instant.parse("1990-05-05T20:00:00Z"));
		 
		 when(service.findAll()).thenReturn(List.of(dto));
		 when(service.findById(existingId)).thenReturn(dto);
		 when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
		 
		 when(service.insert(any())).thenReturn(dto);
		 when(service.update(eq(existingId), any())).thenReturn(dto);
		 when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
		 
		 doNothing().when(service).deleteById(existingId);
		 doThrow(ResourceNotFoundException.class).when(service).deleteById(nonExistingId);
		 
	}

	@Test
	public void deleteSeDerErrado() throws Exception {
		ResultActions result = mockMvc.perform(delete("/pessoas/{id}",nonExistingId)
		 .accept(MediaType.APPLICATION_JSON));
		   result.andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteSeDerCerto() throws Exception {
		ResultActions result = mockMvc.perform(delete("/pessoas/{id}",existingId)
		 .accept(MediaType.APPLICATION_JSON));
		   result.andExpect(status().isNoContent());
	}
	
	@Test
	public void updateIfItDoesNotWork() throws Exception {
		String json = objectMapper.writeValueAsString(dto);
		mockMvc.perform(put("/pessoas/{id}",nonExistingId)
		 .content(json)
		  .contentType(MediaType.APPLICATION_JSON)
		   .accept(MediaType.APPLICATION_JSON))
		   .andExpect(status().isNotFound());
	}
	
	@Test
	public void updateIfItWorks() throws Exception {
		String json = objectMapper.writeValueAsString(dto);
		mockMvc.perform(put("/pessoas/{id}",existingId)
		.content(json)
	     .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
		  .andExpect(status().isOk());
	}
	
	@Test
	public void insertIfItWorks() throws Exception {
		String json = objectMapper.writeValueAsString(dto);
		mockMvc.perform(post("/pessoas")
		.content(json)
		 .contentType(MediaType.APPLICATION_JSON)
		  .accept(MediaType.APPLICATION_JSON))
		  .andExpect(status().isCreated());
	}

	@Test
	public void findByIdIfItDoesNotWork() throws Exception {
		mockMvc.perform(get("/pessoas/{id}",nonExistingId)
		 .accept(MediaType.APPLICATION_JSON))
		  .andExpect(status().isNotFound());
	}
	
	@Test
	public void findByIdIfItWorks() throws Exception {
		mockMvc.perform(get("/pessoas/{id}",existingId)
		 .accept(MediaType.APPLICATION_JSON))
		  .andExpect(status().isOk());
	}
	
	@Test
	public void findAllIfItWorks() throws Exception {
		mockMvc.perform(get("/pessoas")
		 .accept(MediaType.APPLICATION_JSON))
		  .andExpect(status().isOk());
	}
}
