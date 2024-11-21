package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.TipoMatriculaDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.TipoEnsinoMedio;
import br.com.softsy.educacional.model.TipoMatricula;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.TipoMatriculaRepository;

@Service
public class TipoMatriculaService {

    @Autowired
    private TipoMatriculaRepository repository;

    @Autowired
    private ContaRepository contaRepository;

    
    public List<TipoMatricula> listarTudo() {
		return repository.findAll();
	}


    @Transactional(readOnly = true)
    public List<TipoMatriculaDTO> buscarPorIdConta(Long idConta) {
        List<TipoMatricula> tipoMatriculas = repository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar TipoMatricula por ID da conta."));
        return tipoMatriculas.stream()
                .map(TipoMatriculaDTO::new)  
                .collect(Collectors.toList());
    }

  
    @Transactional(readOnly = true)
    public TipoMatriculaDTO buscarPorId(Long id) {
        TipoMatricula tipoMatricula = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TipoMatricula não encontrado com o ID fornecido."));
        return new TipoMatriculaDTO(tipoMatricula); 
    }

  
    @Transactional
    public TipoMatriculaDTO salvar(TipoMatriculaDTO dto) {
        TipoMatricula tipoMatricula = criarTipoMatriculaAPartirDTO(dto);
        tipoMatricula = repository.save(tipoMatricula);
        return new TipoMatriculaDTO(tipoMatricula);  
    }

 
    @Transactional
    public TipoMatriculaDTO atualizar(TipoMatriculaDTO dto) {
        TipoMatricula tipoMatricula = repository.findById(dto.getIdTipoMatricula())
                .orElseThrow(() -> new IllegalArgumentException("TipoMatricula não encontrado para atualização."));
        atualizaDados(tipoMatricula, dto);
        tipoMatricula = repository.save(tipoMatricula);
        return new TipoMatriculaDTO(tipoMatricula);  
    }


    private TipoMatricula criarTipoMatriculaAPartirDTO(TipoMatriculaDTO dto) {
        TipoMatricula tipoMatricula = new TipoMatricula();
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada para o ID fornecido."));
        BeanUtils.copyProperties(dto, tipoMatricula, "idTipoMatricula");
        tipoMatricula.setConta(conta);
        return tipoMatricula;
    }


    private void atualizaDados(TipoMatricula destino, TipoMatriculaDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idTipoMatricula");
        Conta conta = contaRepository.findById(origem.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada para o ID fornecido."));
        destino.setConta(conta);
    }
}
