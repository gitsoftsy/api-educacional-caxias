package br.com.softsy.educacional.controller;

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

import br.com.softsy.educacional.dto.EscolaHorarioFuncionamentoDTO;
import br.com.softsy.educacional.service.EscolaHorarioFuncionamentoService;

@RestController
@RequestMapping("/escolaHorarioFuncionamento")
public class EscolaHorarioFuncionamentoController {

    @Autowired
    private EscolaHorarioFuncionamentoService service;

    @GetMapping
    public ResponseEntity<List<EscolaHorarioFuncionamentoDTO>> listar() {
        List<EscolaHorarioFuncionamentoDTO> horariosFuncionamento = service.listarTudo();
        return ResponseEntity.ok(horariosFuncionamento);
    }

    @GetMapping("/escola/{idEscola}")
    public ResponseEntity<List<EscolaHorarioFuncionamentoDTO>> buscarPorIdEscola(@PathVariable Long idEscola) {
        List<EscolaHorarioFuncionamentoDTO> horariosFuncionamento = service.buscarPorIdEscola(idEscola);
        return ResponseEntity.ok(horariosFuncionamento);
    }

    @PostMapping
    public ResponseEntity<EscolaHorarioFuncionamentoDTO> cadastrar(@RequestBody @Valid EscolaHorarioFuncionamentoDTO dto) {
        EscolaHorarioFuncionamentoDTO horarioFuncionamentoDTO = service.salvar(dto);
        return ResponseEntity.ok(horarioFuncionamentoDTO);
    }

    @PutMapping
    public ResponseEntity<EscolaHorarioFuncionamentoDTO> atualizar(@RequestBody @Valid EscolaHorarioFuncionamentoDTO dto) {
        EscolaHorarioFuncionamentoDTO horarioFuncionamentoDTO = service.atualizar(dto);
        return ResponseEntity.ok(horarioFuncionamentoDTO);
    }
    
    @PutMapping("/{idEscolaHorarioFuncionamento}/ativar")
	public ResponseEntity<?> ativar(@PathVariable Long idEscolaHorarioFuncionamento){
		service.ativaDesativa('S', idEscolaHorarioFuncionamento);
		return ResponseEntity.ok().build();	
	}
	
	
	@PutMapping("/{idEscolaHorarioFuncionamento}/desativar")
	public ResponseEntity<?> desatviar(@PathVariable Long idEscolaHorarioFuncionamento){
		service.ativaDesativa('N', idEscolaHorarioFuncionamento);
		return ResponseEntity.ok().build();
	}
 }



