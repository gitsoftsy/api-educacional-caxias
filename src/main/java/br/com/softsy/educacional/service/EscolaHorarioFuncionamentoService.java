package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.EscolaHorarioFuncionamentoDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaHorarioFuncionamento;
import br.com.softsy.educacional.model.ModalidadeEscola;
import br.com.softsy.educacional.repository.EscolaHorarioFuncionamentoRepository;
import br.com.softsy.educacional.repository.EscolaRepository;

@Service
public class EscolaHorarioFuncionamentoService {

    @Autowired
    private EscolaHorarioFuncionamentoRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Transactional(readOnly = true)
    public List<EscolaHorarioFuncionamentoDTO> listarTudo() {
        List<EscolaHorarioFuncionamento> horariosFuncionamento = repository.findAll();
        return horariosFuncionamento.stream()
                .map(EscolaHorarioFuncionamentoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EscolaHorarioFuncionamentoDTO> buscarPorIdEscola(Long id) {
        List<EscolaHorarioFuncionamento> horariosFuncionamento = repository.findByEscola_IdEscola(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar horário de funcionamento por id de escola"));
        return horariosFuncionamento.stream()
                .map(EscolaHorarioFuncionamentoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public EscolaHorarioFuncionamentoDTO salvar(EscolaHorarioFuncionamentoDTO dto) {
        EscolaHorarioFuncionamento horarioFuncionamento = criarEscolaHorarioFuncionamentoAPartirDTO(dto);
        horarioFuncionamento = repository.save(horarioFuncionamento);
        return new EscolaHorarioFuncionamentoDTO(horarioFuncionamento);
    }

    @Transactional
    public EscolaHorarioFuncionamentoDTO atualizar(EscolaHorarioFuncionamentoDTO dto) {
        EscolaHorarioFuncionamento horarioFuncionamento = repository.getReferenceById(dto.getIdEscolaHorarioFuncionamento());
        atualizaDados(horarioFuncionamento, dto);
        return new EscolaHorarioFuncionamentoDTO(horarioFuncionamento);
    }


    private EscolaHorarioFuncionamento criarEscolaHorarioFuncionamentoAPartirDTO(EscolaHorarioFuncionamentoDTO dto) {
        EscolaHorarioFuncionamento horarioFuncionamento = new EscolaHorarioFuncionamento();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        BeanUtils.copyProperties(dto, horarioFuncionamento, "idEscolaHorarioFuncionamento", "dataCadastro", "escolaId", "ativo");
        horarioFuncionamento.setEscola(escola);
        horarioFuncionamento.setDataCadastro(LocalDateTime.now());
        horarioFuncionamento.setAtivo('S');
        return horarioFuncionamento;
    }
    
	@Transactional
	public void ativaDesativa(char status, Long idEscolaHorarioFuncionamento) {
		EscolaHorarioFuncionamento horarioFuncionamento = repository.getReferenceById(idEscolaHorarioFuncionamento);
		horarioFuncionamento.setAtivo(status);
	}

    private void atualizaDados(EscolaHorarioFuncionamento destino, EscolaHorarioFuncionamentoDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        BeanUtils.copyProperties(origem, destino, "idEscolaHorarioFuncionamento", "dataCadastro", "escolaId", "ativo");
    }
}