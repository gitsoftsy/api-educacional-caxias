package br.com.softsy.educacional.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.EscolaEsgotoDTO;
import br.com.softsy.educacional.service.EscolaEsgotoService;

@RestController
@RequestMapping("/escolaEsgoto")
public class EscolaEsgotoController {

    @Autowired
    private EscolaEsgotoService escolaEsgotoService;

    @PostMapping
    public ResponseEntity<EscolaEsgotoDTO> cadastrar(@RequestBody @Valid EscolaEsgotoDTO dto) {
        EscolaEsgotoDTO escolaEsgotoDTO = escolaEsgotoService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(escolaEsgotoDTO.getIdEscolaEsgoto()).toUri();
        return ResponseEntity.created(uri).body(escolaEsgotoDTO);
    }

    @PutMapping
    public ResponseEntity<EscolaEsgotoDTO> atualizar(@RequestBody @Valid EscolaEsgotoDTO dto) {
        EscolaEsgotoDTO escolaEsgotoDTO = escolaEsgotoService.atualizar(dto);
        return ResponseEntity.ok(escolaEsgotoDTO);
    }

    @DeleteMapping("/{idEscolaEsgoto}")
    public ResponseEntity<?> excluir(@PathVariable Long idEscolaEsgoto) {
        escolaEsgotoService.remover(idEscolaEsgoto);
        return ResponseEntity.ok().build();
    }
}