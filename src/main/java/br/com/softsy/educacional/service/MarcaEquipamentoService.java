package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.MarcaEquipamentoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.MarcaEquipamento;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.MarcaEquipamentoRepository;

@Service
public class MarcaEquipamentoService {

    @Autowired
    private MarcaEquipamentoRepository repository;
    
	@Autowired 
	private DependenciaAdministrativaRepository dependenciaAdministrativaRepository;

    public List<MarcaEquipamento> listarTudo() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public MarcaEquipamentoDTO buscarPorId(Long id) {
        return new MarcaEquipamentoDTO(repository.getReferenceById(id));
    }

    @Transactional
    public MarcaEquipamentoDTO salvar(MarcaEquipamentoDTO dto) {

        MarcaEquipamento marcaEquipamento = criarMarcaEquipamentoAPartirDTO(dto);

        marcaEquipamento = repository.save(marcaEquipamento);
        return new MarcaEquipamentoDTO(marcaEquipamento);
    }


    private MarcaEquipamento criarMarcaEquipamentoAPartirDTO(MarcaEquipamentoDTO dto) {
        MarcaEquipamento marcaEquipamento = new MarcaEquipamento();
        DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(dto.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
        BeanUtils.copyProperties(dto, marcaEquipamento, "idMarcaEquipamento", "ativo", "dataCadastro");
        marcaEquipamento.setDependenciaAdm(dependenciaAdm);
        marcaEquipamento.setDataCadastro(LocalDateTime.now());
        marcaEquipamento.setAtivo('S');
        return marcaEquipamento;
    }

    @Transactional
    public void ativarDesativar(Character status, Long idMarcaEquipamento) {
        MarcaEquipamento marcaEquipamento = repository.getReferenceById(idMarcaEquipamento);
        marcaEquipamento.setAtivo(status);
    }

    @Transactional
    public MarcaEquipamentoDTO atualizar(MarcaEquipamentoDTO dto) {
        MarcaEquipamento marcaEquipamento = repository.getReferenceById(dto.getIdMarcaEquipamento());
        atualizarDados(marcaEquipamento, dto);
        return new MarcaEquipamentoDTO(marcaEquipamento);
    }

    private void atualizarDados(MarcaEquipamento destino, MarcaEquipamentoDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idMarcaEquipamento", "ativo", "dataCadastro");
    	DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(origem.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
    	destino.setDependenciaAdm(dependenciaAdm);
    }
}