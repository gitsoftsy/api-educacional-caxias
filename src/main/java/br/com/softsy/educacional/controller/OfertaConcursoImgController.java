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

import br.com.softsy.educacional.dto.CadastroOfertaConcursoImgDTO;
import br.com.softsy.educacional.dto.OfertaConcursoImgDTO;
import br.com.softsy.educacional.service.OfertaConcursoImgService;

@RestController
@RequestMapping("/ofertaConcursoImg")
public class OfertaConcursoImgController {

	@Autowired
	private OfertaConcursoImgService ofertaConcursoImgService;

	@GetMapping("/oferta/{idOfertaConcurso}/imagens")
	public ResponseEntity<Object> listarImagensOfertaConcurso(@PathVariable Long idOfertaConcurso,
			@RequestHeader("idConta") Long idConta) {
		try {
			List<OfertaConcursoImgDTO> imagens = ofertaConcursoImgService.listarImagensOfertaConcurso(idOfertaConcurso,
					idConta);
			return new ResponseEntity<>(imagens, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
		}
	}
	
	@GetMapping("/imagens/oferta/{idOfertaConcursoImg}")
    public ResponseEntity<Object> obterImagemOfertaConcurso(
            @PathVariable Long idOfertaConcursoImg,
            @RequestHeader("idConta") Long idConta) {
        try {
            // Buscar a imagem da oferta de concurso
            OfertaConcursoImgDTO imagem = ofertaConcursoImgService.obterImagemOfertaConcurso(idOfertaConcursoImg, idConta);
            return ResponseEntity.ok(imagem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

	@PostMapping("/imagem")
	public ResponseEntity<CadastroOfertaConcursoImgDTO> cadastrar(@RequestBody @Valid CadastroOfertaConcursoImgDTO dto)
			throws IOException {
		CadastroOfertaConcursoImgDTO cadastroOfertaConcursoImgDTO = ofertaConcursoImgService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cadastroOfertaConcursoImgDTO.getIdOfertaConcursoImg()).toUri();
		return ResponseEntity.created(uri).body(cadastroOfertaConcursoImgDTO);
	}
	
	@DeleteMapping("/oferta/imagem/{idOfertaConcursoImg}")
	public ResponseEntity<Void> removerImagemOfertaConcurso(
	        @PathVariable Long idOfertaConcursoImg,
	        @RequestHeader("idConta") Long idConta // Recebe o idConta pelo header
	) {
	    try {
	        ofertaConcursoImgService.removerImagemOfertaConcurso(idOfertaConcursoImg, idConta);
	        return ResponseEntity.noContent().build();
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(null);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}


}
