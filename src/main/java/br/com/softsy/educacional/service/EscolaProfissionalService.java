package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEscolaProfissionalDTO;
import br.com.softsy.educacional.dto.EscolaProfissionalDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaProfissional;
import br.com.softsy.educacional.model.TipoProfissional;
import br.com.softsy.educacional.repository.EscolaProfissionalRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.TipoProfissionalRepository;

@Service
public class EscolaProfissionalService {

    @Autowired
    private EscolaProfissionalRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private TipoProfissionalRepository tipoProfissionalRepository;

    @Transactional(readOnly = true)
    public List<EscolaProfissionalDTO> listarTudo() {
        List<EscolaProfissional> escolasProfissionais = repository.findAll();
        return escolasProfissionais.stream()
                .map(EscolaProfissionalDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EscolaProfissionalDTO> buscarPorIdEscola(Long id) {
        List<EscolaProfissional> escolasProfissionais = repository.findByEscola_IdEscola(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar escola profissional por ID da escola"));
        return escolasProfissionais.stream()
                .map(EscolaProfissionalDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public EscolaProfissionalDTO salvar(CadastroEscolaProfissionalDTO dto) {
        EscolaProfissional escolaProfissional = criarEscolaProfissionalAPartirDTO(dto);
        escolaProfissional = repository.save(escolaProfissional);
        return new EscolaProfissionalDTO(escolaProfissional);
    }

    @Transactional
    public EscolaProfissionalDTO atualizar(CadastroEscolaProfissionalDTO dto) {
        EscolaProfissional escolaProfissional = repository.getReferenceById(dto.getIdEscolaProfissional());
        atualizaDados(escolaProfissional, dto);
        return new EscolaProfissionalDTO(escolaProfissional);
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private EscolaProfissional criarEscolaProfissionalAPartirDTO(CadastroEscolaProfissionalDTO dto) {
        EscolaProfissional escolaProfissional = new EscolaProfissional();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        TipoProfissional tipoProfissional = tipoProfissionalRepository.findById(dto.getTipoProfissionalId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de profissional não encontrado"));
        escolaProfissional.setEscola(escola);
        escolaProfissional.setTipoProfissional(tipoProfissional);
        escolaProfissional.setQuantidade(dto.getQuantidade());
        return escolaProfissional;
    }

    private void atualizaDados(EscolaProfissional destino, CadastroEscolaProfissionalDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        destino.setTipoProfissional(tipoProfissionalRepository.findById(origem.getTipoProfissionalId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de profissional não encontrado")));
        destino.setQuantidade(origem.getQuantidade());
    }
}
