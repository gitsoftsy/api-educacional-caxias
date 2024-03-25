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

import br.com.softsy.educacional.dto.RacaDTO;
import br.com.softsy.educacional.model.Raca;
import br.com.softsy.educacional.service.RacaService;

@RestController
@RequestMapping("/raca")
public class RacaController {

    @Autowired
    private RacaService racaService;

    @GetMapping
    public ResponseEntity<List<Raca>> listar() {
        return ResponseEntity.ok(racaService.listarTudo());
    }

    @GetMapping("/{idRaca}")
    public ResponseEntity<RacaDTO> buscarPorId(@PathVariable Long idRaca) {
        return ResponseEntity.ok(racaService.buscarPorId(idRaca));
    }

    @PostMapping
    public ResponseEntity<RacaDTO> cadastrar(@RequestBody @Valid RacaDTO dto) {
        RacaDTO racaDTO = racaService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(racaDTO.getIdRaca()).toUri();
        return ResponseEntity.created(uri).body(racaDTO);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid RacaDTO dto) {
        return ResponseEntity.ok(racaService.atualizar(dto));
    }

    @PutMapping("/{idRaca}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idRaca) {
        racaService.ativaDesativa('S', idRaca);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idRaca}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idRaca) {
        racaService.ativaDesativa('N', idRaca);
        return ResponseEntity.ok().build();
    }
}
