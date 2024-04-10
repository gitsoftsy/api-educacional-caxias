package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.SituacaoProfessorDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.SituacaoProfessor;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.SituacaoProfessorRepository;

@Service
public class SituacaoProfessorService {

    @Autowired
    private SituacaoProfessorRepository repository;
    
	@Autowired 
	private DependenciaAdministrativaRepository dependenciaAdministrativaRepository;

    public List<SituacaoProfessor> listarTudo() {
    	 return repository.findAll();
    }

    @Transactional(readOnly = true)
    public SituacaoProfessorDTO buscarPorId(Long id) {
        SituacaoProfessor situacaoProfessor = repository.getReferenceById(id);
        return new SituacaoProfessorDTO(situacaoProfessor);
    }

    @Transactional
    public SituacaoProfessorDTO salvar(SituacaoProfessorDTO dto) {

        SituacaoProfessor situacaoProfessor = criarSituacaoProfessorAPartirDTO(dto);

        situacaoProfessor = repository.save(situacaoProfessor);
        return new SituacaoProfessorDTO(situacaoProfessor);
    }

    private SituacaoProfessor criarSituacaoProfessorAPartirDTO(SituacaoProfessorDTO dto) {
        SituacaoProfessor situacaoProfessor = new SituacaoProfessor();
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(dto.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		situacaoProfessor.setDependenciaAdm(dependenciaAdm);
        situacaoProfessor.setSituacaoProfessor(dto.getSituacaoProfessor());
        situacaoProfessor.setDataCadastro(LocalDateTime.now());
        situacaoProfessor.setAtivo('S');
        return situacaoProfessor;
    }

    @Transactional
    public SituacaoProfessorDTO atualizar(SituacaoProfessorDTO dto) {
        SituacaoProfessor situacaoProfessor = repository.getReferenceById(dto.getIdSituacaoProfessor());
        atualizaDados(situacaoProfessor, dto);
        return new SituacaoProfessorDTO(situacaoProfessor);
    }

    @Transactional
    public void ativaDesativa(char status, Long idSituacaoProfessor) {
        SituacaoProfessor situacaoProfessor = repository.getReferenceById(idSituacaoProfessor);
        situacaoProfessor.setAtivo(status);
    }


    private void atualizaDados(SituacaoProfessor destino, SituacaoProfessorDTO origem) {
    	BeanUtils.copyProperties(origem, destino, "idSituacaoProfessor", "dataCadastro", "ativo");
		DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(origem.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
		destino.setDependenciaAdm(dependenciaAdm);
    }
}
