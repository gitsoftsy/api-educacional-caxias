package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEscolaDependenciaDTO;
import br.com.softsy.educacional.dto.EscolaDependenciaDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaDependencia;
import br.com.softsy.educacional.model.TipoDependencia;
import br.com.softsy.educacional.repository.EscolaDependenciaRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.TipoDependenciaRepository;

@Service
public class EscolaDependenciaService {

    @Autowired
    private EscolaDependenciaRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private TipoDependenciaRepository tipoDependenciaRepository;

    @Transactional(readOnly = true)
    public List<EscolaDependenciaDTO> listarTudo() {
        List<EscolaDependencia> escolasDependencia = repository.findAll();
        return escolasDependencia.stream()
                .map(EscolaDependenciaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EscolaDependenciaDTO> buscarPorIdEscola(Long id) {
        List<EscolaDependencia> escolasDependencia = repository.findByEscola_IdEscola(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar dependências por id de escola"));
        return escolasDependencia.stream()
                .map(EscolaDependenciaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public EscolaDependenciaDTO salvar(CadastroEscolaDependenciaDTO dto) {
        EscolaDependencia escolaDependencia = criarEscolaDependenciaAPartirDTO(dto);
        escolaDependencia = repository.save(escolaDependencia);
        return new EscolaDependenciaDTO(escolaDependencia);
    }

    @Transactional
    public EscolaDependenciaDTO atualizar(CadastroEscolaDependenciaDTO dto) {
        EscolaDependencia escolaDependencia = repository.getReferenceById(dto.getIdEscolaDependencia());
        atualizarDados(escolaDependencia, dto);
        return new EscolaDependenciaDTO(escolaDependencia);
    }

    private EscolaDependencia criarEscolaDependenciaAPartirDTO(CadastroEscolaDependenciaDTO dto) {
        EscolaDependencia escolaDependencia = new EscolaDependencia();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        TipoDependencia tipoDependencia = tipoDependenciaRepository.findById(dto.getTipoDependenciaId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de dependência não encontrado"));
        escolaDependencia.setEscola(escola);
        escolaDependencia.setDependencia(dto.getDependencia());
        escolaDependencia.setAcessivel(dto.getAcessivel());
        escolaDependencia.setInternaExterna(dto.getInternaExterna());
        escolaDependencia.setClimatizada(dto.getClimatizada());
        escolaDependencia.setQuantidade(dto.getQuantidade());
        escolaDependencia.setTipoDependencia(tipoDependencia);
        return escolaDependencia;
    }
    
    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private void atualizarDados(EscolaDependencia destino, CadastroEscolaDependenciaDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        destino.setDependencia(origem.getDependencia());
        destino.setAcessivel(origem.getAcessivel());
        destino.setInternaExterna(origem.getInternaExterna());
        destino.setClimatizada(origem.getClimatizada());
        destino.setQuantidade(origem.getQuantidade());
        destino.setTipoDependencia(tipoDependenciaRepository.findById(origem.getTipoDependenciaId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de dependência não encontrado")));
    }
}
