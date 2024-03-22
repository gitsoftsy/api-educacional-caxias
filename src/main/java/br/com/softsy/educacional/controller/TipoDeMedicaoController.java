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

import br.com.softsy.educacional.dto.TipoDeMedicaoDTO;
import br.com.softsy.educacional.model.TipoDeMedicao;
import br.com.softsy.educacional.service.TipoDeMedicaoService;

@RestController
@RequestMapping("/tipoMedicao")
public class TipoDeMedicaoController {

    @Autowired
    private TipoDeMedicaoService service;

    @GetMapping
    public ResponseEntity<List<TipoDeMedicao>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoDeMedicaoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<TipoDeMedicaoDTO> cadastrar(@RequestBody @Valid TipoDeMedicaoDTO dto) {
        TipoDeMedicaoDTO tipoMedicaoDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(tipoMedicaoDTO.getIdTipoMedicao()).toUri();
        return ResponseEntity.created(uri).body(tipoMedicaoDTO);
    }

    @PutMapping
    public ResponseEntity<TipoDeMedicaoDTO> atualizar(@RequestBody @Valid TipoDeMedicaoDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
