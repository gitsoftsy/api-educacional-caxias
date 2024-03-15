package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEscolaRegimeEscolarDTO;
import br.com.softsy.educacional.dto.EscolaRegimeEscolarDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaRegimeEscolar;
import br.com.softsy.educacional.model.Periodicidade;
import br.com.softsy.educacional.repository.EscolaRegimeEscolarRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.PeriodicidadeRepository;

@Service
public class EscolaRegimeEscolarService {

    @Autowired
    private EscolaRegimeEscolarRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private PeriodicidadeRepository periodicidadeRepository;

    @Transactional(readOnly = true)
    public List<EscolaRegimeEscolarDTO> listarTudo() {
        List<EscolaRegimeEscolar> escolasRegimeEscolar = repository.findAll();
        return escolasRegimeEscolar.stream()
                .map(EscolaRegimeEscolarDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EscolaRegimeEscolarDTO> buscarPorIdEscola(Long id) {
        List<EscolaRegimeEscolar> escolasRegimeEscolar = repository.findByEscola_IdEscola(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar regime escolar por id de escola"));
        return escolasRegimeEscolar.stream()
                .map(EscolaRegimeEscolarDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public EscolaRegimeEscolarDTO salvar(CadastroEscolaRegimeEscolarDTO dto) {
        EscolaRegimeEscolar escolaRegimeEscolar = criarEscolaRegimeEscolarAPartirDTO(dto);
        escolaRegimeEscolar = repository.save(escolaRegimeEscolar);
        return new EscolaRegimeEscolarDTO(escolaRegimeEscolar);
    }

    @Transactional
    public EscolaRegimeEscolarDTO atualizar(CadastroEscolaRegimeEscolarDTO dto) {
        EscolaRegimeEscolar escolaRegimeEscolar = repository.getReferenceById(dto.getIdEscolaRegimeEscolar());
        atualizaDados(escolaRegimeEscolar, dto);
        return new EscolaRegimeEscolarDTO(escolaRegimeEscolar);
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private EscolaRegimeEscolar criarEscolaRegimeEscolarAPartirDTO(CadastroEscolaRegimeEscolarDTO dto) {
        EscolaRegimeEscolar escolaRegimeEscolar = new EscolaRegimeEscolar();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        Periodicidade periodicidade = periodicidadeRepository.findById(dto.getPeriodicidadeId())
                .orElseThrow(() -> new IllegalArgumentException("Periodicidade não encontrada"));
        escolaRegimeEscolar.setEscola(escola);
        escolaRegimeEscolar.setDescricao(dto.getDescricao());
        escolaRegimeEscolar.setDataHomologacao(dto.getDataHomologacao());
        escolaRegimeEscolar.setDataInicioVigencia(dto.getDataInicioVigencia());
        escolaRegimeEscolar.setDataFimVigencia(dto.getDataFimVigencia());
        escolaRegimeEscolar.setAnoCiclo(dto.getAnoCiclo());
        escolaRegimeEscolar.setPeriodicidade(periodicidade);
        escolaRegimeEscolar.setAnexo(dto.getAnexo());
        return escolaRegimeEscolar;
    }

    private void atualizaDados(EscolaRegimeEscolar destino, CadastroEscolaRegimeEscolarDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        destino.setDescricao(origem.getDescricao());
        destino.setDataHomologacao(origem.getDataHomologacao());
        destino.setDataInicioVigencia(origem.getDataInicioVigencia());
        destino.setDataFimVigencia(origem.getDataFimVigencia());
        destino.setAnoCiclo(origem.getAnoCiclo());
        destino.setAnexo(origem.getAnexo());
        destino.setPeriodicidade(periodicidadeRepository.findById(origem.getPeriodicidadeId())
                .orElseThrow(() -> new IllegalArgumentException("Periodicidade não encontrada")));
    }
}
