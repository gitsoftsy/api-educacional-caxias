package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroTurmaAreaConhecimentoDTO;
import br.com.softsy.educacional.dto.DependenciaAdministrativaDTO;
import br.com.softsy.educacional.dto.TurmaAreaConhecimentoDTO;
import br.com.softsy.educacional.model.AreaConhecimento;
import br.com.softsy.educacional.model.Turma;
import br.com.softsy.educacional.model.TurmaAreaConhecimento;
import br.com.softsy.educacional.repository.AreaConhecimentoRepository;
import br.com.softsy.educacional.repository.TurmaAreaConhecimentoRepository;
import br.com.softsy.educacional.repository.TurmaRepository;

@Service
public class TurmaAreaConhecimentoService {

    @Autowired
    private TurmaAreaConhecimentoRepository repository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private AreaConhecimentoRepository areaConhecimentoRepository;

    @Transactional(readOnly = true)
    public List<TurmaAreaConhecimentoDTO> listarTudo() {
        List<TurmaAreaConhecimento> turmasAreaConhecimento = repository.findAll();
        return turmasAreaConhecimento.stream()
                .map(TurmaAreaConhecimentoDTO::new)
                .collect(Collectors.toList());
    }

	@Transactional(readOnly = true)
	public TurmaAreaConhecimentoDTO buscarPorId(Long id){
		return new TurmaAreaConhecimentoDTO(repository.getReferenceById(id));
	}

    @Transactional
    public TurmaAreaConhecimentoDTO salvar(CadastroTurmaAreaConhecimentoDTO dto) {
        TurmaAreaConhecimento turmaAreaConhecimento = criarTurmaAreaConhecimentoAPartirDTO(dto);
        turmaAreaConhecimento = repository.save(turmaAreaConhecimento);
        return new TurmaAreaConhecimentoDTO(turmaAreaConhecimento);
    }

    @Transactional
    public TurmaAreaConhecimentoDTO atualizar(CadastroTurmaAreaConhecimentoDTO dto) {
        TurmaAreaConhecimento turmaAreaConhecimento = repository.getReferenceById(dto.getIdTurmaAreaConhecimento());
        atualizaDados(turmaAreaConhecimento, dto);
        return new TurmaAreaConhecimentoDTO(turmaAreaConhecimento);
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private TurmaAreaConhecimento criarTurmaAreaConhecimentoAPartirDTO(CadastroTurmaAreaConhecimentoDTO dto) {
        TurmaAreaConhecimento turmaAreaConhecimento = new TurmaAreaConhecimento();
        Turma turma = turmaRepository.findById(dto.getTurmaId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
        AreaConhecimento areaConhecimento = areaConhecimentoRepository.findById(dto.getAreaConhecimentoId())
                .orElseThrow(() -> new IllegalArgumentException("Área de conhecimento não encontrada"));
        turmaAreaConhecimento.setTurma(turma);
        turmaAreaConhecimento.setAreaConhecimento(areaConhecimento);
        turmaAreaConhecimento.setDataCadastro(LocalDateTime.now());
        return turmaAreaConhecimento;
    }

    private void atualizaDados(TurmaAreaConhecimento destino, CadastroTurmaAreaConhecimentoDTO origem) {
        destino.setTurma(turmaRepository.findById(origem.getTurmaId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada")));
        destino.setAreaConhecimento(areaConhecimentoRepository.findById(origem.getAreaConhecimentoId())
                .orElseThrow(() -> new IllegalArgumentException("Área de conhecimento não encontrada")));
    }
}