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

import br.com.softsy.educacional.dto.EscolaDestinacaoLixoDTO;
import br.com.softsy.educacional.dto.ZoneamentoDTO;
import br.com.softsy.educacional.model.Zoneamento;
import br.com.softsy.educacional.service.ZoneamentoService;

@RestController
@RequestMapping("/zoneamento")
public class ZoneamentoController {
	
	@Autowired
	private ZoneamentoService service;
	
	@GetMapping("/conta/{idConta}")
	public ResponseEntity<List<ZoneamentoDTO>> buscarPorIdConta(@PathVariable Long idConta){
		List<ZoneamentoDTO> zoneamento = service.buscarPorIdConta(idConta);
		return ResponseEntity.ok(zoneamento);
	}
	
	@GetMapping("/{idZoneamento}")
	public ResponseEntity<ZoneamentoDTO> buscarPorId(@PathVariable Long idZoneamento) {
		return ResponseEntity.ok(service.buscarPorId(idZoneamento));
	}
	
	@PostMapping
	public ResponseEntity<ZoneamentoDTO> cadastrar(@RequestBody @Valid ZoneamentoDTO dto) {
		ZoneamentoDTO zoneamentoDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(zoneamentoDTO.getIdZoneamento()).toUri();
		return ResponseEntity.created(uri).body(zoneamentoDTO);
	}		
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid ZoneamentoDTO dto) {
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@PutMapping("/{idZoneamento}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idZoneamento) {
		service.ativaDesativa('S', idZoneamento);
		return ResponseEntity.ok().build();	
	}
	
	@PutMapping("/{idZoneamento}/desativar")
	public ResponseEntity<?> desativar(@PathVariable Long idZoneamento) {
		service.ativaDesativa('N', idZoneamento);
		return ResponseEntity.ok().build();
	}
}