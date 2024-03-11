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
import br.com.softsy.educacional.model.DestinacaoLixo;
import br.com.softsy.educacional.service.DestinacaoLixoService;

@RestController
@RequestMapping("/destinacaoLixo")
public class DestinacaoLixoController {

	@Autowired DestinacaoLixoService service;
	
	@GetMapping
	public ResponseEntity<List<DestinacaoLixo>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}
	
	@GetMapping("/{idDestinacaoLixo}")
	public ResponseEntity<DestinacaoLixoDTO> buscarPorId(@PathVariable Long idDestinacaoLixo){
		return ResponseEntity.ok(service.buscarPorId(idDestinacaoLixo));
	}
	
	@PostMapping
	public ResponseEntity<DestinacaoLixoDTO> cadastrar(@RequestBody @Valid DestinacaoLixoDTO dto){
		DestinacaoLixoDTO DestinacaoDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(DestinacaoDTO.getIdDestinacaoLixo()).toUri();
		return ResponseEntity.created(uri).body(DestinacaoDTO);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid DestinacaoLixoDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@PutMapping("/{idDestinacaoLixo}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idDestinacaoLixo){
		service.ativaDesativa('S', idDestinacaoLixo);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idDestinacaoLixo}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idDestinacaoLixo){
		service.ativaDesativa('N', idDestinacaoLixo);
		return ResponseEntity.ok().build();
	}
}
