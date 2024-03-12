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

import br.com.softsy.educacional.dto.FornecimentoAguaDTO;
import br.com.softsy.educacional.dto.LocalizacaoDTO;
import br.com.softsy.educacional.model.FornecimentoAgua;
import br.com.softsy.educacional.service.FornecimentoAguaService;

@RestController
@RequestMapping("/fornecimentoAgua")
public class FornecimentoAguaController {
	
	@Autowired FornecimentoAguaService service;
	
	@GetMapping
	public ResponseEntity<List<FornecimentoAgua>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}
	
	@GetMapping("/{idFornecimentoAgua}")
	public ResponseEntity<FornecimentoAguaDTO> buscarPorId(@PathVariable Long idFornecimentoAgua){
		return ResponseEntity.ok(service.buscarPorId(idFornecimentoAgua));
	}
	
	@PostMapping
	public ResponseEntity<FornecimentoAguaDTO> cadastrar(@RequestBody @Valid FornecimentoAguaDTO dto){
		FornecimentoAguaDTO FornecimentoDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(FornecimentoDTO.getIdFornecimentoAgua()).toUri();
		return ResponseEntity.created(uri).body(FornecimentoDTO);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid FornecimentoAguaDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@PutMapping("/{idFornecimentoAgua}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idFornecimentoAgua){
		service.ativaDesativa('S', idFornecimentoAgua);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idFornecimentoAgua}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idFornecimentoAgua){
		service.ativaDesativa('N', idFornecimentoAgua);
		return ResponseEntity.ok().build();
	}

}
