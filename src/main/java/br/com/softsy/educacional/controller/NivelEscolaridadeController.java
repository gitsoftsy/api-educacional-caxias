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

import br.com.softsy.educacional.dto.NivelEscolaridadeDTO;
import br.com.softsy.educacional.model.NivelEscolaridade;
import br.com.softsy.educacional.service.NivelEscolaridadeService;

@RestController
@RequestMapping("/nivelEscolaridade")
public class NivelEscolaridadeController {

	@Autowired
	NivelEscolaridadeService service;

	@GetMapping
	public ResponseEntity<List<NivelEscolaridade>> listar() {
		return ResponseEntity.ok(service.listarTudo());
	}

	@GetMapping("/{idNivelEscolaridade}")
	public ResponseEntity<NivelEscolaridadeDTO> buscarPorId(@PathVariable Long idNivelEscolaridade) {
		return ResponseEntity.ok(service.buscarPorId(idNivelEscolaridade));
	}

	@PostMapping
	public ResponseEntity<NivelEscolaridadeDTO> cadastrar(@RequestBody @Valid NivelEscolaridadeDTO dto) {
		NivelEscolaridadeDTO NivelEscolaridadeDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(NivelEscolaridadeDTO.getIdNivelEscolaridade()).toUri();
		return ResponseEntity.created(uri).body(NivelEscolaridadeDTO);
	}

	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid NivelEscolaridadeDTO dto) {
		return ResponseEntity.ok(service.atualizar(dto));
	}

	@PutMapping("/{idNivelEscolaridade}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idNivelEscolaridade) {
		service.ativaDesativa('S', idNivelEscolaridade);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{idNivelEscolaridade}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idNivelEscolaridade) {
		service.ativaDesativa('N', idNivelEscolaridade);
		return ResponseEntity.ok().build();
	}

}
