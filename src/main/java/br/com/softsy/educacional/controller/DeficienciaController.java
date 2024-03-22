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

import br.com.softsy.educacional.dto.DeficienciaDTO;
import br.com.softsy.educacional.model.Deficiencia;
import br.com.softsy.educacional.service.DeficienciaService;

@RestController
@RequestMapping("/deficiencia")
public class DeficienciaController {

	@Autowired
	DeficienciaService service;

	@GetMapping
	public ResponseEntity<List<Deficiencia>> listar() {
		return ResponseEntity.ok(service.listarTudo());
	}

	@GetMapping("/{idDeficiencia}")
	public ResponseEntity<DeficienciaDTO> buscarPorId(@PathVariable Long idDeficiencia) {
		return ResponseEntity.ok(service.buscarPorId(idDeficiencia));
	}

	@PostMapping
	public ResponseEntity<DeficienciaDTO> cadastrar(@RequestBody @Valid DeficienciaDTO dto) {
		DeficienciaDTO DeficienciaDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(DeficienciaDTO.getIdDeficiencia()).toUri();
		return ResponseEntity.created(uri).body(DeficienciaDTO);
	}

	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid DeficienciaDTO dto) {
		return ResponseEntity.ok(service.atualizar(dto));
	}

	@PutMapping("/{idDeficiencia}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idDeficiencia) {
		service.ativaDesativa('S', idDeficiencia);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{idDeficiencia}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idDeficiencia) {
		service.ativaDesativa('N', idDeficiencia);
		return ResponseEntity.ok().build();
	}
}
