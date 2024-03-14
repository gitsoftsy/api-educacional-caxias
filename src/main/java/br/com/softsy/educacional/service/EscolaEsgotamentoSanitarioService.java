package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEscolaEsgotamentoSanitarioDTO;
import br.com.softsy.educacional.dto.EscolaEsgotamentoSanitarioDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaEsgotamentoSanitario;
import br.com.softsy.educacional.model.EsgotamentoSanitario;
import br.com.softsy.educacional.repository.EscolaEsgotamentoSanitarioRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.EsgotamentoSanitarioRepository;

@Service
public class EscolaEsgotamentoSanitarioService {

    @Autowired
    private EscolaEsgotamentoSanitarioRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private EsgotamentoSanitarioRepository esgotamentoSanitarioRepository;

    @Transactional(readOnly = true)
    public List<EscolaEsgotamentoSanitarioDTO> listarTudo() {
        List<EscolaEsgotamentoSanitario> escolasEsgotamentoSanitario = repository.findAll();
        return escolasEsgotamentoSanitario.stream()
                .map(EscolaEsgotamentoSanitarioDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EscolaEsgotamentoSanitarioDTO> buscarPorIdEscola(Long id) {
        List<EscolaEsgotamentoSanitario> escolasEsgotamentoSanitario = repository.findByEscola_IdEscola(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar esgotamento sanitário por id de escola"));
        return escolasEsgotamentoSanitario.stream()
                .map(EscolaEsgotamentoSanitarioDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public EscolaEsgotamentoSanitarioDTO salvar(CadastroEscolaEsgotamentoSanitarioDTO dto) {
        EscolaEsgotamentoSanitario escolaEsgotamentoSanitario = criarEscolaEsgotamentoSanitarioAPartirDTO(dto);
        escolaEsgotamentoSanitario = repository.save(escolaEsgotamentoSanitario);
        return new EscolaEsgotamentoSanitarioDTO(escolaEsgotamentoSanitario);
    }

    @Transactional
    public EscolaEsgotamentoSanitarioDTO atualizar(CadastroEscolaEsgotamentoSanitarioDTO dto) {
        EscolaEsgotamentoSanitario escolaEsgotamentoSanitario = repository.getReferenceById(dto.getIdEscolaEsgotamentoSanitario());
        atualizaDados(escolaEsgotamentoSanitario, dto);
        return new EscolaEsgotamentoSanitarioDTO(escolaEsgotamentoSanitario);
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private EscolaEsgotamentoSanitario criarEscolaEsgotamentoSanitarioAPartirDTO(CadastroEscolaEsgotamentoSanitarioDTO dto) {
        EscolaEsgotamentoSanitario escolaEsgotamentoSanitario = new EscolaEsgotamentoSanitario();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        EsgotamentoSanitario esgotamentoSanitario = esgotamentoSanitarioRepository.findById(dto.getEsgotamentoSanitarioId())
                .orElseThrow(() -> new IllegalArgumentException("Esgotamento sanitário não encontrado"));
        escolaEsgotamentoSanitario.setEscola(escola);
        escolaEsgotamentoSanitario.setEsgotamentoSanitario(esgotamentoSanitario);
        return escolaEsgotamentoSanitario;
    }

    private void atualizaDados(EscolaEsgotamentoSanitario destino, CadastroEscolaEsgotamentoSanitarioDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        destino.setEsgotamentoSanitario(esgotamentoSanitarioRepository.findById(origem.getEsgotamentoSanitarioId())
                .orElseThrow(() -> new IllegalArgumentException("Esgotamento sanitário não encontrado")));
    }
}
