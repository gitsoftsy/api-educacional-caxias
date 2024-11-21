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

import br.com.softsy.educacional.dto.TipoMatriculaDTO;
import br.com.softsy.educacional.model.TipoMatricula;
import br.com.softsy.educacional.service.TipoMatriculaService;

@RestController
@RequestMapping("/tiposMatricula")
public class TipoMatriculaController {

	  @Autowired
	  private TipoMatriculaService tipoMatriculaService;

	  @GetMapping
		public ResponseEntity<List<TipoMatricula>> listar() {
			return ResponseEntity.ok(tipoMatriculaService.listarTudo());
		}
	  
	  
	    @GetMapping("/{idTipoMatricula}")
	    public ResponseEntity<TipoMatriculaDTO> buscarPorId(@PathVariable Long idTipoMatricula) {
	        TipoMatriculaDTO tipoMatriculaDTO = tipoMatriculaService.buscarPorId(idTipoMatricula);
	        return ResponseEntity.ok(tipoMatriculaDTO);
	    }

	    @GetMapping("/conta/{idConta}")
	    public ResponseEntity<List<TipoMatriculaDTO>> buscarPorIdConta(@PathVariable Long idConta) {
	        List<TipoMatriculaDTO> tiposMatricula = tipoMatriculaService.buscarPorIdConta(idConta);
	        return ResponseEntity.ok(tiposMatricula);
	    }

	    @PostMapping
	    public ResponseEntity<TipoMatriculaDTO> cadastrar(@RequestBody @Valid TipoMatriculaDTO dto) {
	        TipoMatriculaDTO tipoMatriculaDTO = tipoMatriculaService.salvar(dto);
	        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	                .buildAndExpand(tipoMatriculaDTO.getIdTipoMatricula()).toUri();
	        return ResponseEntity.created(uri).body(tipoMatriculaDTO);
	    }

	    @PutMapping
	    public ResponseEntity<TipoMatriculaDTO> atualizar(@RequestBody @Valid TipoMatriculaDTO dto) {
	        TipoMatriculaDTO tipoMatriculaDTO = tipoMatriculaService.atualizar(dto);
	        return ResponseEntity.ok(tipoMatriculaDTO);
	    }
}
