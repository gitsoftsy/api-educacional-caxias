package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.CadastroEscolaDestinacaoLixoDTO;
import br.com.softsy.educacional.dto.EscolaDestinacaoLixoDTO;
import br.com.softsy.educacional.service.EscolaDestinacaoLixoService;

@RestController
@RequestMapping("/escolaDestinacaoLixo")
public class EscolaDestinacaoLixoController {
	
	@Autowired
	private EscolaDestinacaoLixoService service;
	
	@GetMapping
	public ResponseEntity<List<EscolaDestinacaoLixoDTO>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}
	
	@GetMapping("/escola/{idEscola}")
	public ResponseEntity<List<EscolaDestinacaoLixoDTO>> buscarPorIdEscola(@PathVariable Long idEscola){
		List<EscolaDestinacaoLixoDTO> escolasDestinacaoLixo = service.buscarPorIdEscola(idEscola);
		return ResponseEntity.ok(escolasDestinacaoLixo);
	}
	
	@PostMapping
	public ResponseEntity<EscolaDestinacaoLixoDTO> cadastrar(@RequestBody @Valid CadastroEscolaDestinacaoLixoDTO dto){
		EscolaDestinacaoLixoDTO cadastroEscolaDestinacaoLixoDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cadastroEscolaDestinacaoLixoDTO.getIdEscolaDestinacaoLixo()).toUri();
		return ResponseEntity.created(uri).body(cadastroEscolaDestinacaoLixoDTO);
	}
	
	@PutMapping
	public ResponseEntity<EscolaDestinacaoLixoDTO> atualizar(@RequestBody @Valid CadastroEscolaDestinacaoLixoDTO dto){
		EscolaDestinacaoLixoDTO escolaDestinacaoLixoDTO = service.atualizar(dto);
		return ResponseEntity.ok(escolaDestinacaoLixoDTO);
	}
	
	@DeleteMapping("/{idEscolaDestinacaoLixo}")
	public ResponseEntity<?> excluir(@PathVariable Long idEscolaDestinacaoLixo){
		service.remover(idEscolaDestinacaoLixo);
		return ResponseEntity.ok().build();
	}

}
