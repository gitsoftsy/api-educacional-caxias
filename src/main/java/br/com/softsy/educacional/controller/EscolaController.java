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

import br.com.softsy.educacional.dto.CadastroEscolaDTO;
import br.com.softsy.educacional.dto.EscolaDTO;
import br.com.softsy.educacional.service.EscolaService;

@RestController
@RequestMapping("/escolas")
public class EscolaController {
	
	@Autowired
	private EscolaService service;
	
	
	@GetMapping
	public ResponseEntity<List<EscolaDTO>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}
	
	@PostMapping
	public ResponseEntity<CadastroEscolaDTO> cadastrar(@RequestBody @Valid CadastroEscolaDTO dto){
		CadastroEscolaDTO cadastroDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cadastroDTO.getIdEscola()).toUri();
		return ResponseEntity.created(uri).body(cadastroDTO);
	}
	
	@GetMapping("/ativos")
	public ResponseEntity<List<EscolaDTO>> listarAtivos(){
		return ResponseEntity.ok(service.listarAtivos());
	}
	
	@GetMapping("/{idEscola}")
	public ResponseEntity<EscolaDTO> buscarPorId(@PathVariable Long idEscola){
		return ResponseEntity.ok(service.buscarPorId(idEscola));
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroEscolaDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@PutMapping("/{idEscola}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idEscola){
		service.ativaDesativa('S', idEscola);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{idEscola}/desativar")
	public ResponseEntity<?> desativar(@PathVariable Long idEscola){
		service.ativaDesativa('N', idEscola);
		return ResponseEntity.ok().build();
	}

}
