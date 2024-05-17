package br.com.softsy.educacional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.EscolaEnergiaEletricaDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaEnergiaEletrica;
import br.com.softsy.educacional.repository.EscolaEnergiaEletricaRepository;
import br.com.softsy.educacional.repository.EscolaRepository;

@Service
public class EscolaEnergiaEletricaService {

    @Autowired
    private EscolaEnergiaEletricaRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Transactional
    public EscolaEnergiaEletricaDTO salvar(EscolaEnergiaEletricaDTO dto) {
        EscolaEnergiaEletrica escolaEnergiaEletrica = criarEscolaEnergiaEletricaAPartirDTO(dto);
        escolaEnergiaEletrica = repository.save(escolaEnergiaEletrica);
        return new EscolaEnergiaEletricaDTO(escolaEnergiaEletrica);
    }

    @Transactional
    public EscolaEnergiaEletricaDTO atualizar(EscolaEnergiaEletricaDTO dto) {
        EscolaEnergiaEletrica escolaEnergiaEletrica = repository.getReferenceById(dto.getIdEscolaEnergiaEletrica());
        atualizarDados(escolaEnergiaEletrica, dto);
        return new EscolaEnergiaEletricaDTO(escolaEnergiaEletrica);
    }

    private EscolaEnergiaEletrica criarEscolaEnergiaEletricaAPartirDTO(EscolaEnergiaEletricaDTO dto) {
        EscolaEnergiaEletrica escolaEnergiaEletrica = new EscolaEnergiaEletrica();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        escolaEnergiaEletrica.setEscola(escola);
        escolaEnergiaEletrica.setRedePublica(dto.getRedePublica());
        escolaEnergiaEletrica.setGerador(dto.getGerador());
        escolaEnergiaEletrica.setOutros(dto.getOutros());
        escolaEnergiaEletrica.setDescricaoOutros(dto.getDescricaoOutros());
        escolaEnergiaEletrica.setSemEnergiaEletrica(dto.getSemEnergiaEletrica());
        return escolaEnergiaEletrica;
    }
    
    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private void atualizarDados(EscolaEnergiaEletrica destino, EscolaEnergiaEletricaDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        destino.setRedePublica(origem.getRedePublica());
        destino.setGerador(origem.getGerador());
        destino.setOutros(origem.getOutros());
        destino.setDescricaoOutros(origem.getDescricaoOutros());
        destino.setSemEnergiaEletrica(origem.getSemEnergiaEletrica());
    }
}
