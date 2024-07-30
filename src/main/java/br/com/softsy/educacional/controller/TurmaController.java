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

import br.com.softsy.educacional.dto.CadastroTurmaDTO;
import br.com.softsy.educacional.dto.TurmaDTO;
import br.com.softsy.educacional.service.TurmaService;

@RestController
@RequestMapping("/turma")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @GetMapping
    public ResponseEntity<List<TurmaDTO>> listar() {
        List<TurmaDTO> turmas = turmaService.listarTudo();
        return ResponseEntity.ok(turmas);
    }

    @PostMapping
    public ResponseEntity<TurmaDTO> cadastrar(@RequestBody @Valid CadastroTurmaDTO dto) {
        TurmaDTO cadastroDTO = turmaService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cadastroDTO.getIdTurma()).toUri();
        return ResponseEntity.created(uri).body(cadastroDTO);
    }


    @GetMapping("/{idTurma}")
    public ResponseEntity<TurmaDTO> buscarPorId(@PathVariable Long idTurma) {
        TurmaDTO turma = turmaService.buscarPorId(idTurma);
        return ResponseEntity.ok(turma);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid CadastroTurmaDTO dto) {
        return ResponseEntity.ok(turmaService.atualizar(dto));
    }
    
    @DeleteMapping("/{idTurma}")
    public ResponseEntity<Void> excluir(@PathVariable Long idTurma) {
        turmaService.remover(idTurma);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{idTurma}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idTurma) {
    	turmaService.ativaDesativa('S', idTurma);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idTurma}/desativar")
    public ResponseEntity<?> desatviar(@PathVariable Long idTurma) {
    	turmaService.ativaDesativa('N', idTurma);
        return ResponseEntity.ok().build();
    }

}