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

import br.com.softsy.educacional.dto.ConcursoDTO;
import br.com.softsy.educacional.dto.SerieDTO;
import br.com.softsy.educacional.service.SerieService;

@RestController
@RequestMapping("/serie")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public ResponseEntity<List<SerieDTO>> listar() {
        List<SerieDTO> series = serieService.listarTudo();
        return ResponseEntity.ok(series);
    }
    
    @GetMapping("/{idSerie}")
    public ResponseEntity<SerieDTO> buscarPorId(@PathVariable Long idSerie) {
        SerieDTO serieDTO = serieService.buscarPorId(idSerie);
        return ResponseEntity.ok(serieDTO);
    }
    
    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<SerieDTO>> buscarPorIdConta(@PathVariable Long idConta) {
        List<SerieDTO> concursos = serieService.buscarPorIdConta(idConta);
        return ResponseEntity.ok(concursos);
    }

    @PostMapping
    public ResponseEntity<SerieDTO> cadastrar(@RequestBody @Valid SerieDTO dto) {
        SerieDTO serieDTO = serieService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(serieDTO.getIdSerie()).toUri();
        return ResponseEntity.created(uri).body(serieDTO);
    }

    @PutMapping
    public ResponseEntity<SerieDTO> atualizar(@RequestBody @Valid SerieDTO dto) {
        SerieDTO serieDTO = serieService.atualizar(dto);
        return ResponseEntity.ok(serieDTO);
    }
    
    @PutMapping("/{idSerie}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idSerie) {
        serieService.ativaDesativa('S', idSerie);
        return ResponseEntity.ok().build();    
    }
    
    @PutMapping("/{idSerie}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idSerie) {
        serieService.ativaDesativa('N', idSerie);
        return ResponseEntity.ok().build();
    }
}