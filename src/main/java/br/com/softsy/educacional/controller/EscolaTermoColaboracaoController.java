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

import br.com.softsy.educacional.dto.CadastroEscolaTermoColaboracaoDTO;
import br.com.softsy.educacional.dto.EscolaTermoColaboracaoDTO;
import br.com.softsy.educacional.service.EscolaTermoColaboracaoService;

@RestController
@RequestMapping("/escolaTColaboracao")
public class EscolaTermoColaboracaoController {
	
	@Autowired
	private EscolaTermoColaboracaoService service;
	
	
	@GetMapping
	public ResponseEntity<List<EscolaTermoColaboracaoDTO>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}
	
	@GetMapping("/{idEscolaTermoColaboracao}")
	public ResponseEntity<EscolaTermoColaboracaoDTO> buscarPorId(@PathVariable Long idEscolaTermoColaboracao){
		return ResponseEntity.ok(service.buscarPorId(idEscolaTermoColaboracao));
	}
	
	@PostMapping
	public ResponseEntity<CadastroEscolaTermoColaboracaoDTO> cadastrar(@RequestBody @Valid CadastroEscolaTermoColaboracaoDTO dto){
		CadastroEscolaTermoColaboracaoDTO cadastroDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cadastroDTO.getEscolaId()).toUri();
		return ResponseEntity.created(uri).body(cadastroDTO);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroEscolaTermoColaboracaoDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@GetMapping("/escola/{idEscola}")
	public ResponseEntity<List<EscolaTermoColaboracaoDTO>> buscarPorIdEscola(@PathVariable Long idEscola){
		List<EscolaTermoColaboracaoDTO> produto = service.buscarPorIdEscola(idEscola);
		return ResponseEntity.ok(produto);
	}
	
}
