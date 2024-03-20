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

import br.com.softsy.educacional.dto.CadastroEscolaInstrPedagogicoDTO;
import br.com.softsy.educacional.dto.EscolaInstrPedagogicoDTO;
import br.com.softsy.educacional.service.EscolaInstrPedagogicoService;

@RestController
@RequestMapping("/escolaInstrPedagogico")
public class EscolaInstrPedagogicoController {

    @Autowired
    private EscolaInstrPedagogicoService service;

    @GetMapping
    public ResponseEntity<List<EscolaInstrPedagogicoDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/escola/{idEscola}")
    public ResponseEntity<List<EscolaInstrPedagogicoDTO>> buscarPorIdEscola(@PathVariable Long idEscola) {
        List<EscolaInstrPedagogicoDTO> escolasInstrPedagogico = service.buscarPorIdEscola(idEscola);
        return ResponseEntity.ok(escolasInstrPedagogico);
    }

    @PostMapping
    public ResponseEntity<EscolaInstrPedagogicoDTO> cadastrar(@RequestBody @Valid CadastroEscolaInstrPedagogicoDTO dto) {
        EscolaInstrPedagogicoDTO cadastroEscolaInstrPedagogicoDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cadastroEscolaInstrPedagogicoDTO.getIdEscolaInstrPedagogico()).toUri();
        return ResponseEntity.created(uri).body(cadastroEscolaInstrPedagogicoDTO);
    }

    @PutMapping
    public ResponseEntity<EscolaInstrPedagogicoDTO> atualizar(@RequestBody @Valid CadastroEscolaInstrPedagogicoDTO dto) {
        EscolaInstrPedagogicoDTO escolaInstrPedagogicoDTO = service.atualizar(dto);
        return ResponseEntity.ok(escolaInstrPedagogicoDTO);
    }

	@PutMapping("/{idEscolaInstrPedagogico}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idEscolaInstrPedagogico){
		service.ativaDesativa('S', idEscolaInstrPedagogico);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idEscolaInstrPedagogico}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idEscolaInstrPedagogico){
		service.ativaDesativa('N', idEscolaInstrPedagogico);
		return ResponseEntity.ok().build();
	}

}