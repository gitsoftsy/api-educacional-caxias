package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.ArrayList;
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

import br.com.softsy.educacional.dto.CurriculoDTO;
import br.com.softsy.educacional.model.AllResponse;
import br.com.softsy.educacional.service.CurriculoService;

@RestController
@RequestMapping("/curriculo")
public class CurriculoController {

    @Autowired
    private CurriculoService curriculoService;
    
    @GetMapping("/ativos/curso/{idCurso}/conta/{idConta}")
    public ResponseEntity<?> listarCurriculosAtivos(@PathVariable String idConta, @PathVariable String idCurso) {
        try {
            Long contaId = Long.parseLong(idConta);
            if (contaId <= 0) {
                return ResponseEntity.badRequest().body(
                    new AllResponse("O idConta não pode ser um número negativo ou zero!", new ArrayList<>())
                );
            }
            Long cursoId = Long.parseLong(idCurso);
            if (cursoId <= 0) {
                return ResponseEntity.badRequest().body(
                    new AllResponse("O idCurso não pode ser um número negativo ou zero!", new ArrayList<>())
                );
            }
            List<CurriculoDTO> curriculos = curriculoService.listarCurriculosAtivosPorContaECurso(contaId, cursoId);
            return ResponseEntity.ok(curriculos);

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(
                new AllResponse("O valor de idConta e idCurso devem ser números válidos.", new ArrayList<>())
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                new AllResponse(e.getMessage(), new ArrayList<>())
            );
        }
    }
    
    
    @GetMapping
    public ResponseEntity<List<CurriculoDTO>> listar() {
        List<CurriculoDTO> curriculos = curriculoService.listarTudo();
        return ResponseEntity.ok(curriculos);
    }
    
    @GetMapping("/{idCurriculo}")
    public ResponseEntity<CurriculoDTO> buscarPorId(@PathVariable Long idCurriculo) {
        CurriculoDTO curriculoDTO = curriculoService.buscarPorId(idCurriculo);
        return ResponseEntity.ok(curriculoDTO);
    }
    
    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<List<CurriculoDTO>> buscarPorIdCurso(@PathVariable Long idCurso) {
        List<CurriculoDTO> curriculoDTO = curriculoService.buscarPorIdCurso(idCurso);
        return ResponseEntity.ok(curriculoDTO);
    }

    @PostMapping
    public ResponseEntity<CurriculoDTO> cadastrar(@RequestBody @Valid CurriculoDTO dto) {
        CurriculoDTO curriculoDTO = curriculoService.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(curriculoDTO.getIdCurriculo()).toUri();
        return ResponseEntity.created(uri).body(curriculoDTO);
    }

    @PutMapping
    public ResponseEntity<CurriculoDTO> atualizar(@RequestBody @Valid CurriculoDTO dto) {
        CurriculoDTO curriculoDTO = curriculoService.atualizar(dto);
        return ResponseEntity.ok(curriculoDTO);
    }
    
    @PutMapping("/{idCurriculo}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idCurriculo) {
        curriculoService.ativaDesativa('S', idCurriculo);
        return ResponseEntity.ok().build();    
    }
    
    @PutMapping("/{idCurriculo}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idCurriculo) {
        curriculoService.ativaDesativa('N', idCurriculo);
        return ResponseEntity.ok().build();
    }
}