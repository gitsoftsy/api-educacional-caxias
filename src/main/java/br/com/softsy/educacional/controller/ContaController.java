package br.com.softsy.educacional.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.CadastroContaDTO;
import br.com.softsy.educacional.dto.ContaDTO;
import br.com.softsy.educacional.model.CaminhoImagemRequest;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.service.ContaService;

@RestController
@RequestMapping("/conta")
public class ContaController {
	
	@Autowired ContaService service;
	
	@GetMapping
	public ResponseEntity<List<Conta>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}
	
	@GetMapping("/{idConta}")
	public ResponseEntity<ContaDTO> buscarPorId(@PathVariable Long idConta){
		return ResponseEntity.ok(service.buscarPorId(idConta));
	}
	
	
	@PostMapping
	public ResponseEntity<CadastroContaDTO> cadastrar(@RequestBody @Valid CadastroContaDTO dto) throws IOException{
		CadastroContaDTO contaDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(contaDTO.getIdConta()).toUri();
		return ResponseEntity.created(uri).body(contaDTO);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroContaDTO dto) throws IOException{
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@PutMapping("/{idConta}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idConta){
		service.ativaDesativa('S', idConta);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idConta}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idConta){
		service.ativaDesativa('N', idConta);
		return ResponseEntity.ok().build();
	}

}

