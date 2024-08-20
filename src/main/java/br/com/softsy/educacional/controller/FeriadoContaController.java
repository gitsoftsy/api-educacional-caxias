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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.FeriadoContaDTO;
import br.com.softsy.educacional.service.FeriadoContaService;

@RestController
@RequestMapping("/feriadosConta")
public class FeriadoContaController {

    @Autowired
    private FeriadoContaService service;

    @GetMapping
    public ResponseEntity<List<FeriadoContaDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{idFeriadoConta}")
    public ResponseEntity<FeriadoContaDTO> buscarPorId(@PathVariable Long idFeriadoConta) {
        return ResponseEntity.ok(service.buscarPorId(idFeriadoConta));
    }

    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<FeriadoContaDTO>> buscarPorIdConta(@PathVariable Long idConta) {
        List<FeriadoContaDTO> feriadosConta = service.buscarPorIdConta(idConta);
        return ResponseEntity.ok(feriadosConta);
    }

    @PostMapping
    public ResponseEntity<FeriadoContaDTO> cadastrar(@RequestBody @Valid FeriadoContaDTO dto) {
        FeriadoContaDTO feriadoContaDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(feriadoContaDTO.getIdFeriadoConta()).toUri();
        return ResponseEntity.created(uri).body(feriadoContaDTO);
    }

    @PutMapping
    public ResponseEntity<FeriadoContaDTO> atualizar(@RequestBody @Valid FeriadoContaDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

	@PutMapping("/{idFeriadoConta}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idFeriadoConta){
		service.ativaDesativa('S', idFeriadoConta);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idFeriadoConta}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idFeriadoConta){
		service.ativaDesativa('N', idFeriadoConta);
		return ResponseEntity.ok().build();
	}

}