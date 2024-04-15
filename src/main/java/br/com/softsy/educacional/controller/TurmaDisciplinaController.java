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

import br.com.softsy.educacional.dto.CadastroTurmaDisciplinaDTO;
import br.com.softsy.educacional.dto.TurmaDisciplinaDTO;
import br.com.softsy.educacional.service.TurmaDisciplinaService;

@RestController
@RequestMapping("/turmaDisciplina")
public class TurmaDisciplinaController {

    @Autowired
    private TurmaDisciplinaService service;

    @GetMapping
    public ResponseEntity<List<TurmaDisciplinaDTO>> listar() {
        List<TurmaDisciplinaDTO> turmaDisciplinas = service.listarTudo();
        return ResponseEntity.ok(turmaDisciplinas);
    }

    @GetMapping("/{idTurmaDisciplina}")
    public ResponseEntity<TurmaDisciplinaDTO> buscarPorId(@PathVariable Long idTurmaDisciplina) {
        TurmaDisciplinaDTO turmaDisciplina = service.buscarPorId(idTurmaDisciplina);
        return ResponseEntity.ok(turmaDisciplina);
    }

    @PostMapping
    public ResponseEntity<TurmaDisciplinaDTO> cadastrar(@RequestBody @Valid CadastroTurmaDisciplinaDTO dto) {
        TurmaDisciplinaDTO turmaDisciplinaDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(turmaDisciplinaDTO.getIdTurmaDisciplina()).toUri();
        return ResponseEntity.created(uri).body(turmaDisciplinaDTO);
    }

    @PutMapping
    public ResponseEntity<TurmaDisciplinaDTO> atualizar(@RequestBody @Valid CadastroTurmaDisciplinaDTO dto) {
        TurmaDisciplinaDTO turmaDisciplinaDTO = service.atualizar(dto);
        return ResponseEntity.ok(turmaDisciplinaDTO);
    }

    @PutMapping("/{idTurmaDisciplina}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idTurmaDisciplina) {
        service.ativaDesativa('S', idTurmaDisciplina);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idTurmaDisciplina}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idTurmaDisciplina) {
        service.ativaDesativa('N', idTurmaDisciplina);
        return ResponseEntity.ok().build();
    }
}
