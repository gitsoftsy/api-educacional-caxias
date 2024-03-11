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

import br.com.softsy.educacional.dto.EsgotamentoSanitarioDTO;
import br.com.softsy.educacional.model.EsgotamentoSanitario;
import br.com.softsy.educacional.service.EsgotamentoSanitarioService;

@RestController
@RequestMapping("/esgotamentoSanitario")
public class EsgotamentoSanitarioController {

	@Autowired EsgotamentoSanitarioService service;
	
	@GetMapping
	public ResponseEntity<List<EsgotamentoSanitario>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}
	
	@GetMapping("/{idEsgotamentoSanitario}")
	public ResponseEntity<EsgotamentoSanitarioDTO> buscarPorId(@PathVariable Long idEsgotamentoSanitario){
		return ResponseEntity.ok(service.buscarPorId(idEsgotamentoSanitario));
	}
	
	@PostMapping
	public ResponseEntity<EsgotamentoSanitarioDTO> cadastrar(@RequestBody @Valid EsgotamentoSanitarioDTO dto){
		EsgotamentoSanitarioDTO EsgotamentoDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(EsgotamentoDTO.getIdEsgotamentoSanitario()).toUri();
		return ResponseEntity.created(uri).body(EsgotamentoDTO);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid EsgotamentoSanitarioDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@PutMapping("/{idEsgotamentoSanitario}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idEsgotamentoSanitario){
		service.ativaDesativa('S', idEsgotamentoSanitario);
		return ResponseEntity.ok().build();	
	}
	
	@PutMapping("/{idEsgotamentoSanitario}/desativar")
	public ResponseEntity<?> desativar(@PathVariable Long idEsgotamentoSanitario){
		service.ativaDesativa('N', idEsgotamentoSanitario);
		return ResponseEntity.ok().build();	
	}
	
}
