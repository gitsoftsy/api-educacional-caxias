package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.FeriadoEscolaDTO;
import br.com.softsy.educacional.service.FeriadoEscolaService;

@RestController
@RequestMapping("/feriadosEscola")
public class FeriadoEscolaController {

    @Autowired
    private FeriadoEscolaService service;

    @GetMapping
    public ResponseEntity<List<FeriadoEscolaDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idFeriadoConta}")
    public ResponseEntity<FeriadoEscolaDTO> buscarPorId(@PathVariable Long idFeriadoConta) {
        return ResponseEntity.ok(service.buscarPorId(idFeriadoConta));
    }

    @GetMapping("/escola/{idEscola}")
    public ResponseEntity<List<FeriadoEscolaDTO>> buscarPorIdEscola(@PathVariable Long idEscola) {
        List<FeriadoEscolaDTO> feriadosEscola = service.buscarPorIdEscola(idEscola);
        return ResponseEntity.ok(feriadosEscola);
    }

    @PostMapping
    public ResponseEntity<FeriadoEscolaDTO> cadastrar(@RequestBody @Validated FeriadoEscolaDTO dto) {
        FeriadoEscolaDTO feriadoEscolaDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(feriadoEscolaDTO.getIdFeriadoEscola()).toUri();
        return ResponseEntity.created(uri).body(feriadoEscolaDTO);
    }

    @PutMapping
    public ResponseEntity<FeriadoEscolaDTO> atualizar(@RequestBody @Validated FeriadoEscolaDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @PutMapping("/{idFeriadoConta}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long idFeriadoConta) {
        service.ativaDesativa('S', idFeriadoConta);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idFeriadoConta}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long idFeriadoConta) {
        service.ativaDesativa('N', idFeriadoConta);
        return ResponseEntity.ok().build();
    }
}