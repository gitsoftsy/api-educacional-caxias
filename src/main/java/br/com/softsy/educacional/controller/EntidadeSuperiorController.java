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

import br.com.softsy.educacional.dto.EntidadeSuperiorDTO;
import br.com.softsy.educacional.model.EntidadeSuperior;
import br.com.softsy.educacional.service.EntidadeSuperiorService;

@RestController
@RequestMapping("/entidadeSuperior")
public class EntidadeSuperiorController {
	
	@Autowired
	private EntidadeSuperiorService service;
	
	@GetMapping("/conta/{idConta}")
		public ResponseEntity<List<EntidadeSuperiorDTO>> buscarPorIdConta(@PathVariable Long idConta){
			List<EntidadeSuperiorDTO> entidadeSuperior = service.buscarPorIdConta(idConta);
			return ResponseEntity.ok(entidadeSuperior);
		}
	
	@GetMapping("/{idEntidadeSuperior}")
	public ResponseEntity<EntidadeSuperiorDTO> buscarPorId(@PathVariable Long idEntidadeSuperior) {
		return ResponseEntity.ok(service.buscarPorId(idEntidadeSuperior));
	}
	
	@PostMapping
	public ResponseEntity<EntidadeSuperiorDTO> cadastrar(@RequestBody @Valid EntidadeSuperiorDTO dto) {
		EntidadeSuperiorDTO entidadeSuperiorDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(entidadeSuperiorDTO.getIdEntidadeSuperior()).toUri();
		return ResponseEntity.created(uri).body(entidadeSuperiorDTO);
	}		
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid EntidadeSuperiorDTO dto) {
		return ResponseEntity.ok(service.atualizar(dto));
	}
	

	@PutMapping("/{idEntidadeSuperior}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idEntidadeSuperior) {
		service.ativaDesativa('S', idEntidadeSuperior);
		return ResponseEntity.ok().build();	
	}
	
	@PutMapping("/{idEntidadeSuperior}/desativar")
	public ResponseEntity<?> desativar(@PathVariable Long idEntidadeSuperior) {
		service.ativaDesativa('N', idEntidadeSuperior);
		return ResponseEntity.ok().build();
	}
}