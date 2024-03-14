package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEscolaFornecimentoAguaDTO;
import br.com.softsy.educacional.dto.EscolaFornecimentoAguaDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaFornecimentoAgua;
import br.com.softsy.educacional.model.FornecimentoAgua;
import br.com.softsy.educacional.repository.EscolaFornecimentoAguaRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.FornecimentoAguaRepository;

@Service
public class EscolaFornecimentoAguaService {

    @Autowired
    private EscolaFornecimentoAguaRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private FornecimentoAguaRepository fornecimentoAguaRepository;

    @Transactional(readOnly = true)
    public List<EscolaFornecimentoAguaDTO> listarTudo() {
        List<EscolaFornecimentoAgua> escolasFornecimentoAgua = repository.findAll();
        return escolasFornecimentoAgua.stream()
                .map(EscolaFornecimentoAguaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EscolaFornecimentoAguaDTO> buscarPorIdEscola(Long id) {
        List<EscolaFornecimentoAgua> escolasFornecimentoAgua = repository.findByEscola_IdEscola(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar fornecimento de água por id de escola"));
        return escolasFornecimentoAgua.stream()
                .map(EscolaFornecimentoAguaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public EscolaFornecimentoAguaDTO salvar(CadastroEscolaFornecimentoAguaDTO dto) {
        EscolaFornecimentoAgua escolaFornecimentoAgua = criarEscolaFornecimentoAguaAPartirDTO(dto);
        escolaFornecimentoAgua = repository.save(escolaFornecimentoAgua);
        return new EscolaFornecimentoAguaDTO(escolaFornecimentoAgua);
    }

    @Transactional
    public EscolaFornecimentoAguaDTO atualizar(CadastroEscolaFornecimentoAguaDTO dto) {
        EscolaFornecimentoAgua escolaFornecimentoAgua = repository.getReferenceById(dto.getIdEscolaFornecimentoAgua());
        atualizaDados(escolaFornecimentoAgua, dto);
        return new EscolaFornecimentoAguaDTO(escolaFornecimentoAgua);
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private EscolaFornecimentoAgua criarEscolaFornecimentoAguaAPartirDTO(CadastroEscolaFornecimentoAguaDTO dto) {
        EscolaFornecimentoAgua escolaFornecimentoAgua = new EscolaFornecimentoAgua();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        FornecimentoAgua fornecimentoAgua = fornecimentoAguaRepository.findById(dto.getFornecimentoAguaId())
                .orElseThrow(() -> new IllegalArgumentException("Fornecimento de água não encontrado"));
        escolaFornecimentoAgua.setEscola(escola);
        escolaFornecimentoAgua.setFornecimentoAgua(fornecimentoAgua);
        return escolaFornecimentoAgua;
    }

    private void atualizaDados(EscolaFornecimentoAgua destino, CadastroEscolaFornecimentoAguaDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        destino.setFornecimentoAgua(fornecimentoAguaRepository.findById(origem.getFornecimentoAguaId())
                .orElseThrow(() -> new IllegalArgumentException("Fornecimento de água não encontrado")));
    }
}
