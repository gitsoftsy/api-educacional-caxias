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

import br.com.softsy.educacional.dto.SituacaoFuncionamentoDTO;
import br.com.softsy.educacional.model.SituacaoFuncionamento;
import br.com.softsy.educacional.service.SituacaoFuncionamentoService;

@RestController
@RequestMapping("/situacaoFuncionamento")
public class SituacaoFuncionamentoController {

	@Autowired SituacaoFuncionamentoService service;
	
	@GetMapping
	public ResponseEntity<List<SituacaoFuncionamento>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}

	
	@GetMapping("/{idSituacaoFuncionamento}")
	public ResponseEntity<SituacaoFuncionamentoDTO> buscarPorId(@PathVariable Long idSituacaoFuncionamento){
		return ResponseEntity.ok(service.buscarPorId(idSituacaoFuncionamento));
	}
	
	@PostMapping
	public ResponseEntity<SituacaoFuncionamentoDTO> cadastrar(@RequestBody @Valid SituacaoFuncionamentoDTO dto){
		SituacaoFuncionamentoDTO FormaDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(FormaDTO.getIdSituacaoFuncionamento()).toUri();
		return ResponseEntity.created(uri).body(FormaDTO);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid SituacaoFuncionamentoDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@PutMapping("/{idSituacaoFuncionamento}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idSituacaoFuncionamento){
		service.ativaDesativa('S', idSituacaoFuncionamento);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idSituacaoFuncionamento}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idSituacaoFuncionamento){
		service.ativaDesativa('N', idSituacaoFuncionamento);
		return ResponseEntity.ok().build();
	}
}
