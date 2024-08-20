package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.FeriadoEscolaDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.FeriadoEscola;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.FeriadoEscolaRepository;

@Service
public class FeriadoEscolaService {

    @Autowired
    private FeriadoEscolaRepository repository;
    
    @Autowired
    private EscolaRepository escolaRepository;

    @Transactional(readOnly = true)
    public List<FeriadoEscolaDTO> listarTudo() {
        List<FeriadoEscola> feriadosEscola = repository.findAll();
        return feriadosEscola.stream()
                .map(FeriadoEscolaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FeriadoEscolaDTO buscarPorId(Long id) {
        return new FeriadoEscolaDTO(repository.getReferenceById(id));
    }

    @Transactional
    public FeriadoEscolaDTO salvar(FeriadoEscolaDTO dto) {
        FeriadoEscola feriadoEscola = criarFeriadoEscolaAPartirDTO(dto);
        feriadoEscola = repository.save(feriadoEscola);
        return new FeriadoEscolaDTO(feriadoEscola);
    }
    
    @Transactional(readOnly = true)
    public List<FeriadoEscolaDTO> buscarPorIdEscola(Long idEscola) {
        List<FeriadoEscola> feriadoEscola = repository.findByEscola_IdEscola(idEscola)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar feriado por ID da escola"));
        return feriadoEscola.stream()
                .map(FeriadoEscolaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public FeriadoEscolaDTO atualizar(FeriadoEscolaDTO dto) {
        FeriadoEscola feriadoEscola = repository.getReferenceById(dto.getIdFeriadoEscola());
        atualizaDados(feriadoEscola, dto);
        return new FeriadoEscolaDTO(feriadoEscola);
    }

    private FeriadoEscola criarFeriadoEscolaAPartirDTO(FeriadoEscolaDTO dto) {
        FeriadoEscola feriadoEscola = new FeriadoEscola();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        BeanUtils.copyProperties(dto, feriadoEscola, "idFeriadoConta", "dataCadastro");
        feriadoEscola.setEscola(escola);
        feriadoEscola.setDataCadastro(LocalDateTime.now());
        feriadoEscola.setAtivo('S');
        return feriadoEscola;
    }

    private void atualizaDados(FeriadoEscola destino, FeriadoEscolaDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        BeanUtils.copyProperties(origem, destino, "idFeriadoConta", "dataCadastro", "ativo");
    }

    @Transactional
    public void ativaDesativa(char status, Long id) {
        FeriadoEscola feriadoEscola = repository.getReferenceById(id);
        feriadoEscola.setAtivo(status);
    }
}