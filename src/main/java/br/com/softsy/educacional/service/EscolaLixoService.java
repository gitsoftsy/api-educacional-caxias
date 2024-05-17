package br.com.softsy.educacional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.EscolaLixoDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaLixo;
import br.com.softsy.educacional.repository.EscolaLixoRepository;
import br.com.softsy.educacional.repository.EscolaRepository;

@Service
public class EscolaLixoService {

    @Autowired
    private EscolaLixoRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Transactional
    public EscolaLixoDTO salvar(EscolaLixoDTO dto) {
        EscolaLixo escolaLixo = criarEscolaLixoAPartirDTO(dto);
        escolaLixo = repository.save(escolaLixo);
        return new EscolaLixoDTO(escolaLixo);
    }

    @Transactional
    public EscolaLixoDTO atualizar(EscolaLixoDTO dto) {
        EscolaLixo escolaLixo = repository.getReferenceById(dto.getIdEscolaLixo());
        atualizarDados(escolaLixo, dto);
        return new EscolaLixoDTO(escolaLixo);
    }

    private EscolaLixo criarEscolaLixoAPartirDTO(EscolaLixoDTO dto) {
        EscolaLixo escolaLixo = new EscolaLixo();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        escolaLixo.setEscola(escola);
        escolaLixo.setColetaPeriodica(dto.getColetaPeriodica());
        escolaLixo.setQueimaLixo(dto.getQueimaLixo());
        escolaLixo.setJogaOutraArea(dto.getJogaOutraArea());
        escolaLixo.setReciclagem(dto.getReciclagem());
        escolaLixo.setEnterra(dto.getEnterra());
        escolaLixo.setOutros(dto.getOutros());
        escolaLixo.setDescricaoOutros(dto.getDescricaoOutros());
        return escolaLixo;
    }
    
    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private void atualizarDados(EscolaLixo destino, EscolaLixoDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        destino.setColetaPeriodica(origem.getColetaPeriodica());
        destino.setQueimaLixo(origem.getQueimaLixo());
        destino.setJogaOutraArea(origem.getJogaOutraArea());
        destino.setReciclagem(origem.getReciclagem());
        destino.setEnterra(origem.getEnterra());
        destino.setOutros(origem.getOutros());
        destino.setDescricaoOutros(origem.getDescricaoOutros());
    }
}