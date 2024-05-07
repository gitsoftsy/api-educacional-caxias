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

import br.com.softsy.educacional.dto.CadastroEquipamentoDTO;
import br.com.softsy.educacional.dto.EquipamentoDTO;
import br.com.softsy.educacional.service.EquipamentoService;

@RestController
@RequestMapping("/equipamento")
public class EquipamentoController {

    @Autowired
    private EquipamentoService service;

   @GetMapping("/conta/{idConta}")
   	public ResponseEntity<List<EquipamentoDTO>> buscarPorIdConta(@PathVariable Long idConta){
   		List<EquipamentoDTO> equipamento = service.buscarPorIdConta(idConta);
   		return ResponseEntity.ok(equipamento);
   	}

    @GetMapping("/{idEquipamento}")
    public ResponseEntity<EquipamentoDTO> buscarPorId(@PathVariable Long idEquipamento) {
        EquipamentoDTO equipamentoDTO = service.buscarPorId(idEquipamento);
        return ResponseEntity.ok(equipamentoDTO);
    }

    @PostMapping
    public ResponseEntity<EquipamentoDTO> cadastrar(@RequestBody @Valid CadastroEquipamentoDTO dto) {
        EquipamentoDTO equipamentoDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(equipamentoDTO.getIdEquipamento()).toUri();
        return ResponseEntity.created(uri).body(equipamentoDTO);
    }

    @PutMapping
    public ResponseEntity<EquipamentoDTO> atualizar(@RequestBody @Valid CadastroEquipamentoDTO dto) {
        EquipamentoDTO equipamentoDTO = service.atualizar(dto);
        return ResponseEntity.ok(equipamentoDTO);
    }

    @PutMapping("/{idEquipamento}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idEquipamento) {
        service.ativarDesativarEquipamento('S', idEquipamento);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idEquipamento}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idEquipamento) {
        service.ativarDesativarEquipamento('N', idEquipamento);
        return ResponseEntity.ok().build();
    }
}