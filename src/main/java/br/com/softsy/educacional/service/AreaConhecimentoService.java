package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.AreaConhecimentoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.AreaConhecimento;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.repository.AreaConhecimentoRepository;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;

@Service
public class AreaConhecimentoService {

    @Autowired
    private AreaConhecimentoRepository repository;
    
	@Autowired 
	private DependenciaAdministrativaRepository dependenciaAdministrativaRepository;

    public List<AreaConhecimentoDTO> listarTudo() {
        List<AreaConhecimento> areaConhecimentoList = repository.findAll();
        return areaConhecimentoList.stream()
                .map(AreaConhecimentoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AreaConhecimentoDTO buscarPorId(Long id) {
        Optional<AreaConhecimento> areaConhecimentoOptional = repository.findById(id);
        if (areaConhecimentoOptional.isPresent()) {
            AreaConhecimento areaConhecimento = areaConhecimentoOptional.get();
            return new AreaConhecimentoDTO(areaConhecimento);
        }
        return null;
    }

    @Transactional
    public AreaConhecimentoDTO salvar(AreaConhecimentoDTO dto) {
        validarAreaConhecimento(dto.getAreaConhecimento());

        AreaConhecimento areaConhecimento = criarAreaConhecimentoAPartirDTO(dto);

        areaConhecimento = repository.save(areaConhecimento);
        return new AreaConhecimentoDTO(areaConhecimento);
    }

    @Transactional
    public AreaConhecimentoDTO atualizar(AreaConhecimentoDTO dto) {
        AreaConhecimento areaConhecimento = repository.getReferenceById(dto.getIdAreaConhecimento());
            atualizarDados(areaConhecimento, dto);
            return new AreaConhecimentoDTO(areaConhecimento);
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    private AreaConhecimento criarAreaConhecimentoAPartirDTO(AreaConhecimentoDTO dto) {
        AreaConhecimento areaConhecimento = new AreaConhecimento();
        DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(dto.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
        areaConhecimento.setDependenciaAdm(dependenciaAdm);
        areaConhecimento.setAreaConhecimento(dto.getAreaConhecimento());
        areaConhecimento.setDataCadastro(LocalDateTime.now());
        return areaConhecimento;
    }

    private void validarAreaConhecimento(String areaConhecimento) {
        Optional<AreaConhecimento> areaConhecimentoExistente = repository.findByAreaConhecimento(areaConhecimento).stream().findFirst();
        if (areaConhecimentoExistente.isPresent()) {
            throw new UniqueException("Essa área de conhecimento já existe.");
        }
    }

    private void atualizarDados(AreaConhecimento destino, AreaConhecimentoDTO origem) {
        destino.setAreaConhecimento(origem.getAreaConhecimento());
        DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(origem.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
        destino.setDependenciaAdm(dependenciaAdm);
    }
}
