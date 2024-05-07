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

import br.com.softsy.educacional.dto.TratamentoLixoDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.model.TratamentoLixo;
import br.com.softsy.educacional.service.TratamentoLixoService;


@RestController
@RequestMapping("/tratamentoLixo")
public class TratamentoLixoController {
	
	@Autowired  TratamentoLixoService service;
	
	@GetMapping("/conta/{idConta}")
	public ResponseEntity<List<TratamentoLixoDTO>> buscarPorIdConta(@PathVariable Long idConta){
		List<TratamentoLixoDTO> tratamentoLixo = service.buscarPorIdConta(idConta);
		return ResponseEntity.ok(tratamentoLixo);
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
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid TratamentoLixoDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@PutMapping("/{idTratamentoLixo}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idTratamentoLixo){
		service.ativaDesativa('S', idTratamentoLixo);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idTratamentoLixo}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idTratamentoLixo){
		service.ativaDesativa('N', idTratamentoLixo);
		return ResponseEntity.ok().build();
	}

}
