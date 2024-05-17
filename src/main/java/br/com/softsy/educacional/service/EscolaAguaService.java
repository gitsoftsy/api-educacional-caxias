package br.com.softsy.educacional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.EscolaAguaDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaAgua;
import br.com.softsy.educacional.repository.EscolaAguaRepository;
import br.com.softsy.educacional.repository.EscolaRepository;

@Service
public class EscolaAguaService {

    @Autowired
    private EscolaAguaRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Transactional
    public EscolaAguaDTO salvar(EscolaAguaDTO dto) {
        EscolaAgua escolaAgua = criarEscolaAguaAPartirDTO(dto);
        escolaAgua = repository.save(escolaAgua);
        return new EscolaAguaDTO(escolaAgua);
    }

    @Transactional
    public EscolaAguaDTO atualizar(EscolaAguaDTO dto) {
        EscolaAgua escolaAgua = repository.getReferenceById(dto.getIdEscolaAgua());
        atualizarDados(escolaAgua, dto);
        return new EscolaAguaDTO(escolaAgua);
    }

    private EscolaAgua criarEscolaAguaAPartirDTO(EscolaAguaDTO dto) {
        EscolaAgua escolaAgua = new EscolaAgua();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        escolaAgua.setEscola(escola);
        escolaAgua.setAguaTratada(dto.getAguaTratada());
        escolaAgua.setAguaPocoArtesiano(dto.getAguaPocoArtesiano());
        escolaAgua.setAguaCacimba(dto.getAguaCacimba());
        escolaAgua.setAguaFonteRio(dto.getAguaFonteRio());
        escolaAgua.setSemAgua(dto.getSemAgua());
        return escolaAgua;
    }
    
    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private void atualizarDados(EscolaAgua destino, EscolaAguaDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        destino.setAguaTratada(origem.getAguaTratada());
        destino.setAguaPocoArtesiano(origem.getAguaPocoArtesiano());
        destino.setAguaCacimba(origem.getAguaCacimba());
        destino.setAguaFonteRio(origem.getAguaFonteRio());
        destino.setSemAgua(origem.getSemAgua());
    }
}