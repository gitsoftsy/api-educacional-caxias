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

import br.com.softsy.educacional.dto.UfDTO;
import br.com.softsy.educacional.service.UfService;

@RestController
@RequestMapping("/uf")
public class UfController {

    @Autowired
    private UfService ufService;

    @GetMapping
    public ResponseEntity<List<UfDTO>> listar() {
        List<UfDTO> ufs = ufService.listarTudo();
        return ResponseEntity.ok(ufs);
    }

    @GetMapping("/{idUf}")
    public ResponseEntity<UfDTO> buscarPorId(@PathVariable Long idUf) {
        UfDTO dto = ufService.buscarPorId(idUf);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<UfDTO> cadastrar(@RequestBody @Valid UfDTO dto) {
        UfDTO ufDTO = ufService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(ufDTO.getIdUf()).toUri();
        return ResponseEntity.created(uri).body(ufDTO);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid UfDTO dto) {
        UfDTO ufDTO = ufService.atualizar(dto);
        return ResponseEntity.ok(ufDTO);
    }

    @DeleteMapping("/{idUf}")
    public ResponseEntity<Void> excluir(@PathVariable Long idUf) {
        ufService.excluir(idUf);
        return ResponseEntity.ok().build();
    }
}
