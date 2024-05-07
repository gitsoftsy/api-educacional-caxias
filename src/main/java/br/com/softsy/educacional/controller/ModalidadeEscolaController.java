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

import br.com.softsy.educacional.dto.ModalidadeEscolaDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.model.ModalidadeEscola;
import br.com.softsy.educacional.service.ModalidadeEscolaService;

@RestController
@RequestMapping("/modalidadeEscola")
public class ModalidadeEscolaController {
	
	
	@Autowired ModalidadeEscolaService service;
	
	@GetMapping("/conta/{idConta}")
	public ResponseEntity<List<ModalidadeEscolaDTO>> buscarPorIdConta(@PathVariable Long idConta){
		List<ModalidadeEscolaDTO> modalidadeEscola = service.buscarPorIdConta(idConta);
		return ResponseEntity.ok(modalidadeEscola);
	}
	
	@GetMapping("/{idModalidadeEscola}")
	public ResponseEntity<ModalidadeEscolaDTO> buscarPorId(@PathVariable Long idModalidadeEscola){
		return ResponseEntity.ok(service.buscarPorId(idModalidadeEscola));
	}
	
	@PostMapping
	public ResponseEntity<ModalidadeEscolaDTO> cadastrar(@RequestBody @Valid ModalidadeEscolaDTO dto){
		ModalidadeEscolaDTO FormaDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(FormaDTO.getIdModalidadeEscola()).toUri();
		return ResponseEntity.created(uri).body(FormaDTO);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid ModalidadeEscolaDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@PutMapping("/{idModalidadeEscola}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idModalidadeEscola){
		service.ativaDesativa('S', idModalidadeEscola);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idModalidadeEscola}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idModalidadeEscola){
		service.ativaDesativa('N', idModalidadeEscola);
		return ResponseEntity.ok().build();
	}


}
