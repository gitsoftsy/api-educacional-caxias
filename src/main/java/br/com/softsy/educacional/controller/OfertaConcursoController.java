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

import br.com.softsy.educacional.dto.CadastroOfertaConcursoDTO;
import br.com.softsy.educacional.dto.OfertaConcursoDTO;
import br.com.softsy.educacional.service.OfertaConcursoService;

@RestController
@RequestMapping("/ofertasConcurso")
public class OfertaConcursoController {

    @Autowired
    private OfertaConcursoService ofertaConcursoService;

    @GetMapping
    public ResponseEntity<List<CadastroOfertaConcursoDTO>> listar() {
        List<CadastroOfertaConcursoDTO> ofertas = ofertaConcursoService.listarTudo();
        return ResponseEntity.ok(ofertas);
    }
    
    @GetMapping("/{idOfertaConcurso}")
    public ResponseEntity<CadastroOfertaConcursoDTO> buscarPorId(@PathVariable Long idOfertaConcurso) {
        CadastroOfertaConcursoDTO ofertaDto = ofertaConcursoService.buscarPorId(idOfertaConcurso);
        return ResponseEntity.ok(ofertaDto);
    }

    @GetMapping("/concurso/{idConcurso}")
    public ResponseEntity<List<OfertaConcursoDTO>> buscarPorIdConta(@PathVariable Long idConcurso) {
        List<OfertaConcursoDTO> ofertas = ofertaConcursoService.buscarPorIdConcurso(idConcurso);
        return ResponseEntity.ok(ofertas);
    }

    @PostMapping
    public ResponseEntity<CadastroOfertaConcursoDTO> cadastrar(@RequestBody @Valid CadastroOfertaConcursoDTO dto) {
        CadastroOfertaConcursoDTO ofertaDTO = ofertaConcursoService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(ofertaDTO.getIdOfertaConcurso()).toUri();
        return ResponseEntity.created(uri).body(ofertaDTO);
    }

    @PutMapping
    public ResponseEntity<CadastroOfertaConcursoDTO> atualizar(@RequestBody @Valid CadastroOfertaConcursoDTO dto) {
        CadastroOfertaConcursoDTO ofertaDTO = ofertaConcursoService.atualizar(dto);
        return ResponseEntity.ok(ofertaDTO);
    }
    
    @PutMapping("/{idOfertaConcurso}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idOfertaConcurso) {
        ofertaConcursoService.ativaDesativa('S', idOfertaConcurso);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idOfertaConcurso}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idOfertaConcurso) {
        ofertaConcursoService.ativaDesativa('N', idOfertaConcurso);
        return ResponseEntity.ok().build();
    }
}