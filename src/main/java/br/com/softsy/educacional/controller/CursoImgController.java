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

import br.com.softsy.educacional.dto.CadastroCursoImgDTO;
import br.com.softsy.educacional.dto.CursoImgDTO;
import br.com.softsy.educacional.service.CursoImgService;

@RestController
@RequestMapping("/cursoImg")
public class CursoImgController {

    @Autowired
    private CursoImgService cursoImgService;
    
    @GetMapping("/cursos/{idCurso}/imagens")
    public ResponseEntity<Object> listarImagensCurso(
            @PathVariable Long idCurso,
            @RequestHeader("idConta") Long idConta
    ) {
        try {
            List<CursoImgDTO> imagens = cursoImgService.listarImagensCurso(idCurso, idConta);
            return new ResponseEntity<>(imagens, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("message", String.format("O curso com ID %d não possui imagens associadas à conta com ID %d", idCurso, idConta))
            );
        }
    }
 
    @GetMapping("/cursos/imagens/{idCursoImg}")
    public ResponseEntity<Object> obterImagemCurso(
            @PathVariable Long idCursoImg,       
            @RequestHeader("idConta") Long idConta  
    ) {
        try {
            CursoImgDTO cursoImgDTO = cursoImgService.obterImagemCurso(idCursoImg, idConta);
            return new ResponseEntity<>(cursoImgDTO, HttpStatus.OK);
          
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("message", String.format("O curso com ID %d não possui imagens associadas à conta com ID %d", idCursoImg, idConta))
            );
        }
    }

    @PostMapping("/imagem")
    public ResponseEntity<CadastroCursoImgDTO> cadastrar(@RequestBody @Valid CadastroCursoImgDTO dto) throws IOException {
    	CadastroCursoImgDTO cadastroCursoImgDTO = cursoImgService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cadastroCursoImgDTO.getIdCursoImg()).toUri();
        return ResponseEntity.created(uri).body(cadastroCursoImgDTO);
    }
    
    @DeleteMapping("/cursos/imagem/{idCursoImg}")
    public ResponseEntity<Void> removerImagemDescricao(
            @PathVariable Long idCursoImg, 
            @RequestHeader("idConta") Long idConta // Recebe o idConta pelo header
    ) {
        try {
            cursoImgService.removerImagemDescricao(idCursoImg, idConta);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

