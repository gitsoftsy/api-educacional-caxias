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

import br.com.softsy.educacional.dto.CadastroEscolaPpciDTO;
import br.com.softsy.educacional.dto.EscolaPpciDTO;
import br.com.softsy.educacional.service.EscolaPpciService;

@RestController
@RequestMapping("/escolaPpci")
public class EscolaPpciController {

	@Autowired
	private EscolaPpciService service;

	@GetMapping
	public ResponseEntity<List<EscolaPpciDTO>> listar() {
		return ResponseEntity.ok(service.listarTudo());
	}

	@GetMapping("/{idEscolaPpci}")
	public ResponseEntity<EscolaPpciDTO> buscarPorId(@PathVariable Long idEscolaPpci) {
		return ResponseEntity.ok(service.buscarPorId(idEscolaPpci));
	}

	@PostMapping
	public ResponseEntity<CadastroEscolaPpciDTO> cadastrar(@RequestBody @Valid CadastroEscolaPpciDTO dto) {
		CadastroEscolaPpciDTO cadastroDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cadastroDTO.getEscolaId()).toUri();
		return ResponseEntity.created(uri).body(cadastroDTO);
	}

	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroEscolaPpciDTO dto) {
		return ResponseEntity.ok(service.atualizar(dto));
	}

	@GetMapping("/escola/{idEscola}")
	public ResponseEntity<List<EscolaPpciDTO>> buscarPorIdEscola(@PathVariable Long idEscola) {
		List<EscolaPpciDTO> produto = service.buscarPorIdEscola(idEscola);
		return ResponseEntity.ok(produto);
	}
	
	@DeleteMapping("/{idEscolaPpci}")
	public ResponseEntity<?> excluir(@PathVariable Long idEscolaPpci){
		service.remover(idEscolaPpci);
		return ResponseEntity.ok().build();
	}

}
