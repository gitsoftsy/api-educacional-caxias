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

import br.com.softsy.educacional.dto.CadastroMatriculaDTO;
import br.com.softsy.educacional.dto.MatriculaDTO;
import br.com.softsy.educacional.dto.PrematriculaDTO;
import br.com.softsy.educacional.service.MatriculaService;

@RestController
@RequestMapping("/matricula")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @GetMapping
    public ResponseEntity<List<MatriculaDTO>> listar() {
        List<MatriculaDTO> matricula = matriculaService.listarTudo();
        return ResponseEntity.ok(matricula);
    }

    @GetMapping("/{idMatricula}")
    public ResponseEntity<MatriculaDTO> buscarPorId(@PathVariable Long idMatricula) {
        MatriculaDTO matriculaDTO = matriculaService.buscarPorId(idMatricula);
        return ResponseEntity.ok(matriculaDTO);
    }
    
    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<MatriculaDTO>> buscarPorIdConta(@PathVariable Long idConta) {
        List<MatriculaDTO> matriculaDTO = matriculaService.buscarPorIdConta(idConta);
        return ResponseEntity.ok(matriculaDTO);
    }

    @PostMapping
    public ResponseEntity<MatriculaDTO> cadastrar(@RequestBody @Valid CadastroMatriculaDTO dto) {
        MatriculaDTO matriculaDTO = matriculaService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idMatricula}")
                .buildAndExpand(matriculaDTO.getIdMatricula()).toUri();
        return ResponseEntity.created(uri).body(matriculaDTO);
    }

    @PutMapping
    public ResponseEntity<MatriculaDTO> atualizar(@RequestBody @Valid CadastroMatriculaDTO dto) {
        MatriculaDTO matriculaDTO = matriculaService.atualizar(dto);
        return ResponseEntity.ok(matriculaDTO);
    }

    @PutMapping("/{idMatricula}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long idMatricula) {
        matriculaService.ativaDesativa('S', idMatricula);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idMatricula}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long idMatricula) {
        matriculaService.ativaDesativa('N', idMatricula);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idMatricula}")
    public ResponseEntity<Void> excluir(@PathVariable Long idMatricula) {
        matriculaService.excluir(idMatricula);
        return ResponseEntity.noContent().build();
    }
}
