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

import br.com.softsy.educacional.dto.TipoDependenciaDTO;
import br.com.softsy.educacional.model.TipoDependencia;
import br.com.softsy.educacional.service.TipoDependenciaService;


@RestController
@RequestMapping("/tipoDependencia")
public class TipoDependenciaController {

	@Autowired TipoDependenciaService service;
	
	@GetMapping
	public ResponseEntity<List<TipoDependencia>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}

	
	@GetMapping("/{idTipoDependencia}")
	public ResponseEntity<TipoDependenciaDTO> buscarPorId(@PathVariable Long idTipoDependencia){
		return ResponseEntity.ok(service.buscarPorId(idTipoDependencia));
	}
	
	@PostMapping
	public ResponseEntity<TipoDependenciaDTO> cadastrar(@RequestBody @Valid TipoDependenciaDTO dto){
		TipoDependenciaDTO FormaDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(FormaDTO.getIdTipoDependencia()).toUri();
		return ResponseEntity.created(uri).body(FormaDTO);
	}	
	
}
