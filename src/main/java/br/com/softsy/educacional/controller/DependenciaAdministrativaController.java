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

import br.com.softsy.educacional.dto.DependenciaAdministrativaDTO;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.service.DependenciaAdministrativaService;

@RestController
@RequestMapping("/dependenciaAdministrativa")
public class DependenciaAdministrativaController {
	
	@Autowired DependenciaAdministrativaService service;
	
	@GetMapping
	public ResponseEntity<List<DependenciaAdministrativa>> listar(){
		return ResponseEntity.ok(service.listarTudo());
	}
	
	@GetMapping("/{idDependenciaAdministrativa}")
	public ResponseEntity<DependenciaAdministrativaDTO> buscarPorId(@PathVariable Long idDependenciaAdministrativa){
		return ResponseEntity.ok(service.buscarPorId(idDependenciaAdministrativa));
	}
	
	@PostMapping
	public ResponseEntity<DependenciaAdministrativaDTO> cadastrar(@RequestBody @Valid DependenciaAdministrativaDTO dto){
		DependenciaAdministrativaDTO DependenciaDTO = service.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(DependenciaDTO.getIdDependenciaAdministrativa()).toUri();
		return ResponseEntity.created(uri).body(DependenciaDTO);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody @Valid DependenciaAdministrativaDTO dto){
		return ResponseEntity.ok(service.atualizar(dto));
	}
	
	@PutMapping("/{idDependenciaAdministrativa}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idDependenciaAdministrativa){
		service.ativaDesativa('S', idDependenciaAdministrativa);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idDependenciaAdministrativa}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idDependenciaAdministrativa){
		service.ativaDesativa('N', idDependenciaAdministrativa);
		return ResponseEntity.ok().build();
	}

}
