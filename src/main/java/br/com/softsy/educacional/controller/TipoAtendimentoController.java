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

import br.com.softsy.educacional.dto.TipoAtendimentoDTO;
import br.com.softsy.educacional.model.TipoAtendimento;
import br.com.softsy.educacional.service.TipoAtendimentoService;

@RestController
@RequestMapping("/tipoAtendimento")
public class TipoAtendimentoController {

    @Autowired
    private TipoAtendimentoService service;

    @GetMapping
    public ResponseEntity<List<TipoAtendimento>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoAtendimentoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<TipoAtendimentoDTO> cadastrar(@RequestBody @Valid TipoAtendimentoDTO dto) {
        TipoAtendimentoDTO tipoAtendimentoDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(tipoAtendimentoDTO.getIdTipoAtendimento()).toUri();
        return ResponseEntity.created(uri).body(tipoAtendimentoDTO);
    }

    @PutMapping
    public ResponseEntity<TipoAtendimentoDTO> atualizar(@RequestBody @Valid TipoAtendimentoDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.ok().build();
    }
}
