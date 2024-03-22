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

import br.com.softsy.educacional.dto.NacionalidadeDTO;
import br.com.softsy.educacional.model.Nacionalidade;
import br.com.softsy.educacional.service.NacionalidadeService;

@RestController
@RequestMapping("/nacionalidade")
public class NacionalidadeController {

	@Autowired
	NacionalidadeService service;

	@GetMapping
	public ResponseEntity<List<Nacionalidade>> listar() {
		return ResponseEntity.ok(service.listarTudo());
	}

	@GetMapping("/{idNacionalidade}")
	public ResponseEntity<NacionalidadeDTO> buscarPorId(@PathVariable Long idNacionalidade) {
		return ResponseEntity.ok(service.buscarPorId(idNacionalidade));
	}

	@PostMapping
	public ResponseEntity<NacionalidadeDTO> cadastrar(@RequestBody @Valid NacionalidadeDTO dto) {
		NacionalidadeDTO NacionalidadeDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(NacionalidadeDTO.getIdNacionalidade()).toUri();
		return ResponseEntity.created(uri).body(NacionalidadeDTO);
	}

	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid NacionalidadeDTO dto) {
		return ResponseEntity.ok(service.atualizar(dto));
	}

	@PutMapping("/{idNacionalidade}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idNacionalidade) {
		service.ativaDesativa('S', idNacionalidade);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{idNacionalidade}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idNacionalidade) {
		service.ativaDesativa('N', idNacionalidade);
		return ResponseEntity.ok().build();
	}

}
