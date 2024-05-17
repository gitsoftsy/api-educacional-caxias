package br.com.softsy.educacional.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.EscolaDispositivoDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaDispositivo;
import br.com.softsy.educacional.repository.EscolaDispositivoRepository;
import br.com.softsy.educacional.repository.EscolaRepository;

@Service
public class EscolaDispositivoService {

    @Autowired
    private EscolaDispositivoRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Transactional
    public EscolaDispositivoDTO salvar(EscolaDispositivoDTO dto) {
        EscolaDispositivo escolaDispositivo = criarEscolaDispositivoAPartirDTO(dto);
        escolaDispositivo = repository.save(escolaDispositivo);
        return new EscolaDispositivoDTO(escolaDispositivo);
    }

    @Transactional
    public EscolaDispositivoDTO atualizar(EscolaDispositivoDTO dto) {
        EscolaDispositivo escolaDispositivo = repository.getReferenceById(dto.getIdEscolaDispositivo());
        atualizarDados(escolaDispositivo, dto);
        return new EscolaDispositivoDTO(escolaDispositivo);
    }

    private EscolaDispositivo criarEscolaDispositivoAPartirDTO(EscolaDispositivoDTO dto) {
        EscolaDispositivo escolaDispositivo = new EscolaDispositivo();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        escolaDispositivo.setEscola(escola);
        escolaDispositivo.setQtdComputadoresAlunos(dto.getQtdComputadoresAlunos());
        escolaDispositivo.setQtdAparelhosDvd(dto.getQtdAparelhosDvd());
        escolaDispositivo.setQtdImpressora(dto.getQtdImpressora());
        escolaDispositivo.setQtdParabolicas(dto.getQtdParabolicas());
        escolaDispositivo.setQtdCopiadoras(dto.getQtdCopiadoras());
        escolaDispositivo.setQtdProjetores(dto.getQtdProjetores());
        escolaDispositivo.setQtdTvs(dto.getQtdTvs());
        return escolaDispositivo;
    }
    
    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private void atualizarDados(EscolaDispositivo destino, EscolaDispositivoDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        destino.setQtdComputadoresAlunos(origem.getQtdComputadoresAlunos());
        destino.setQtdAparelhosDvd(origem.getQtdAparelhosDvd());
        destino.setQtdImpressora(origem.getQtdImpressora());
        destino.setQtdParabolicas(origem.getQtdParabolicas());
        destino.setQtdCopiadoras(origem.getQtdCopiadoras());
        destino.setQtdProjetores(origem.getQtdProjetores());
        destino.setQtdTvs(origem.getQtdTvs());
    }
}