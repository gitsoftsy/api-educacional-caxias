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

import br.com.softsy.educacional.dto.CadastroEscolaFornecimentoAguaDTO;
import br.com.softsy.educacional.dto.EscolaFornecimentoAguaDTO;
import br.com.softsy.educacional.service.EscolaFornecimentoAguaService;

@RestController
@RequestMapping("/escolaFornecimentoAgua")
public class EscolaFornecimentoAguaController {
	
	@Autowired
	private EscolaFornecimentoAguaService service;
	
	@GetMapping
	public ResponseEntity<List<EscolaFornecimentoAguaDTO>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}
	
	@GetMapping("/escola/{idEscola}")
	public ResponseEntity<List<EscolaFornecimentoAguaDTO>> buscarPorIdEscola(@PathVariable Long idEscola){
		List<EscolaFornecimentoAguaDTO> escolasFornecimentoAgua = service.buscarPorIdEscola(idEscola);
		return ResponseEntity.ok(escolasFornecimentoAgua);
	}
	
	@PostMapping
	public ResponseEntity<EscolaFornecimentoAguaDTO> cadastrar(@RequestBody @Valid CadastroEscolaFornecimentoAguaDTO dto){
		EscolaFornecimentoAguaDTO cadastroEscolaFornecimentoAguaDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cadastroEscolaFornecimentoAguaDTO.getIdEscolaFornecimentoAgua()).toUri();
		return ResponseEntity.created(uri).body(cadastroEscolaFornecimentoAguaDTO);
	}
	
	@PutMapping
	public ResponseEntity<EscolaFornecimentoAguaDTO> atualizar(@RequestBody @Valid CadastroEscolaFornecimentoAguaDTO dto){
		EscolaFornecimentoAguaDTO escolaFornecimentoAguaDTO = service.atualizar(dto);
		return ResponseEntity.ok(escolaFornecimentoAguaDTO);
	}
	
	@DeleteMapping("/{idEscolaFornecimentoAgua}")
	public ResponseEntity<?> excluir(@PathVariable Long idEscolaFornecimentoAgua){
		service.remover(idEscolaFornecimentoAgua);
		return ResponseEntity.ok().build();
	}
}
