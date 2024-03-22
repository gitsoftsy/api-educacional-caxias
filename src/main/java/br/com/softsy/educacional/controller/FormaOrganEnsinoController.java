package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.FormaOrganEnsinoDTO;
import br.com.softsy.educacional.model.FormaOrganEnsino;
import br.com.softsy.educacional.service.FormaOrganEnsinoService;

@RestController
@RequestMapping("/formaOrganEnsino")
public class FormaOrganEnsinoController {

	@Autowired
	FormaOrganEnsinoService service;

	@GetMapping
	public ResponseEntity<List<FormaOrganEnsino>> listar() {
		return ResponseEntity.ok(service.listarTudo());
	}

	@GetMapping("/{idFormaOrganEnsino}")
	public ResponseEntity<FormaOrganEnsinoDTO> buscarPorId(@PathVariable Long idFormaOrganEnsino) {
		return ResponseEntity.ok(service.buscarPorId(idFormaOrganEnsino));
	}

	@PostMapping
	public ResponseEntity<FormaOrganEnsinoDTO> cadastrar(@RequestBody @Valid FormaOrganEnsinoDTO dto) {
		FormaOrganEnsinoDTO FormaOrganEnsinoDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(FormaOrganEnsinoDTO.getIdFormaOrganEnsino()).toUri();
		return ResponseEntity.created(uri).body(FormaOrganEnsinoDTO);
	}

	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid FormaOrganEnsinoDTO dto) {
		return ResponseEntity.ok(service.atualizar(dto));
	}

	@DeleteMapping("/{idFormaOrganEnsino}")
	public ResponseEntity<?> excluir(@PathVariable Long idFormaOrganEnsino) {
		service.remover(idFormaOrganEnsino);
		return ResponseEntity.ok().build();
	}

}
