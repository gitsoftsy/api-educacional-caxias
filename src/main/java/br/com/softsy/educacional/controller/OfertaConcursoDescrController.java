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

import br.com.softsy.educacional.dto.CadastroOfertaConcursoDescrDTO;
import br.com.softsy.educacional.dto.OfertaConcursoDescrDTO;
import br.com.softsy.educacional.model.AllResponse;
import br.com.softsy.educacional.service.OfertaConcursoDescrService;

@RestController
@RequestMapping("/ofertaConcursoDescr")
public class OfertaConcursoDescrController {

    @Autowired
    private OfertaConcursoDescrService ofertaConcursoDescrService;

    @GetMapping("/ofertas/{idOfertaConcurso}/descricoes")
    public ResponseEntity<?> listarPorOferta(@PathVariable Long idOfertaConcurso, @RequestHeader("idConta") Long idConta) {
        try {
            List<OfertaConcursoDescrDTO> descricoes = ofertaConcursoDescrService.listarPorOfertaConcursoEConta(idOfertaConcurso, idConta);
            return ResponseEntity.ok(descricoes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
        }
    }

    @GetMapping("/ofertas/descricoes/{idOfertaConcursoDescr}")
    public ResponseEntity<?> buscarPorId(
            @PathVariable Long idOfertaConcursoDescr,
            @RequestHeader("idConta") Long idConta) {

        try {
            OfertaConcursoDescrDTO ofertaConcursoDescrDTO = ofertaConcursoDescrService.buscarPorId(idOfertaConcursoDescr, idConta);
            return ResponseEntity.ok(ofertaConcursoDescrDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
        }
    }

    @PostMapping
    public ResponseEntity<OfertaConcursoDescrDTO> cadastrar(@RequestBody @Valid CadastroOfertaConcursoDescrDTO dto) {
        OfertaConcursoDescrDTO ofertaConcursoDescrDTO = ofertaConcursoDescrService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(ofertaConcursoDescrDTO.getIdOfertaConcursoDescr()).toUri();
        return ResponseEntity.created(uri).body(ofertaConcursoDescrDTO);
    }

    @PutMapping("/ofertas/descricao")
    public ResponseEntity<OfertaConcursoDescrDTO> atualizar(@RequestBody @Valid CadastroOfertaConcursoDescrDTO dto) {
        OfertaConcursoDescrDTO ofertaConcursoDescrDTO = ofertaConcursoDescrService.atualizar(dto);
        return ResponseEntity.ok(ofertaConcursoDescrDTO);
    }
    
    @DeleteMapping("/ofertas/descricao/{idOfertaConcursoDescr}")
    public ResponseEntity<?> excluir(
            @PathVariable Long idOfertaConcursoDescr,
            @RequestHeader("idConta") Long idConta) {
        
        try {
            ofertaConcursoDescrService.excluirPorId(idOfertaConcursoDescr, idConta);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new AllResponse(e.getMessage(), new ArrayList<>()));
        }
    }
}
