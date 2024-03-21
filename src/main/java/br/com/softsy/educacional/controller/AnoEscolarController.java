package br.com.softsy.educacional.controller;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.AnoEscolarDTO;
import br.com.softsy.educacional.model.AnoEscolar;
import br.com.softsy.educacional.service.AnoEscolarService;

@RestController
@RequestMapping("/anoEscolar")
public class AnoEscolarController {

    @Autowired
    private AnoEscolarService service;

    @GetMapping
    public ResponseEntity<List<AnoEscolar>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idAnoEscolar}")
    public ResponseEntity<AnoEscolarDTO> buscarPorId(@PathVariable Long idAnoEscolar) {
        return ResponseEntity.ok(service.buscarPorId(idAnoEscolar));
    }

    @PostMapping
    public ResponseEntity<AnoEscolarDTO> cadastrar(@RequestBody @Valid AnoEscolarDTO dto) {
        AnoEscolarDTO anoEscolarDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(anoEscolarDTO.getIdAnoEscolar()).toUri();
        return ResponseEntity.created(uri).body(anoEscolarDTO);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid AnoEscolarDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @DeleteMapping("/{idAnoEscolar}")
    public ResponseEntity<Void> excluir(@PathVariable Long idAnoEscolar) {
        service.excluir(idAnoEscolar);
        return ResponseEntity.ok().build();
    }
}