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
import br.com.softsy.educacional.dto.TipoAtoRegulatorioDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.model.TipoAtoRegulatorio;
import br.com.softsy.educacional.service.TipoAtoRegulatorioService;

@RestController
@RequestMapping("/tipoAtoRegulatorio")
public class TipoAtoRegulatorioController {
	
	@Autowired TipoAtoRegulatorioService service;
	
	@GetMapping("/conta/{idConta}")
	public ResponseEntity<List<TipoAtoRegulatorioDTO>> buscarPorIdConta(@PathVariable Long idConta){
		List<TipoAtoRegulatorioDTO> tipoAtoRegulatorio = service.buscarPorIdConta(idConta);
		return ResponseEntity.ok(tipoAtoRegulatorio);
	}
	
	@GetMapping("/{idTipoAtoRegulatorio}")
	public ResponseEntity<TipoAtoRegulatorioDTO> buscarPorId(@PathVariable Long idTipoAtoRegulatorio){
		return ResponseEntity.ok(service.buscarPorId(idTipoAtoRegulatorio));
	}
	
	@PostMapping
	public ResponseEntity<TipoAtoRegulatorioDTO> cadastrar(@RequestBody @Valid TipoAtoRegulatorioDTO dto){
		TipoAtoRegulatorioDTO TipoAtoDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(TipoAtoDTO.getIdTipoAtoRegulatorio()).toUri();
		return ResponseEntity.created(uri).body(TipoAtoDTO);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid TipoAtoRegulatorioDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@PutMapping("/{idTipoAtoRegulatorio}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idTipoAtoRegulatorio){
		service.ativaDesativa('S', idTipoAtoRegulatorio);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idTipoAtoRegulatorio}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idTipoAtoRegulatorio){
		service.ativaDesativa('N', idTipoAtoRegulatorio);
		return ResponseEntity.ok().build();
	}

}
