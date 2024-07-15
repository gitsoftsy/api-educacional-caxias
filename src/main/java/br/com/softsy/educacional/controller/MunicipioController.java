package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.CadastroMunicipioDTO;
import br.com.softsy.educacional.dto.CursoDTO;
import br.com.softsy.educacional.dto.MunicipioDTO;
import br.com.softsy.educacional.service.MunicipioService;

@RestController
@RequestMapping("/municipio")
public class MunicipioController {

	@Autowired
	private MunicipioService service;

	@GetMapping
	public ResponseEntity<List<MunicipioDTO>> listar() {
		return ResponseEntity.ok(service.listarTudo());
	}

	@GetMapping("/{idMunicipio}")
	public ResponseEntity<MunicipioDTO> buscarPorId(@PathVariable Long idMunicipio) {
		return ResponseEntity.ok(service.buscarPorId(idMunicipio));
	}
	
    @GetMapping("/uf/{idUf}")
    public ResponseEntity<List<MunicipioDTO>> buscarPorIdUf(@PathVariable Long idUf) {
        List<MunicipioDTO> municipio = service.buscarPorIdUf(idUf);
        return ResponseEntity.ok(municipio);
    }

	@PostMapping
	public ResponseEntity<CadastroMunicipioDTO> cadastrar(@RequestBody @Valid CadastroMunicipioDTO dto) {
		CadastroMunicipioDTO cadastroDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cadastroDTO.getUfId())
				.toUri();
		return ResponseEntity.created(uri).body(cadastroDTO);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroMunicipioDTO dto) {
		return ResponseEntity.ok(service.atualizar(dto));
	}


	@DeleteMapping("/{idMunicipio}")
	public ResponseEntity<?> excluir(@PathVariable Long idMunicipio) {
		service.remover(idMunicipio);
		return ResponseEntity.ok().build();
	}

}
