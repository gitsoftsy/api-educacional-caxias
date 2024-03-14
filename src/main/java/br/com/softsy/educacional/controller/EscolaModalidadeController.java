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

import br.com.softsy.educacional.dto.CadastroEscolaModalidadeDTO;
import br.com.softsy.educacional.dto.CadastroEscolaTratamentoLixoDTO;
import br.com.softsy.educacional.dto.EscolaModalidadeDTO;
import br.com.softsy.educacional.dto.EscolaTratamentoLixoDTO;
import br.com.softsy.educacional.service.EscolaModalidadeService;

@RestController
@RequestMapping("/escolaModalidade")
public class EscolaModalidadeController {
	
	@Autowired
	private EscolaModalidadeService service;
	
	@GetMapping
	public ResponseEntity<List<EscolaModalidadeDTO>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}
	
	@GetMapping("/escola/{idEscola}")
	public ResponseEntity<List<EscolaModalidadeDTO>> buscarPorIdEscola(@PathVariable Long idEscola){
		List<EscolaModalidadeDTO> produto = service.buscarPorIdEscola(idEscola);
		return ResponseEntity.ok(produto);
	}
	
	@PostMapping
	public ResponseEntity<EscolaModalidadeDTO> cadastrar(@RequestBody @Valid CadastroEscolaModalidadeDTO dto){
		EscolaModalidadeDTO cadastroEscolaModalidadeDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cadastroEscolaModalidadeDTO.getIdEscolaModalidade()).toUri();
		return ResponseEntity.created(uri).body(cadastroEscolaModalidadeDTO);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroEscolaModalidadeDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@DeleteMapping("/{idEscolaModalidade}")
	public ResponseEntity<?> excluir(@PathVariable Long idEscolaModalidade){
		service.remover(idEscolaModalidade);
		return ResponseEntity.ok().build();
	}

}
