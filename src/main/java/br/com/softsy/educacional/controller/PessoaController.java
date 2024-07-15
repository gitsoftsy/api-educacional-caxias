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

import br.com.softsy.educacional.dto.CadastroPessoaDTO;
import br.com.softsy.educacional.dto.PessoaDTO;
import br.com.softsy.educacional.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listar() {
        return ResponseEntity.ok(pessoaService.listarTudo());
    }

    @PostMapping
    public ResponseEntity<CadastroPessoaDTO> cadastrar(@RequestBody @Valid CadastroPessoaDTO dto) {
        CadastroPessoaDTO cadastroDTO = pessoaService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cadastroDTO.getIdPessoa()).toUri();
        return ResponseEntity.created(uri).body(cadastroDTO);
    }

    @GetMapping("/{idPessoa}")
    public ResponseEntity<PessoaDTO> buscarPorId(@PathVariable Long idPessoa) {
        return ResponseEntity.ok(pessoaService.buscarPorId(idPessoa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> atualizar(@PathVariable Long id, @RequestBody CadastroPessoaDTO dto) {
        dto.setIdPessoa(id); 
        PessoaDTO pessoaAtualizada = pessoaService.atualizar(dto);
        return ResponseEntity.ok(pessoaAtualizada);
    }

    @PutMapping("/{idPessoa}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idPessoa) {
        pessoaService.ativaDesativa('S', idPessoa);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idPessoa}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idPessoa) {
        pessoaService.ativaDesativa('N', idPessoa);
        return ResponseEntity.ok().build();
    }
}