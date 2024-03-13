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

import br.com.softsy.educacional.dto.CadastroEscolaDTO;
import br.com.softsy.educacional.dto.CadastroEscolaTelefoneDTO;
import br.com.softsy.educacional.dto.EscolaDTO;
import br.com.softsy.educacional.dto.EscolaTelefoneDTO;
import br.com.softsy.educacional.model.EscolaTelefone;
import br.com.softsy.educacional.service.EscolaTelefoneService;

@RestController
@RequestMapping("/escolaTelefone")
public class EscolaTelefoneController {

	@Autowired
	private EscolaTelefoneService service;
	
	@GetMapping
	public ResponseEntity<List<EscolaTelefoneDTO>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}
	
	@GetMapping("/escola/{idEscola}")
	public ResponseEntity<List<EscolaTelefoneDTO>> buscarPorIdEscola(@PathVariable Long idEscola){
		List<EscolaTelefoneDTO> produto = service.buscarPorIdEscola(idEscola);
		return ResponseEntity.ok(produto);
	}
	
	@PostMapping
	public ResponseEntity<CadastroEscolaTelefoneDTO> cadastrar(@RequestBody @Valid CadastroEscolaTelefoneDTO dto){
		CadastroEscolaTelefoneDTO cadastroTelefoneDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cadastroTelefoneDTO.getIdTelefoneEscola()).toUri();
		return ResponseEntity.created(uri).body(cadastroTelefoneDTO);
	}
	
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroEscolaTelefoneDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@DeleteMapping("/{idTelefoneEscola}")
	public ResponseEntity<?> excluir(@PathVariable Long idTelefoneEscola){
		service.remover(idTelefoneEscola);
		return ResponseEntity.ok().build();
	}
}
