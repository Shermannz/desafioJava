package com.example.desafioJava.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.desafioJava.dto.PessoaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class PessoaResourceIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
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
	public void deleteIfItDoesNotWork() throws Exception {
		String json = objectMapper.writeValueAsString(dto);
		mockMvc.perform(delete("/pessoas/{id}",nonExistingId)
		 .accept(MediaType.APPLICATION_JSON)
		  .content(json)
		   .contentType(MediaType.APPLICATION_JSON))
		    .andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteIfItWorks() throws Exception {
		String json = objectMapper.writeValueAsString(dto);
		mockMvc.perform(delete("/pessoas/{id}",existingId)
		 .accept(MediaType.APPLICATION_JSON)
		  .content(json)
		   .contentType(MediaType.APPLICATION_JSON))
	        .andExpect(status().isNoContent());
	}
	
	@Test
	public void updateIfItDoesNotWork() throws Exception {
		String json = objectMapper.writeValueAsString(dto);
		mockMvc.perform(put("/pessoas/{id}",nonExistingId)
		 .accept(MediaType.APPLICATION_JSON)
		  .content(json)
		   .contentType(MediaType.APPLICATION_JSON))
		    .andExpect(status().isNotFound());
	}
	
	@Test
	public void updateIfItWorks() throws Exception {
		String json = objectMapper.writeValueAsString(dto);
		mockMvc.perform(put("/pessoas/{id}",existingId)
		 .accept(MediaType.APPLICATION_JSON)
		  .content(json)
		   .contentType(MediaType.APPLICATION_JSON))
		    .andExpect(status().isOk());
	}
	
	@Test
	public void insertIfItWorks() throws Exception {
	  String json = objectMapper.writeValueAsString(dto);
		mockMvc.perform(post("/pessoas")
		 .accept(MediaType.APPLICATION_JSON)
		  .content(json)
		   .contentType(MediaType.APPLICATION_JSON))
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
		    .andExpect(status().isOk())
        	 .andExpect(jsonPath(".name").exists());
	}
}
