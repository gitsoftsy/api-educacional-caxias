package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEscolaFonteEnergiaEletricaDTO;
import br.com.softsy.educacional.dto.EscolaFonteEnergiaEletricaDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaFonteEnergiaEletrica;
import br.com.softsy.educacional.model.FonteEnergiaEletrica;
import br.com.softsy.educacional.repository.EscolaFonteEnergiaEletricaRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.FonteEnergiaEletricaRepository;

@Service
public class EscolaFonteEnergiaEletricaService {

    @Autowired
    private EscolaFonteEnergiaEletricaRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private FonteEnergiaEletricaRepository fonteEnergiaEletricaRepository;

    @Transactional(readOnly = true)
    public List<EscolaFonteEnergiaEletricaDTO> listarTudo() {
        List<EscolaFonteEnergiaEletrica> escolasFonteEnergiaEletrica = repository.findAll();
        return escolasFonteEnergiaEletrica.stream()
                .map(EscolaFonteEnergiaEletricaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EscolaFonteEnergiaEletricaDTO> buscarPorIdEscola(Long id) {
        List<EscolaFonteEnergiaEletrica> escolasFonteEnergiaEletrica = repository.findByEscola_IdEscola(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar fonte de energia elétrica por id de escola"));
        return escolasFonteEnergiaEletrica.stream()
                .map(EscolaFonteEnergiaEletricaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public EscolaFonteEnergiaEletricaDTO salvar(CadastroEscolaFonteEnergiaEletricaDTO dto) {
        EscolaFonteEnergiaEletrica escolaFonteEnergiaEletrica = criarEscolaFonteEnergiaEletricaAPartirDTO(dto);
        escolaFonteEnergiaEletrica = repository.save(escolaFonteEnergiaEletrica);
        return new EscolaFonteEnergiaEletricaDTO(escolaFonteEnergiaEletrica);
    }

    @Transactional
    public EscolaFonteEnergiaEletricaDTO atualizar(CadastroEscolaFonteEnergiaEletricaDTO dto) {
        EscolaFonteEnergiaEletrica escolaFonteEnergiaEletrica = repository.getReferenceById(dto.getIdEscolaFonteEnergiaEletrica());
        atualizaDados(escolaFonteEnergiaEletrica, dto);
        return new EscolaFonteEnergiaEletricaDTO(escolaFonteEnergiaEletrica);
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private EscolaFonteEnergiaEletrica criarEscolaFonteEnergiaEletricaAPartirDTO(CadastroEscolaFonteEnergiaEletricaDTO dto) {
        EscolaFonteEnergiaEletrica escolaFonteEnergiaEletrica = new EscolaFonteEnergiaEletrica();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        FonteEnergiaEletrica fonteEnergiaEletrica = fonteEnergiaEletricaRepository.findById(dto.getFonteEnergiaEletricaId())
                .orElseThrow(() -> new IllegalArgumentException("Fonte de energia elétrica não encontrada"));
        escolaFonteEnergiaEletrica.setEscola(escola);
        escolaFonteEnergiaEletrica.setFonteEnergiaEletrica(fonteEnergiaEletrica);
        return escolaFonteEnergiaEletrica;
    }

    private void atualizaDados(EscolaFonteEnergiaEletrica destino, CadastroEscolaFonteEnergiaEletricaDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        destino.setFonteEnergiaEletrica(fonteEnergiaEletricaRepository.findById(origem.getFonteEnergiaEletricaId())
                .orElseThrow(() -> new IllegalArgumentException("Fonte de energia elétrica não encontrada")));
    }
}