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

import br.com.softsy.educacional.dto.CadastroEscolaEquipamentoDTO;
import br.com.softsy.educacional.dto.EscolaEquipamentoDTO;
import br.com.softsy.educacional.service.EscolaEquipamentoService;

@RestController
@RequestMapping("/escolaEquipamento")
public class EscolaEquipamentoController {

    @Autowired
    private EscolaEquipamentoService service;

    @GetMapping
    public ResponseEntity<List<EscolaEquipamentoDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/escola/{idEscola}")
    public ResponseEntity<List<EscolaEquipamentoDTO>> buscarPorIdEscola(@PathVariable Long idEscola) {
        List<EscolaEquipamentoDTO> escolasEquipamento = service.buscarPorIdEscola(idEscola);
        return ResponseEntity.ok(escolasEquipamento);
    }

    @PostMapping
    public ResponseEntity<EscolaEquipamentoDTO> cadastrar(@RequestBody @Valid CadastroEscolaEquipamentoDTO dto) {
        EscolaEquipamentoDTO cadastroEscolaEquipamentoDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cadastroEscolaEquipamentoDTO.getIdEscolaEquipamento()).toUri();
        return ResponseEntity.created(uri).body(cadastroEscolaEquipamentoDTO);
    }

    @PutMapping
    public ResponseEntity<EscolaEquipamentoDTO> atualizar(@RequestBody @Valid CadastroEscolaEquipamentoDTO dto) {
        EscolaEquipamentoDTO escolaEquipamentoDTO = service.atualizar(dto);
        return ResponseEntity.ok(escolaEquipamentoDTO);
    }

    @PutMapping("/{idEscolaEquipamento}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idEscolaEquipamento){
        service.ativarDesativar('S', idEscolaEquipamento);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idEscolaEquipamento}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idEscolaEquipamento){
        service.ativarDesativar('N', idEscolaEquipamento);
        return ResponseEntity.ok().build();
    }
}