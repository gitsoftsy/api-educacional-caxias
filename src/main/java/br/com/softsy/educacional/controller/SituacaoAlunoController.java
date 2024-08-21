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

import br.com.softsy.educacional.dto.AreaConhecimentoDTO;
import br.com.softsy.educacional.dto.SituacaoAlunoDTO;
import br.com.softsy.educacional.service.SituacaoAlunoService;

@RestController
@RequestMapping("/situacoesAluno")
public class SituacaoAlunoController {

    @Autowired
    private SituacaoAlunoService situacaoAlunoService;

    @GetMapping
    public ResponseEntity<List<SituacaoAlunoDTO>> listar() {
        List<SituacaoAlunoDTO> situacoes = situacaoAlunoService.listarTudo();
        return ResponseEntity.ok(situacoes);
    }

    @GetMapping("/{idSituacaoAluno}")
    public ResponseEntity<SituacaoAlunoDTO> buscarPorId(@PathVariable Long idSituacaoAluno) {
        SituacaoAlunoDTO situacaoAlunoDto = situacaoAlunoService.buscarPorId(idSituacaoAluno);
        return ResponseEntity.ok(situacaoAlunoDto);
    }
    
    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<SituacaoAlunoDTO>> buscarPorIdConta(@PathVariable Long idConta) {
        List<SituacaoAlunoDTO> situacaoAlunoDto = situacaoAlunoService.buscarPorIdConta(idConta);
        return ResponseEntity.ok(situacaoAlunoDto);
    }

    @PostMapping
    public ResponseEntity<SituacaoAlunoDTO> cadastrar(@RequestBody @Valid SituacaoAlunoDTO dto) {
        SituacaoAlunoDTO situacaoAlunoDTO = situacaoAlunoService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(situacaoAlunoDTO.getIdSituacaoAluno()).toUri();
        return ResponseEntity.created(uri).body(situacaoAlunoDTO);
    }

    @PutMapping
    public ResponseEntity<SituacaoAlunoDTO> atualizar(@RequestBody @Valid SituacaoAlunoDTO dto) {
        SituacaoAlunoDTO situacaoAlunoDTO = situacaoAlunoService.atualizar(dto);
        return ResponseEntity.ok(situacaoAlunoDTO);
    }

    @DeleteMapping("/{idSituacaoAluno}")
    public ResponseEntity<Void> excluir(@PathVariable Long idSituacaoAluno) {
    	situacaoAlunoService.excluir(idSituacaoAluno);
        return ResponseEntity.ok().build();
    }
}