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

import br.com.softsy.educacional.dto.AtoRegulatorioDTO;
import br.com.softsy.educacional.dto.DestinacaoLixoDTO;
import br.com.softsy.educacional.model.AtoRegulatorio;
import br.com.softsy.educacional.model.DestinacaoLixo;
import br.com.softsy.educacional.service.AtoRegulatorioService;

@RestController
@RequestMapping("/atoRegulatorio")
public class AtoRegulatorioController {

	@Autowired AtoRegulatorioService service;
	
	@GetMapping
	public ResponseEntity<List<AtoRegulatorio>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}
	
	@GetMapping("/{idAtoRegulatorio}")
	public ResponseEntity<AtoRegulatorioDTO> buscarPorId(@PathVariable Long idAtoRegulatorio){
		return ResponseEntity.ok(service.buscarPorId(idAtoRegulatorio));
	}
	
	@PostMapping
	public ResponseEntity<AtoRegulatorioDTO> cadastrar(@RequestBody @Valid AtoRegulatorioDTO dto){
		AtoRegulatorioDTO AtoRegulatorioDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(AtoRegulatorioDTO.getIdAtoRegulatorio()).toUri();
		return ResponseEntity.created(uri).body(AtoRegulatorioDTO);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid AtoRegulatorioDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@PutMapping("/{idAtoRegulatorio}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idAtoRegulatorio){
		service.ativaDesativa('S', idAtoRegulatorio);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idAtoRegulatorio}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idAtoRegulatorio){
		service.ativaDesativa('N', idAtoRegulatorio);
		return ResponseEntity.ok().build();
	}
}
