package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.CadastroCursoDescrDTO;
import br.com.softsy.educacional.dto.CursoDescrDTO;
import br.com.softsy.educacional.model.AllResponse;
import br.com.softsy.educacional.service.CursoDescrService;

@RestController
@RequestMapping("/cursoDescr")
public class CursoDescrController {

    @Autowired
    private CursoDescrService cursoDescrService;

    @GetMapping("/cursos/{idCurso}/descricoes")
    public ResponseEntity<?> listarPorCurso(@PathVariable Long idCurso, @RequestHeader("idConta") Long idConta) {
        try {
            List<CursoDescrDTO> cursosDescr = cursoDescrService.listarPorCursoEConta(idCurso, idConta);
            return ResponseEntity.ok(cursosDescr);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
        }
    }

    @GetMapping("/cursos/descricoes/{idCursoDescr}")
    public ResponseEntity<?> buscarPorId(
            @PathVariable Long idCursoDescr,
            @RequestHeader("idConta") Long idConta) {

        try {
            CursoDescrDTO cursoDescrDTO = cursoDescrService.buscarPorId(idCursoDescr, idConta);
            return ResponseEntity.ok(cursoDescrDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
        }
    }

    @PostMapping
    public ResponseEntity<CursoDescrDTO> cadastrar(@RequestBody @Valid CadastroCursoDescrDTO dto) {
        CursoDescrDTO cursoDescrDTO = cursoDescrService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cursoDescrDTO.getIdCursoDescr()).toUri();
        return ResponseEntity.created(uri).body(cursoDescrDTO);
    }

    @PutMapping("/cursos/descricao")
    public ResponseEntity<CursoDescrDTO> atualizar(@RequestBody @Valid CadastroCursoDescrDTO dto) {
        CursoDescrDTO cursoDescrDTO = cursoDescrService.atualizar(dto);
        return ResponseEntity.ok(cursoDescrDTO);
    }
    
    @DeleteMapping("/cursos/descricao/{idCursoDescr}")
    public ResponseEntity<?> excluir(
            @PathVariable Long idCursoDescr,
            @RequestHeader("idConta") Long idConta) {
        
        try {
            cursoDescrService.excluirPorId(idCursoDescr, idConta);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
        }
    }
}