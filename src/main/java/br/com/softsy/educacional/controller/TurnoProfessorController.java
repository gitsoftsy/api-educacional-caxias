package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.TurnoProfessorDTO;
import br.com.softsy.educacional.model.TurnoProfessor;
import br.com.softsy.educacional.service.TurnoProfessorService;

@RestController
@RequestMapping("/turnoProfessor")
public class TurnoProfessorController {

	@Autowired
	TurnoProfessorService service;

	@GetMapping
	public ResponseEntity<List<TurnoProfessor>> listar() {
		return ResponseEntity.ok(service.listarTudo());
	}

	@GetMapping("/{idTurnoProfessor}")
	public ResponseEntity<TurnoProfessorDTO> buscarPorId(@PathVariable Long idTurnoProfessor) {
		return ResponseEntity.ok(service.buscarPorId(idTurnoProfessor));
	}

	@PostMapping
	public ResponseEntity<TurnoProfessorDTO> cadastrar(@RequestBody @Valid TurnoProfessorDTO dto) {
		TurnoProfessorDTO TurnoProfessorDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(TurnoProfessorDTO.getIdTurnoProfessor()).toUri();
		return ResponseEntity.created(uri).body(TurnoProfessorDTO);
	}

	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid TurnoProfessorDTO dto) {
		return ResponseEntity.ok(service.atualizar(dto));
	}

	@PutMapping("/{idDestinacaoLixo}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idDestinacaoLixo) {
		service.ativaDesativa('S', idDestinacaoLixo);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{idTurnoProfessor}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idTurnoProfessor) {
		service.ativaDesativa('N', idTurnoProfessor);
		return ResponseEntity.ok().build();
	}

}
