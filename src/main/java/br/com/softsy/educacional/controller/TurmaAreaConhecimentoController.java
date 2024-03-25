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

import br.com.softsy.educacional.dto.CadastroTurmaAreaConhecimentoDTO;
import br.com.softsy.educacional.dto.TurmaAreaConhecimentoDTO;
import br.com.softsy.educacional.service.TurmaAreaConhecimentoService;

@RestController
@RequestMapping("/turmaAreaConhecimento")
public class TurmaAreaConhecimentoController {

    @Autowired
    private TurmaAreaConhecimentoService service;

    @GetMapping
    public ResponseEntity<List<TurmaAreaConhecimentoDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaAreaConhecimentoDTO> buscarPorId(@PathVariable Long id) {
        TurmaAreaConhecimentoDTO turmaAreaConhecimentoDTO = service.buscarPorId(id);
        return ResponseEntity.ok(turmaAreaConhecimentoDTO);
    }

    @PostMapping
    public ResponseEntity<TurmaAreaConhecimentoDTO> cadastrar(@RequestBody @Valid CadastroTurmaAreaConhecimentoDTO dto) {
        TurmaAreaConhecimentoDTO turmaAreaConhecimentoDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(turmaAreaConhecimentoDTO.getIdTurmaAreaConhecimento()).toUri();
        return ResponseEntity.created(uri).body(turmaAreaConhecimentoDTO);
    }

    @PutMapping
    public ResponseEntity<TurmaAreaConhecimentoDTO> atualizar(@RequestBody @Valid CadastroTurmaAreaConhecimentoDTO dto) {
        TurmaAreaConhecimentoDTO turmaAreaConhecimentoDTO = service.atualizar(dto);
        return ResponseEntity.ok(turmaAreaConhecimentoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.ok().build();
    }
}
