package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.TratamentoLixoDTO;
import br.com.softsy.educacional.model.TratamentoLixo;
import br.com.softsy.educacional.service.TratamentoLixoService;


@RestController
@RequestMapping("/tratamentoLixo")
public class TratamentoLixoController {
	
	@Autowired  TratamentoLixoService service;
	
	@GetMapping
	public ResponseEntity<List< TratamentoLixo>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}

	
	@GetMapping("/{idTratamentoLixo}")
	public ResponseEntity< TratamentoLixoDTO> buscarPorId(@PathVariable Long idTratamentoLixo){
		return ResponseEntity.ok(service.buscarPorId(idTratamentoLixo));
	}
	
	@PostMapping
	public ResponseEntity< TratamentoLixoDTO> cadastrar(@RequestBody @Valid  TratamentoLixoDTO dto){
		 TratamentoLixoDTO FormaDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(FormaDTO.getIdTratamentoLixo()).toUri();
		return ResponseEntity.created(uri).body(FormaDTO);
	}		

}
