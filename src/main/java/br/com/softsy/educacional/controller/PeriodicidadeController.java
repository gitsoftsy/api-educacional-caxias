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

import br.com.softsy.educacional.dto.DestinacaoLixoDTO;
import br.com.softsy.educacional.dto.PeriodicidadeDTO;
import br.com.softsy.educacional.model.DestinacaoLixo;
import br.com.softsy.educacional.model.Periodicidade;
import br.com.softsy.educacional.service.PeriodicidadeService;

@RestController
@RequestMapping("/periodicidade")
public class PeriodicidadeController {
	
	@Autowired PeriodicidadeService service;
	
	@GetMapping
	public ResponseEntity<List<Periodicidade>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}
	
	@GetMapping("/{idPeriodicidade}")
	public ResponseEntity<PeriodicidadeDTO> buscarPorId(@PathVariable Long idPeriodicidade){
		return ResponseEntity.ok(service.buscarPorId(idPeriodicidade));
	}
	
	@PostMapping
	public ResponseEntity<PeriodicidadeDTO> cadastrar(@RequestBody @Valid PeriodicidadeDTO dto){
		PeriodicidadeDTO PeriodicidadeDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(PeriodicidadeDTO.getIdPeriodicidade()).toUri();
		return ResponseEntity.created(uri).body(PeriodicidadeDTO);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid PeriodicidadeDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@PutMapping("/{idPeriodicidade}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idPeriodicidade){
		service.ativaDesativa('S', idPeriodicidade);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idPeriodicidade}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idPeriodicidade){
		service.ativaDesativa('N', idPeriodicidade);
		return ResponseEntity.ok().build();
	}

}
