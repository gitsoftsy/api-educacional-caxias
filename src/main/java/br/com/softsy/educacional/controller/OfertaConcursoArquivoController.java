package br.com.softsy.educacional.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.CadastroOfertaConcursoArquivoDTO;
import br.com.softsy.educacional.dto.OfertaConcursoArquivoDTO;
import br.com.softsy.educacional.service.OfertaConcursoArquivoService;

@RestController
@RequestMapping("/ofertaConcursoArq")
public class OfertaConcursoArquivoController {

	@Autowired
	private OfertaConcursoArquivoService ofertaConcursoArqService;

	@PostMapping("/arquivo")
	public ResponseEntity<CadastroOfertaConcursoArquivoDTO> cadastrar(
			@RequestBody @Valid CadastroOfertaConcursoArquivoDTO dto) throws IOException {
		CadastroOfertaConcursoArquivoDTO cadastroOfertaConcursoArqDTO = ofertaConcursoArqService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cadastroOfertaConcursoArqDTO.getIdOfertaConcursoArq()).toUri();
		return ResponseEntity.created(uri).body(cadastroOfertaConcursoArqDTO);
	}

	@GetMapping("/oferta/{idOfertaConcurso}/arquivos")
	public ResponseEntity<Object> listarArquivosOfertaConcurso(@PathVariable Long idOfertaConcurso,
			@RequestHeader("idConta") Long idConta) {
		try {
			List<OfertaConcursoArquivoDTO> arquivos = ofertaConcursoArqService
					.listarImagensOfertaConcursoArq(idOfertaConcurso, idConta);
			return new ResponseEntity<>(arquivos, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
		}
	}

	@GetMapping("/oferta/arquivo/{idOfertaConcursoArq}")
	public ResponseEntity<Object> obterArquivoOfertaConcurso(@PathVariable Long idOfertaConcursoArq,
			@RequestHeader("idConta") Long idConta) {
		try {
			OfertaConcursoArquivoDTO arquivo = ofertaConcursoArqService.obterImagemOfertaConcursoArq(idOfertaConcursoArq,
					idConta);
			return ResponseEntity.ok(arquivo);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
		}
	}
	
	@DeleteMapping("/oferta/arquivo/{idOfertaConcursoArq}")
	public ResponseEntity<Void> removerImagemOfertaConcursoArq(
	        @PathVariable Long idOfertaConcursoArq,
	        @RequestHeader("idConta") Long idConta
	) {
	    try {
	        ofertaConcursoArqService.removerImagemOfertaConcursoArq(idOfertaConcursoArq, idConta);
	        return ResponseEntity.noContent().build();
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(null);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

}
