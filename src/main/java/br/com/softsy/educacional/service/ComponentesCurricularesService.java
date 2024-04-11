package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.ComponentesCurricularesDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.ComponentesCurriculares;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.repository.ComponentesCurricularesRepository;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;

@Service
public class ComponentesCurricularesService {

    @Autowired
    private ComponentesCurricularesRepository repository;
    
	@Autowired 
	private DependenciaAdministrativaRepository dependenciaAdministrativaRepository;

    public List<ComponentesCurriculares> listarTudo() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public ComponentesCurricularesDTO buscarPorId(Long id) {
        return new ComponentesCurricularesDTO(repository.getReferenceById(id));
    }

    @Transactional
    public ComponentesCurricularesDTO salvar(ComponentesCurricularesDTO dto) {
        validarComponentesCurriculares(dto.getComponentesCurriculares());

        ComponentesCurriculares componentesCurriculares = criarComponentesCurricularesAPartirDTO(dto);

        componentesCurriculares = repository.save(componentesCurriculares);
        return new ComponentesCurricularesDTO(componentesCurriculares);
    }

    @Transactional
    public ComponentesCurricularesDTO atualizar(ComponentesCurricularesDTO dto) {
        ComponentesCurriculares componentesCurriculares = repository.getReferenceById(dto.getIdComponentesCurriculares());
        atualizarDados(componentesCurriculares, dto);
        return new ComponentesCurricularesDTO(componentesCurriculares);
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    private ComponentesCurriculares criarComponentesCurricularesAPartirDTO(ComponentesCurricularesDTO dto) {
        ComponentesCurriculares componentesCurriculares = new ComponentesCurriculares();
        DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(dto.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
        componentesCurriculares.setDependenciaAdm(dependenciaAdm);
        componentesCurriculares.setComponentesCurriculares(dto.getComponentesCurriculares());
        componentesCurriculares.setDataCadastro(LocalDateTime.now());
        return componentesCurriculares;
    }

    private void validarComponentesCurriculares(String componentesCurriculares) {
        Optional<ComponentesCurriculares> componentesCurricularesExistente = repository
                .findByComponentesCurriculares(componentesCurriculares).stream().findFirst();
        if (componentesCurricularesExistente.isPresent()) {
            throw new UniqueException("Esse componente curricular já existe.");
        }
    }

    private void atualizarDados(ComponentesCurriculares destino, ComponentesCurricularesDTO origem) {
        destino.setComponentesCurriculares(origem.getComponentesCurriculares());
        DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(origem.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
        destino.setDependenciaAdm(dependenciaAdm);
    }
}