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

import br.com.softsy.educacional.dto.CategoriaEscolaPrivadaDTO;
import br.com.softsy.educacional.model.CategoriaEscolaPrivada;
import br.com.softsy.educacional.service.CategoriaEscolaPrivadaService;

@RestController
@RequestMapping("/categoriaEscolaPrivada")
public class CategoriaEscolaPrivadaController {
	
	@Autowired
	private CategoriaEscolaPrivadaService service;
	
	@GetMapping("/conta/{idConta}")
	public ResponseEntity<List<CategoriaEscolaPrivadaDTO>> buscarPorIdConta(@PathVariable Long idConta){
			List<CategoriaEscolaPrivadaDTO> zoneamento = service.buscarPorIdConta(idConta);
			return ResponseEntity.ok(zoneamento);
		}
	
	@GetMapping("/{idCategoriaEscolaPrivada}")
	public ResponseEntity<CategoriaEscolaPrivadaDTO> buscarPorId(@PathVariable Long idCategoriaEscolaPrivada) {
		return ResponseEntity.ok(service.buscarPorId(idCategoriaEscolaPrivada));
	}
	
	@PostMapping
	public ResponseEntity<CategoriaEscolaPrivadaDTO> cadastrar(@RequestBody @Valid CategoriaEscolaPrivadaDTO dto) {
		CategoriaEscolaPrivadaDTO categoriaDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(categoriaDTO.getIdCategoriaEscolaPrivada()).toUri();
		return ResponseEntity.created(uri).body(categoriaDTO);
	}		
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid CategoriaEscolaPrivadaDTO dto) {
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@PutMapping("/{idCategoriaEscolaPrivada}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idCategoriaEscolaPrivada) {
		service.ativaDesativa('S', idCategoriaEscolaPrivada);
		return ResponseEntity.ok().build();	
	}
	
	@PutMapping("/{idCategoriaEscolaPrivada}/desativar")
	public ResponseEntity<?> desativar(@PathVariable Long idCategoriaEscolaPrivada) {
		service.ativaDesativa('N', idCategoriaEscolaPrivada);
		return ResponseEntity.ok().build();
	}
}
