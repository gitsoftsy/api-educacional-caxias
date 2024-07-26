package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.LinguaEnsinoDTO;
import br.com.softsy.educacional.dto.PessoaFichaMedicaDTO;
import br.com.softsy.educacional.service.PessoaFichaMedicaService;

@RestController
@RequestMapping("/fichasMedicas")
public class PessoaFichaMedicaController {

    @Autowired
    private PessoaFichaMedicaService pessoaFichaMedicaService;

    @GetMapping
    public ResponseEntity<List<PessoaFichaMedicaDTO>> listar() {
        return ResponseEntity.ok(pessoaFichaMedicaService.listarTudo());
    }


    @PostMapping
    public ResponseEntity<PessoaFichaMedicaDTO> cadastrar(@RequestBody @Valid PessoaFichaMedicaDTO dto) {
        PessoaFichaMedicaDTO fichaMedicaDTO = pessoaFichaMedicaService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(fichaMedicaDTO.getIdPessoaFichaMedica()).toUri();
        return ResponseEntity.created(uri).body(fichaMedicaDTO);
    }
    
    @GetMapping("/pessoa/{idPessoa}")
    public Object listaFichaMedicaResponsavel(
            @PathVariable Long idPessoa
    ) {
        // Verifica se todos os parâmetros são nulos
        if (idPessoa == null ) {
            return "Por favor, informe o parâmetro obrigatório";
        }

        List<Map<String, Object>> result = pessoaFichaMedicaService.listaFichaMedicaResponsavel(idPessoa);

        if (result.isEmpty()) {
            return "Nenhum resultado encontrado para os parâmetros informados.";
        }

        return result;
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<PessoaFichaMedicaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pessoaFichaMedicaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaFichaMedicaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid PessoaFichaMedicaDTO dto) {
        dto.setIdPessoaFichaMedica(id); 
        PessoaFichaMedicaDTO fichaAtualizada = pessoaFichaMedicaService.atualizar(dto);
        return ResponseEntity.ok(fichaAtualizada);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        pessoaFichaMedicaService.excluir(id);
        return ResponseEntity.ok().build();
    }
}