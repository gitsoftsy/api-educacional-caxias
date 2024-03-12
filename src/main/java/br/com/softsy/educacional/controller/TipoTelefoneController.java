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

import br.com.softsy.educacional.dto.TipoTelefoneDTO;
import br.com.softsy.educacional.model.TipoTelefone;
import br.com.softsy.educacional.service.TipoTelefoneService;

@RestController
@RequestMapping("/tipoTelefone")
public class TipoTelefoneController {
	
	@Autowired TipoTelefoneService service;
	
	@GetMapping
	public ResponseEntity<List<TipoTelefone>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}

	
	@GetMapping("/{idTipoTelefone}")
	public ResponseEntity<TipoTelefoneDTO> buscarPorId(@PathVariable Long idTipoTelefone){
		return ResponseEntity.ok(service.buscarPorId(idTipoTelefone));
	}
	
	@PostMapping
	public ResponseEntity<TipoTelefoneDTO> cadastrar(@RequestBody @Valid TipoTelefoneDTO dto){
		TipoTelefoneDTO FormaDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(FormaDTO.getIdTipoTelefone()).toUri();
		return ResponseEntity.created(uri).body(FormaDTO);
	}		

}
