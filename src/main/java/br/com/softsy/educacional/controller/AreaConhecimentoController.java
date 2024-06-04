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
import br.com.softsy.educacional.service.AreaConhecimentoService;

@RestController
@RequestMapping("/areaConhecimento")
public class AreaConhecimentoController {

    @Autowired
    private AreaConhecimentoService service;
    
    
    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<AreaConhecimentoDTO>> buscarPorIdConta(@PathVariable Long idConta) {
        List<AreaConhecimentoDTO> areaConhecimento = service.buscarPorIdConta(idConta);
        return ResponseEntity.ok(areaConhecimento);
    }

    @GetMapping
    public ResponseEntity<List<AreaConhecimentoDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idAreaConhecimento}")
    public ResponseEntity<AreaConhecimentoDTO> buscarPorId(@PathVariable Long idAreaConhecimento) {
        return ResponseEntity.ok(service.buscarPorId(idAreaConhecimento));
    }

    @PostMapping
    public ResponseEntity<AreaConhecimentoDTO> cadastrar(@RequestBody @Valid AreaConhecimentoDTO dto) {
        AreaConhecimentoDTO areaConhecimentoDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(areaConhecimentoDTO.getIdAreaConhecimento()).toUri();
        return ResponseEntity.created(uri).body(areaConhecimentoDTO);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid AreaConhecimentoDTO dto) {
            return ResponseEntity.ok(service.atualizar(dto));
    }

    @DeleteMapping("/{idAreaConhecimento}")
    public ResponseEntity<Void> excluir(@PathVariable Long idAreaConhecimento) {
        service.excluir(idAreaConhecimento);
        return ResponseEntity.ok().build();
    }
}