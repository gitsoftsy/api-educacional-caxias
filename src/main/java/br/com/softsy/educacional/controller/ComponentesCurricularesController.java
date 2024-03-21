package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import br.com.softsy.educacional.dto.ComponentesCurricularesDTO;
import br.com.softsy.educacional.service.ComponentesCurricularesService;

@RestController
@RequestMapping("/componentesCurriculares")
public class ComponentesCurricularesController {

    @Autowired
    private ComponentesCurricularesService service;

    @GetMapping
    public ResponseEntity<List<ComponentesCurricularesDTO>> listar() {
        List<ComponentesCurricularesDTO> componentesCurriculares = service.listarTudo().stream()
                .map(ComponentesCurricularesDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(componentesCurriculares);
    }

    @GetMapping("/{idComponentesCurriculares}")
    public ResponseEntity<ComponentesCurricularesDTO> buscarPorId(@PathVariable Long idComponentesCurriculares) {
        ComponentesCurricularesDTO dto = service.buscarPorId(idComponentesCurriculares);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ComponentesCurricularesDTO> cadastrar(@RequestBody @Valid ComponentesCurricularesDTO dto) {
        ComponentesCurricularesDTO componentesCurricularesDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(componentesCurricularesDTO.getIdComponentesCurriculares()).toUri();
        return ResponseEntity.created(uri).body(componentesCurricularesDTO);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid ComponentesCurricularesDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @DeleteMapping("/{idComponentesCurriculares}")
    public ResponseEntity<Void> excluir(@PathVariable Long idComponentesCurriculares) {
        service.excluir(idComponentesCurriculares);
        return ResponseEntity.ok().build();
    }
}
