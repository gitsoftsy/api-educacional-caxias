package br.com.softsy.educacional.controller;

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

import br.com.softsy.educacional.dto.CadastroPessoaNacionalidadeDTO;
import br.com.softsy.educacional.dto.PessoaNacionalidadeDTO;
import br.com.softsy.educacional.service.PessoaNacionalidadeService;

@RestController
@RequestMapping("/pessoasNacionalidade")
public class PessoaNacionalidadeController {

    @Autowired
    private PessoaNacionalidadeService service;

    @GetMapping
    public ResponseEntity<List<PessoaNacionalidadeDTO>> listar() {
        List<PessoaNacionalidadeDTO> pessoasNacionalidade = service.listarTudo();
        return ResponseEntity.ok(pessoasNacionalidade);
    }

//    @GetMapping("/pessoa/{idPessoa}")
//    public ResponseEntity<List<PessoaNacionalidadeDTO>> buscarPorIdPessoa(@PathVariable Long idPessoa) {
//        List<PessoaNacionalidadeDTO> pessoasNacionalidade = service.buscarPorIdPessoa(idPessoa);
//        return ResponseEntity.ok(pessoasNacionalidade);
//    }

    @PostMapping
    public ResponseEntity<PessoaNacionalidadeDTO> cadastrar(@RequestBody @Valid CadastroPessoaNacionalidadeDTO dto) {
        PessoaNacionalidadeDTO pessoaNacionalidadeDTO = service.salvar(dto);
        return ResponseEntity.ok(pessoaNacionalidadeDTO);
    }

    @PutMapping
    public ResponseEntity<PessoaNacionalidadeDTO> atualizar(@RequestBody @Valid CadastroPessoaNacionalidadeDTO dto) {
        PessoaNacionalidadeDTO pessoaNacionalidadeDTO = service.atualizar(dto);
        return ResponseEntity.ok(pessoaNacionalidadeDTO);
    }

    @DeleteMapping("/{idPessoaNacionalidade}")
    public ResponseEntity<?> excluir(@PathVariable Long idPessoaNacionalidade) {
        service.remover(idPessoaNacionalidade);
        return ResponseEntity.ok().build();
    }
}