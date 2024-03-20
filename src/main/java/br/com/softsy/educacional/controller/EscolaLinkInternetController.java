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

import br.com.softsy.educacional.dto.CadastroEscolaLinkInternetDTO;
import br.com.softsy.educacional.dto.EscolaLinkInternetDTO;
import br.com.softsy.educacional.service.EscolaLinkInternetService;

@RestController
@RequestMapping("/escolaLinkInternet")
public class EscolaLinkInternetController {

    @Autowired
    private EscolaLinkInternetService service;

    @GetMapping
    public ResponseEntity<List<EscolaLinkInternetDTO>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/escola/{idEscola}")
    public ResponseEntity<List<EscolaLinkInternetDTO>> buscarPorIdEscola(@PathVariable Long idEscola) {
        List<EscolaLinkInternetDTO> escolasLinkInternet = service.buscarPorIdEscola(idEscola);
        return ResponseEntity.ok(escolasLinkInternet);
    }

    @PostMapping
    public ResponseEntity<EscolaLinkInternetDTO> cadastrar(@RequestBody @Valid CadastroEscolaLinkInternetDTO dto) {
        EscolaLinkInternetDTO cadastroEscolaLinkInternetDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cadastroEscolaLinkInternetDTO.getIdEscolaLinkInternet()).toUri();
        return ResponseEntity.created(uri).body(cadastroEscolaLinkInternetDTO);
    }

    @PutMapping
    public ResponseEntity<EscolaLinkInternetDTO> atualizar(@RequestBody @Valid CadastroEscolaLinkInternetDTO dto) {
        EscolaLinkInternetDTO escolaLinkInternetDTO = service.atualizar(dto);
        return ResponseEntity.ok(escolaLinkInternetDTO);
    }

    @PutMapping("/{idEscolaLinkInternet}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idEscolaLinkInternet) {
        service.ativarDesativar('S', idEscolaLinkInternet);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idEscolaLinkInternet}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idEscolaLinkInternet) {
        service.ativarDesativar('N', idEscolaLinkInternet);
        return ResponseEntity.ok().build();
    }
}
