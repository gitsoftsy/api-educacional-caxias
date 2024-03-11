package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.DestinacaoLixoDTO;
import br.com.softsy.educacional.dto.FormaOcupacaoPredioDTO;
import br.com.softsy.educacional.model.DestinacaoLixo;
import br.com.softsy.educacional.model.FormaOcupacaoPredio;
import br.com.softsy.educacional.service.FormaOcupacaoPredioService;

@RestController
@RequestMapping("/formaOcupacaoPredio")
public class FormaOcupacaoPredioController {
	
	
	@Autowired FormaOcupacaoPredioService service;
	
	@GetMapping
	public ResponseEntity<List<FormaOcupacaoPredio>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}

	
	@GetMapping("/{idFormaOcupacaoPredio}")
	public ResponseEntity<FormaOcupacaoPredioDTO> buscarPorId(@PathVariable Long idFormaOcupacaoPredio){
		return ResponseEntity.ok(service.buscarPorId(idFormaOcupacaoPredio));
	}
	
	@PostMapping
	public ResponseEntity<FormaOcupacaoPredioDTO> cadastrar(@RequestBody @Valid FormaOcupacaoPredioDTO dto){
		FormaOcupacaoPredioDTO FormaDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(FormaDTO.getIdFormaOcupacaoPredio()).toUri();
		return ResponseEntity.created(uri).body(FormaDTO);
	}
}
