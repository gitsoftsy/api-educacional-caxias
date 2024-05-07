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

import br.com.softsy.educacional.dto.LinguaEnsinoDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.model.LinguaEnsino;
import br.com.softsy.educacional.service.LinguaEnsinoService;

@RestController
@RequestMapping("/linguaEnsino")
public class LinguaEnsinoController {
	
	@Autowired LinguaEnsinoService service;
	
	@GetMapping("/conta/{idConta}")
	public ResponseEntity<List<LinguaEnsinoDTO>> buscarPorIdConta(@PathVariable Long idConta){
		List<LinguaEnsinoDTO> linguaEnsino = service.buscarPorIdConta(idConta);
		return ResponseEntity.ok(linguaEnsino);
	}
	
	@GetMapping("/{idLinguaEnsino}")
	public ResponseEntity<LinguaEnsinoDTO> buscarPorId(@PathVariable Long idLinguaEnsino){
		return ResponseEntity.ok(service.buscarPorId(idLinguaEnsino));
	}
	
	@PostMapping
	public ResponseEntity<LinguaEnsinoDTO> cadastrar(@RequestBody @Valid LinguaEnsinoDTO dto){
		LinguaEnsinoDTO linguaEnsino = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(linguaEnsino.getIdLinguaEnsino()).toUri();
		return ResponseEntity.created(uri).body(linguaEnsino);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid LinguaEnsinoDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@PutMapping("/{idLinguaEnsino}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idLinguaEnsino){
		service.ativaDesativa('S', idLinguaEnsino);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idLinguaEnsino}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idLinguaEnsino){
		service.ativaDesativa('N', idLinguaEnsino);
		return ResponseEntity.ok().build();
	}

}
