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

import br.com.softsy.educacional.dto.CursoSerieDTO;
import br.com.softsy.educacional.service.CursoSerieService;

@RestController
@RequestMapping("/cursoSerie")
public class CursoSerieController {

    @Autowired
    private CursoSerieService cursoSerieService;

    @GetMapping
    public ResponseEntity<List<CursoSerieDTO>> listar() {
        List<CursoSerieDTO> cursos = cursoSerieService.listarTudo();
        return ResponseEntity.ok(cursos);
    }
    
    @GetMapping("/{idCursoSerie}")
    public ResponseEntity<CursoSerieDTO> buscarPorId(@PathVariable Long idCursoSerie) {
    	CursoSerieDTO cursoSerieDTO = cursoSerieService.buscarPorId(idCursoSerie);
        return ResponseEntity.ok(cursoSerieDTO);
    }

    @PostMapping
    public ResponseEntity<CursoSerieDTO> cadastrar(@RequestBody @Valid CursoSerieDTO dto) {
        CursoSerieDTO cursoDTO = cursoSerieService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cursoDTO.getIdCursoSerie()).toUri();
        return ResponseEntity.created(uri).body(cursoDTO);
    }

    @PutMapping
    public ResponseEntity<CursoSerieDTO> atualizar(@RequestBody @Valid CursoSerieDTO dto) {
        CursoSerieDTO cursoDTO = cursoSerieService.atualizar(dto);
        return ResponseEntity.ok(cursoDTO);
    }
    
    @PutMapping("/{idCursoSerie}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idCursoSerie) {
        cursoSerieService.ativaDesativa('S', idCursoSerie);
        return ResponseEntity.ok().build();    
    }
    
    @PutMapping("/{idCursoSerie}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idCursoSerie) {
        cursoSerieService.ativaDesativa('N', idCursoSerie);
        return ResponseEntity.ok().build();
    }
}
