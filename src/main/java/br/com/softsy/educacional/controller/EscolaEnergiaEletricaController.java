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

import br.com.softsy.educacional.dto.EscolaEnergiaEletricaDTO;
import br.com.softsy.educacional.dto.EscolaInfraestruturaDTO;
import br.com.softsy.educacional.service.EscolaEnergiaEletricaService;

@RestController
@RequestMapping("/escolaEnergiaEletrica")
public class EscolaEnergiaEletricaController {

    @Autowired
    private EscolaEnergiaEletricaService escolaEnergiaEletricaService;

    @PostMapping
    public ResponseEntity<EscolaEnergiaEletricaDTO> cadastrar(@RequestBody @Valid EscolaEnergiaEletricaDTO dto) {
        EscolaEnergiaEletricaDTO escolaEnergiaEletricaDTO = escolaEnergiaEletricaService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(escolaEnergiaEletricaDTO.getIdEscolaEnergiaEletrica()).toUri();
        return ResponseEntity.created(uri).body(escolaEnergiaEletricaDTO);
    }
    
	@GetMapping("/escola/{idEscola}")
	public ResponseEntity<List<EscolaEnergiaEletricaDTO>> buscarPorIdEscola(@PathVariable Long idEscola){
		List<EscolaEnergiaEletricaDTO> escolaEnergiaEletrica = escolaEnergiaEletricaService.buscarPorIdEscola(idEscola);
		return ResponseEntity.ok(escolaEnergiaEletrica);
	}

    @PutMapping
    public ResponseEntity<EscolaEnergiaEletricaDTO> atualizar(@RequestBody @Valid EscolaEnergiaEletricaDTO dto) {
        EscolaEnergiaEletricaDTO escolaEnergiaEletricaDTO = escolaEnergiaEletricaService.atualizar(dto);
        return ResponseEntity.ok(escolaEnergiaEletricaDTO);
    }

    @DeleteMapping("/{idEscolaEnergiaEletrica}")
    public ResponseEntity<?> excluir(@PathVariable Long idEscolaEnergiaEletrica) {
        escolaEnergiaEletricaService.remover(idEscolaEnergiaEletrica);
        return ResponseEntity.ok().build();
    }
}