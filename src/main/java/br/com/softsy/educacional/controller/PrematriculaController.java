package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.CadastroPrematriculaArrayDTO;
import br.com.softsy.educacional.dto.CadastroPrematriculaDTO;
import br.com.softsy.educacional.dto.PrematriculaDTO;
import br.com.softsy.educacional.model.AllResponse;
import br.com.softsy.educacional.service.PrematriculaService;

@RestController
@RequestMapping("/prematricula")
public class PrematriculaController {

    @Autowired
    private PrematriculaService prematriculaService;
    
    @GetMapping
    public ResponseEntity<List<PrematriculaDTO>> listar() {
        List<PrematriculaDTO> prematricula = prematriculaService.listarTudo();
        return ResponseEntity.ok(prematricula);
    }


    @GetMapping("/{idPrematricula}")
    public ResponseEntity<PrematriculaDTO> buscarPorId(@PathVariable Long idPrematricula) {
        PrematriculaDTO prematriculaDTO = prematriculaService.buscarPorId(idPrematricula);
        return ResponseEntity.ok(prematriculaDTO);
    }
    
    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<PrematriculaDTO>> buscarPorIdConta(@PathVariable Long idConta) {
        List<PrematriculaDTO> prematricula = prematriculaService.buscarPorIdConta(idConta);
        return ResponseEntity.ok(prematricula);
    }
    
    @GetMapping("/turma/{idTurma}")
    public ResponseEntity<List<PrematriculaDTO>> buscarPorIdTurma(@PathVariable Long idTurma) {
        List<PrematriculaDTO> prematricula = prematriculaService.buscarPorIdTurma(idTurma);
        return ResponseEntity.ok(prematricula);
    }

    @PostMapping
    public ResponseEntity<List<PrematriculaDTO>> cadastrar(@RequestBody @Valid CadastroPrematriculaArrayDTO dto) {
        List<PrematriculaDTO> prematriculasDTO = prematriculaService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .build().toUri();
        return ResponseEntity.created(uri).body(prematriculasDTO);
    }

    @PutMapping
    public ResponseEntity<PrematriculaDTO> atualizar( @RequestBody @Valid CadastroPrematriculaDTO dto) {
        PrematriculaDTO prematriculaDTO = prematriculaService.atualizar(dto);
        return ResponseEntity.ok(prematriculaDTO);
    }
    

    @PutMapping("/{idPrematricula}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long idPrematricula) {
        prematriculaService.ativaDesativa('S', idPrematricula);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idPrematricula}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long idPrematricula) {
        prematriculaService.ativaDesativa('N', idPrematricula);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{idPrematricula}")
    public ResponseEntity<Void> excluir(@PathVariable Long idPrematricula) {
        prematriculaService.excluir(idPrematricula);
        return ResponseEntity.noContent().build();
    }

}
