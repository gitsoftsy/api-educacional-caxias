package br.com.softsy.educacional.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.CadastroContaLogoDTO;
import br.com.softsy.educacional.dto.ContaLogoDTO;
import br.com.softsy.educacional.model.ContaLogo;
import br.com.softsy.educacional.service.ContaLogoService;

@RestController
@RequestMapping("/contaLogo")
public class ContaLogoController {

	@Autowired
	ContaLogoService service;

	@GetMapping
	public ResponseEntity<List<ContaLogo>> listar() {
		return ResponseEntity.ok(service.listarTudo());
	}

	@GetMapping("/{idContaLogo}")
	public ResponseEntity<ContaLogoDTO> buscarPorId(@PathVariable Long idContaLogo) {
		return ResponseEntity.ok(service.buscarPorId(idContaLogo));
	}

	@PostMapping
	public ResponseEntity<CadastroContaLogoDTO> cadastrar(@RequestBody @Valid CadastroContaLogoDTO dto)
			throws IOException {
		CadastroContaLogoDTO contaLogoDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(contaLogoDTO.getIdContaLogo()).toUri();
		return ResponseEntity.created(uri).body(contaLogoDTO);
	}

	@DeleteMapping("/{idContaLogo}")
	public ResponseEntity<Void> excluir(@PathVariable Long idContaLogo) {
		try {
			service.excluir(idContaLogo);
			return ResponseEntity.noContent().build();
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ContaLogo não encontrado para exclusão", e);
		}
	}

}
