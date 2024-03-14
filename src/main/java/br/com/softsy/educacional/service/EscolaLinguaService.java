package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEscolaLinguaDTO;
import br.com.softsy.educacional.dto.EscolaLinguaDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaLingua;
import br.com.softsy.educacional.model.LinguaEnsino;
import br.com.softsy.educacional.repository.EscolaLinguaRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.LinguaEnsinoRepository;

@Service
public class EscolaLinguaService {

    @Autowired
    private EscolaLinguaRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private LinguaEnsinoRepository linguaEnsinoRepository;

    @Transactional(readOnly = true)
    public List<EscolaLinguaDTO> listarTudo() {
        List<EscolaLingua> escolasLingua = repository.findAll();
        return escolasLingua.stream()
                .map(EscolaLinguaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EscolaLinguaDTO> buscarPorIdEscola(Long id) {
        List<EscolaLingua> escolasLingua = repository.findByEscola_IdEscola(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar língua de ensino por id de escola"));
        return escolasLingua.stream()
                .map(EscolaLinguaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public EscolaLinguaDTO salvar(CadastroEscolaLinguaDTO dto) {
        EscolaLingua escolaLingua = criarEscolaLinguaAPartirDTO(dto);
        escolaLingua = repository.save(escolaLingua);
        return new EscolaLinguaDTO(escolaLingua);
    }

    @Transactional
    public EscolaLinguaDTO atualizar(CadastroEscolaLinguaDTO dto) {
        EscolaLingua escolaLingua = repository.getReferenceById(dto.getIdEscolaLingua());
        atualizaDados(escolaLingua, dto);
        return new EscolaLinguaDTO(escolaLingua);
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private EscolaLingua criarEscolaLinguaAPartirDTO(CadastroEscolaLinguaDTO dto) {
        EscolaLingua escolaLingua = new EscolaLingua();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        LinguaEnsino linguaEnsino = linguaEnsinoRepository.findById(dto.getLinguaEnsinoId())
                .orElseThrow(() -> new IllegalArgumentException("Língua de ensino não encontrada"));
        escolaLingua.setEscola(escola);
        escolaLingua.setLinguaEnsino(linguaEnsino);
        return escolaLingua;
    }

    private void atualizaDados(EscolaLingua destino, CadastroEscolaLinguaDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        destino.setLinguaEnsino(linguaEnsinoRepository.findById(origem.getLinguaEnsinoId())
                .orElseThrow(() -> new IllegalArgumentException("Língua de ensino não encontrada")));
    }
}