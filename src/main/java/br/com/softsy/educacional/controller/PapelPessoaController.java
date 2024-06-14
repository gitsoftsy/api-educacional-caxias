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

import br.com.softsy.educacional.dto.PapelPessoaDTO;
import br.com.softsy.educacional.dto.TipoIngressoDTO;
import br.com.softsy.educacional.service.PapelPessoaService;

@RestController
@RequestMapping("/papelPessoa")
public class PapelPessoaController {

    @Autowired
    private PapelPessoaService papelPessoaService;

    @GetMapping
    public ResponseEntity<List<PapelPessoaDTO>> listarPapeisPessoa() {
        List<PapelPessoaDTO> papeisPessoa = papelPessoaService.listarTudo();
        return ResponseEntity.ok(papeisPessoa);
    }

    @GetMapping("/{idPapelPessoa}")
    public ResponseEntity<PapelPessoaDTO> buscarPapelPessoaPorId(@PathVariable Long idPapelPessoa) {
        PapelPessoaDTO papelPessoaDTO = papelPessoaService.buscarPorId(idPapelPessoa);
        return ResponseEntity.ok(papelPessoaDTO);
    }
    
    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<PapelPessoaDTO>> buscarPorIdConta(@PathVariable Long idConta) {
        List<PapelPessoaDTO> papelPessoa = papelPessoaService.buscarPorIdConta(idConta);
        return ResponseEntity.ok(papelPessoa);
    }

    @PostMapping
    public ResponseEntity<PapelPessoaDTO> cadastrarPapelPessoa(@RequestBody @Valid PapelPessoaDTO dto) {
        PapelPessoaDTO papelPessoaDTO = papelPessoaService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idPapelPessoa}")
                .buildAndExpand(papelPessoaDTO.getIdPapelPessoa()).toUri();
        return ResponseEntity.created(uri).body(papelPessoaDTO);
    }

    @PutMapping
    public ResponseEntity<PapelPessoaDTO> atualizarPapelPessoa(@RequestBody @Valid PapelPessoaDTO dto) {
        PapelPessoaDTO papelPessoaDTO = papelPessoaService.atualizar(dto);
        return ResponseEntity.ok(papelPessoaDTO);
    }

    @DeleteMapping("/{idPapelPessoa}")
    public ResponseEntity<?> excluirPapelPessoa(@PathVariable Long idPapelPessoa) {
        papelPessoaService.excluir(idPapelPessoa);
        return ResponseEntity.ok().build();
    }
}
