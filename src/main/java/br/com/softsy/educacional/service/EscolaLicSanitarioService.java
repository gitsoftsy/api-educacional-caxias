package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.EscolaLicSanitarioDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaLicSanitario;
import br.com.softsy.educacional.repository.EscolaLicSanitarioRepository;
import br.com.softsy.educacional.repository.EscolaRepository;

@Service
public class EscolaLicSanitarioService {

    @Autowired
    private EscolaLicSanitarioRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Transactional(readOnly = true)
    public List<EscolaLicSanitarioDTO> listarTudo() {
        List<EscolaLicSanitario> escolasLicSanitario = repository.findAll();
        return escolasLicSanitario.stream()
                .map(EscolaLicSanitarioDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EscolaLicSanitarioDTO> buscarPorIdEscola(Long id) {
        List<EscolaLicSanitario> escolasLicSanitario = repository.findByEscola_IdEscola(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar licença sanitária por id de escola"));
        return escolasLicSanitario.stream()
                .map(EscolaLicSanitarioDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public EscolaLicSanitarioDTO salvar(EscolaLicSanitarioDTO dto) {
        EscolaLicSanitario escolaLicSanitario = criarEscolaLicSanitarioAPartirDTO(dto);
        escolaLicSanitario = repository.save(escolaLicSanitario);
        return new EscolaLicSanitarioDTO(escolaLicSanitario);
    }

    @Transactional
    public EscolaLicSanitarioDTO atualizar(EscolaLicSanitarioDTO dto) {
        EscolaLicSanitario escolaLicSanitario = repository.getReferenceById(dto.getIdEscolaLicSanitario());
        atualizaDados(escolaLicSanitario, dto);
        return new EscolaLicSanitarioDTO(escolaLicSanitario);
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private EscolaLicSanitario criarEscolaLicSanitarioAPartirDTO(EscolaLicSanitarioDTO dto) {
        EscolaLicSanitario escolaLicSanitario = new EscolaLicSanitario();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        BeanUtils.copyProperties(dto, escolaLicSanitario, "idEscolaLicSanitario", "dataCadastro", "escolaId");
        escolaLicSanitario.setEscola(escola);
        escolaLicSanitario.setDataCadastro(LocalDateTime.now());
        return escolaLicSanitario;
    }

    private void atualizaDados(EscolaLicSanitario destino, EscolaLicSanitarioDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        BeanUtils.copyProperties(origem, destino, "idEscolaLicSanitario", "dataCadastro", "escolaId");
    }
}
