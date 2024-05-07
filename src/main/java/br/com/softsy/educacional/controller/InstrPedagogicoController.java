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

import br.com.softsy.educacional.dto.InstrPedagogicoDTO;
import br.com.softsy.educacional.service.InstrPedagogicoService;

@RestController
@RequestMapping("/instrPedagogico")
public class InstrPedagogicoController {

    @Autowired
    private InstrPedagogicoService service;

   @GetMapping("/conta/{idConta}")
   	public ResponseEntity<List<InstrPedagogicoDTO>> buscarPorIdConta(@PathVariable Long idConta){
   		List<InstrPedagogicoDTO> instrPedagogico = service.buscarPorIdConta(idConta);
   		return ResponseEntity.ok(instrPedagogico);
   	}

    @GetMapping("/{idInstrPedagogico}")
    public ResponseEntity<InstrPedagogicoDTO> buscarPorId(@PathVariable Long idInstrPedagogico) {
        return ResponseEntity.ok(service.buscarPorId(idInstrPedagogico));
    }

    @PostMapping
    public ResponseEntity<InstrPedagogicoDTO> cadastrar(@RequestBody @Valid InstrPedagogicoDTO dto) {
        InstrPedagogicoDTO instrucaoDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(instrucaoDTO.getIdInstrPedagogico()).toUri();
        return ResponseEntity.created(uri).body(instrucaoDTO);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody @Valid InstrPedagogicoDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @PutMapping("/{idInstrPedagogico}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idInstrPedagogico) {
        service.ativarDesativarInstrucaoPedagogica('S', idInstrPedagogico);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idInstrPedagogico}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idInstrPedagogico) {
        service.ativarDesativarInstrucaoPedagogica('N', idInstrPedagogico);
        return ResponseEntity.ok().build();
    }
}