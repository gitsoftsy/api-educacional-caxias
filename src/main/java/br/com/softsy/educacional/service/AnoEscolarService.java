package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.AnoEscolarDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.AnoEscolar;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.repository.AnoEscolarRepository;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;

@Service
public class AnoEscolarService {

    @Autowired
    private AnoEscolarRepository repository;
    
	@Autowired 
	private DependenciaAdministrativaRepository dependenciaAdministrativaRepository;

    public List<AnoEscolar> listarTudo() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public AnoEscolarDTO buscarPorId(Long id) {
       return new AnoEscolarDTO(repository.getReferenceById(id));
    }

    @Transactional
    public AnoEscolarDTO salvar(AnoEscolarDTO dto) {
        validarAnoEscolar(dto.getAnoEscolar());

        AnoEscolar anoEscolar = criarAnoEscolarAPartirDTO(dto);

        anoEscolar = repository.save(anoEscolar);
        return new AnoEscolarDTO(anoEscolar);
    }

    @Transactional
    public AnoEscolarDTO atualizar(AnoEscolarDTO dto) {
        AnoEscolar anoEscolar = repository.getReferenceById(dto.getIdAnoEscolar());
            atualizarDados(anoEscolar, dto);
            return new AnoEscolarDTO(anoEscolar);
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    private AnoEscolar criarAnoEscolarAPartirDTO(AnoEscolarDTO dto) {
        AnoEscolar anoEscolar = new AnoEscolar();
        DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(dto.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
        BeanUtils.copyProperties(dto, anoEscolar, "idAnoEscolar", "dataCadastro");
        anoEscolar.setDependenciaAdm(dependenciaAdm);
        anoEscolar.setDataCadastro(LocalDateTime.now());
        return anoEscolar;
    }

    private void validarAnoEscolar(String anoEscolar) {
        Optional<AnoEscolar> anoEscolarExistente = repository.findByAnoEscolar(anoEscolar).stream().findFirst();
        if (anoEscolarExistente.isPresent()) {
            throw new UniqueException("Esse ano escolar já existe.");
        }
    }

    private void atualizarDados(AnoEscolar destino, AnoEscolarDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idAnoEscolar", "dataCadastro");
        DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(origem.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
        destino.setDependenciaAdm(dependenciaAdm);
    }
}
