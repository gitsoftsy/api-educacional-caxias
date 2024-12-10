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

import br.com.softsy.educacional.dto.AlunoDTO;
import br.com.softsy.educacional.dto.CadastroNotaDTO;
import br.com.softsy.educacional.dto.NotaDTO;
import br.com.softsy.educacional.service.NotaService;

@RestController
@RequestMapping("/nota")
public class NotaController {

    @Autowired
    private NotaService service;

    @GetMapping
    public ResponseEntity<List<NotaDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idNota}")
    public ResponseEntity<NotaDTO> buscarPorId(@PathVariable Long idNota) {
        return ResponseEntity.ok(service.buscarPorId(idNota));
    }
    
    @GetMapping("/aluno/{idAluno}")
    public ResponseEntity<List<NotaDTO>> buscarPorIdAluno(@PathVariable Long idAluno) {
        List<NotaDTO> alunos = service.buscarPorIdAluno(idAluno);
        return ResponseEntity.ok(alunos);
    }

    @PostMapping
    public ResponseEntity<NotaDTO> cadastrar(@RequestBody @Valid CadastroNotaDTO dto) {
        NotaDTO notaDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idNota}")
                .buildAndExpand(notaDTO.getIdNota()).toUri();
        return ResponseEntity.created(uri).body(notaDTO);
    }

    @PutMapping
    public ResponseEntity<NotaDTO> atualizar(@RequestBody @Valid CadastroNotaDTO dto) {
        NotaDTO notaDTO = service.atualizar(dto);
        return ResponseEntity.ok(notaDTO);
    }

}