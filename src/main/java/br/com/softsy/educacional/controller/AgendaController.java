package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.AgendaDTO;
import br.com.softsy.educacional.service.AgendaService;

@RestController
@RequestMapping("/agendas")
public class AgendaController {

    @Autowired
    private AgendaService service;

    @GetMapping
    public ResponseEntity<List<AgendaDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idAgenda}")
    public ResponseEntity<AgendaDTO> buscarPorId(@PathVariable Long idAgenda) {
        return ResponseEntity.ok(service.buscarPorId(idAgenda));
    }

    @PostMapping
    public ResponseEntity<AgendaDTO> cadastrar(@RequestBody @Valid AgendaDTO dto) {
        AgendaDTO agendaDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(agendaDTO.getIdAgenda()).toUri();
        return ResponseEntity.created(uri).body(agendaDTO);
    }

    @PutMapping
    public ResponseEntity<AgendaDTO> atualizar(@RequestBody @Valid AgendaDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @PutMapping("/{idAgenda}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idAgenda) {
        service.ativaDesativa('S', idAgenda);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idAgenda}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idAgenda) {
        service.ativaDesativa('N', idAgenda);
        return ResponseEntity.ok().build();
    }
}
