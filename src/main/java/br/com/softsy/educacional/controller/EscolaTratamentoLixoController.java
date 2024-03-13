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

import br.com.softsy.educacional.dto.CadastroEscolaTelefoneDTO;
import br.com.softsy.educacional.dto.CadastroEscolaTratamentoLixoDTO;
import br.com.softsy.educacional.dto.EscolaTelefoneDTO;
import br.com.softsy.educacional.dto.EscolaTratamentoLixoDTO;
import br.com.softsy.educacional.service.EscolaTratamentoLixoService;

@RestController
@RequestMapping("/escolaTratamentoLixo")
public class EscolaTratamentoLixoController {

	@Autowired
	private EscolaTratamentoLixoService service;
	
	@GetMapping
	public ResponseEntity<List<EscolaTratamentoLixoDTO>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}
	
	@GetMapping("/escola/{idEscola}")
	public ResponseEntity<List<EscolaTratamentoLixoDTO>> buscarPorIdEscola(@PathVariable Long idEscola){
		List<EscolaTratamentoLixoDTO> produto = service.buscarPorIdEscola(idEscola);
		return ResponseEntity.ok(produto);
	}
	
	@PostMapping
	public ResponseEntity<EscolaTratamentoLixoDTO> cadastrar(@RequestBody @Valid CadastroEscolaTratamentoLixoDTO dto){
		EscolaTratamentoLixoDTO cadastroEscolaTratamentoDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cadastroEscolaTratamentoDTO.getIdEscolaTratamentoLixo()).toUri();
		return ResponseEntity.created(uri).body(cadastroEscolaTratamentoDTO);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroEscolaTratamentoLixoDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@DeleteMapping("/{idEscolaTratamentoLixo}")
	public ResponseEntity<?> excluir(@PathVariable Long idEscolaTratamentoLixo){
		service.remover(idEscolaTratamentoLixo);
		return ResponseEntity.ok().build();
	}
}
