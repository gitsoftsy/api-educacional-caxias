package br.com.softsy.educacional.controller;

import java.io.IOException;
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

import br.com.softsy.educacional.dto.CadastroConcursoDTO;
import br.com.softsy.educacional.dto.CandidatoDocumentoIngressoDTO;
import br.com.softsy.educacional.dto.ConcursoDTO;
import br.com.softsy.educacional.dto.ModuloDTO;
import br.com.softsy.educacional.model.CaminhoImagemRequest;
import br.com.softsy.educacional.service.CandidatoDocumentoIngressoService;

@RestController
@RequestMapping("/candidatoDocumentoIngresso")
public class CandidatoDocumentoIngressoController {

    @Autowired
    private CandidatoDocumentoIngressoService service;

    @GetMapping
    public ResponseEntity<List<CandidatoDocumentoIngressoDTO>> listarDocumentos() {
        List<CandidatoDocumentoIngressoDTO> documentos = service.listarTudo();
        return ResponseEntity.ok(documentos);
    }
    
    @GetMapping("/candidato/{idCandidato}")
    public ResponseEntity<List<CandidatoDocumentoIngressoDTO>> buscarPorIdCandidato(@PathVariable Long idCandidato) {
        List<CandidatoDocumentoIngressoDTO> documentos = service.buscarPorIdCandidato(idCandidato);
        return ResponseEntity.ok(documentos);
    }
    
    @PutMapping
    public ResponseEntity<CandidatoDocumentoIngressoDTO> atualizar(@RequestBody @Valid CandidatoDocumentoIngressoDTO dto) {
    	CandidatoDocumentoIngressoDTO documentos = service.atualizar(dto);
        return ResponseEntity.ok(documentos);
    }
    
    @GetMapping("/{idCandidatoDocumentoIngresso}")
    public ResponseEntity<CandidatoDocumentoIngressoDTO> buscarPorId(@PathVariable Long idCandidatoDocumentoIngresso) {
    	CandidatoDocumentoIngressoDTO documentos = service.buscarPorId(idCandidatoDocumentoIngresso);
        return ResponseEntity.ok(documentos);
    }
    
    @GetMapping("/{id}/arquivo")
    public ResponseEntity<String> getLogoById(@PathVariable("id") Long id, @RequestBody CaminhoImagemRequest request) throws IOException {
        String caminho = request.getCaminho();
        String logo = service.getLogoById(id, caminho);
        return ResponseEntity.ok(logo);
    }

    @PostMapping
    public ResponseEntity<CandidatoDocumentoIngressoDTO> cadastrar(@RequestBody @Valid CandidatoDocumentoIngressoDTO dto) {
    	CandidatoDocumentoIngressoDTO moduloDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(moduloDTO.getIdCandidatoDocumentoIngresso()).toUri();
        return ResponseEntity.created(uri).body(moduloDTO);
    }
    
    @PutMapping("/deletar/{id}")
    public ResponseEntity<CandidatoDocumentoIngressoDTO> excluirImagem(@PathVariable Long id) {
        CandidatoDocumentoIngressoDTO documentoAtualizado = service.excluirImagem(id);
        return ResponseEntity.ok(documentoAtualizado);
    }

}