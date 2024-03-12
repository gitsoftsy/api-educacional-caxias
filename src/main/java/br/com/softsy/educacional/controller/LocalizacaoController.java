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
import br.com.softsy.educacional.dto.LocalizacaoDTO;
import br.com.softsy.educacional.model.DestinacaoLixo;
import br.com.softsy.educacional.model.Localizacao;
import br.com.softsy.educacional.service.LocalizacaoService;

@RestController
@RequestMapping("/localizacao")
public class LocalizacaoController {
	
	@Autowired LocalizacaoService service;
	
	@GetMapping
	public ResponseEntity<List<Localizacao>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}
	
	@GetMapping("/{idLocalizacao}")
	public ResponseEntity<LocalizacaoDTO> buscarPorId(@PathVariable Long idLocalizacao){
		return ResponseEntity.ok(service.buscarPorId(idLocalizacao));
	}

	@PostMapping
	public ResponseEntity<LocalizacaoDTO> cadastrar(@RequestBody @Valid LocalizacaoDTO dto){
		LocalizacaoDTO LocalizacaoDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(LocalizacaoDTO.getIdLocalizacao()).toUri();
		return ResponseEntity.created(uri).body(LocalizacaoDTO);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid LocalizacaoDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@PutMapping("/{idLocalizacao}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idLocalizacao){
		service.ativaDesativa('S', idLocalizacao);
		return ResponseEntity.ok().build();	
	}
	
	@PutMapping("/{idLocalizacao}/desativar")
	public ResponseEntity<?> desativar(@PathVariable Long idLocalizacao){
		service.ativaDesativa('N', idLocalizacao);
		return ResponseEntity.ok().build();	
	}
}
