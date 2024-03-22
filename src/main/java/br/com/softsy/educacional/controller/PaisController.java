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

import br.com.softsy.educacional.dto.PaisDTO;
import br.com.softsy.educacional.model.Pais;
import br.com.softsy.educacional.service.PaisService;

@RestController
@RequestMapping("/paises")
public class PaisController {

    @Autowired
    private PaisService paisService;

    @GetMapping
    public ResponseEntity<List<Pais>> listarPaises() {
        return ResponseEntity.ok(paisService.listarTudo());
    }

    @GetMapping("/{idPais}")
    public ResponseEntity<PaisDTO> buscarPaisPorId(@PathVariable Long idPais) {
        return ResponseEntity.ok(paisService.buscarPorId(idPais));
    }

    @PostMapping
    public ResponseEntity<PaisDTO> cadastrarPais(@RequestBody @Valid PaisDTO dto) {
        PaisDTO paisDTO = paisService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idPais}")
                .buildAndExpand(paisDTO.getIdPais()).toUri();
        return ResponseEntity.created(uri).body(paisDTO);
    }

    @PutMapping
    public ResponseEntity<PaisDTO> atualizarPais(@RequestBody @Valid PaisDTO dto) {
        return ResponseEntity.ok(paisService.atualizar(dto));
    }

    @DeleteMapping("/{idPais}")
    public ResponseEntity<?> excluirPais(@PathVariable Long idPais) {
        paisService.excluir(idPais);
        return ResponseEntity.ok().build();
    }
}