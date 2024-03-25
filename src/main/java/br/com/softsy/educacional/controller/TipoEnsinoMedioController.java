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

import br.com.softsy.educacional.dto.TipoEnsinoMedioDTO;
import br.com.softsy.educacional.model.TipoEnsinoMedio;
import br.com.softsy.educacional.service.TipoEnsinoMedioService;

@RestController
@RequestMapping("/tipoEnsinoMedio")
public class TipoEnsinoMedioController {

	@Autowired
	TipoEnsinoMedioService service;

	@GetMapping
	public ResponseEntity<List<TipoEnsinoMedio>> listar() {
		return ResponseEntity.ok(service.listarTudo());
	}

	@GetMapping("/{idTipoEnsinoMedio}")
	public ResponseEntity<TipoEnsinoMedioDTO> buscarPorId(@PathVariable Long idTipoEnsinoMedio) {
		return ResponseEntity.ok(service.buscarPorId(idTipoEnsinoMedio));
	}

	@PostMapping
	public ResponseEntity<TipoEnsinoMedioDTO> cadastrar(@RequestBody @Valid TipoEnsinoMedioDTO dto) {
		TipoEnsinoMedioDTO TipoEnsinoMedioDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(TipoEnsinoMedioDTO.getIdTipoEnsinoMedio()).toUri();
		return ResponseEntity.created(uri).body(TipoEnsinoMedioDTO);
	}

	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid TipoEnsinoMedioDTO dto) {
		return ResponseEntity.ok(service.atualizar(dto));
	}

	@PutMapping("/{idTipoEnsinoMedio}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idTipoEnsinoMedio) {
		service.ativaDesativa('S', idTipoEnsinoMedio);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{idTipoEnsinoMedio}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idTipoEnsinoMedio) {
		service.ativaDesativa('N', idTipoEnsinoMedio);
		return ResponseEntity.ok().build();
	}

}
