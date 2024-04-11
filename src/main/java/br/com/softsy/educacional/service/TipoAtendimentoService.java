package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.TipoAtendimentoDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.TipoAtendimento;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.TipoAtendimentoRepository;

@Service
public class TipoAtendimentoService {

    @Autowired
    private TipoAtendimentoRepository repository;
    
	@Autowired 
	private DependenciaAdministrativaRepository dependenciaAdministrativaRepository;

    public List<TipoAtendimento> listarTudo() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public TipoAtendimentoDTO buscarPorId(Long id) {
        return new TipoAtendimentoDTO(repository.getReferenceById(id));
    }

    @Transactional
    public TipoAtendimentoDTO salvar(TipoAtendimentoDTO dto) {
        validarTipoAtendimento(dto.getTipoAtendimento());

        TipoAtendimento tipoAtendimento = criarTipoAtendimentoAPartirDTO(dto);

        tipoAtendimento = repository.save(tipoAtendimento);
        return new TipoAtendimentoDTO(tipoAtendimento);
    }

    private TipoAtendimento criarTipoAtendimentoAPartirDTO(TipoAtendimentoDTO dto) {
        TipoAtendimento tipoAtendimento = new TipoAtendimento();
        DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(dto.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
        tipoAtendimento.setDependenciaAdm(dependenciaAdm);
        tipoAtendimento.setTipoAtendimento(dto.getTipoAtendimento());
        tipoAtendimento.setDataCadastro(LocalDateTime.now());
        return tipoAtendimento;
    }
    
    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }


    @Transactional
    public TipoAtendimentoDTO atualizar(TipoAtendimentoDTO dto) {
        TipoAtendimento tipoAtendimento = repository.getReferenceById(dto.getIdTipoAtendimento());
        atualizaDados(tipoAtendimento, dto);
        return new TipoAtendimentoDTO(tipoAtendimento);
    }


    private void validarTipoAtendimento(String tipoAtendimento) {
        Optional<TipoAtendimento> tipoAtendimentoExistente = repository.findByTipoAtendimento(tipoAtendimento).stream().findFirst();
        if (tipoAtendimentoExistente.isPresent()) {
            throw new UniqueException("Esse tipo de atendimento já existe.");
        }
    }

    private void atualizaDados(TipoAtendimento destino, TipoAtendimentoDTO origem) {
       BeanUtils.copyProperties(origem, destino, "dataCadastro", "idTipoAtendimento");
       DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(origem.getDependenciaAdmId())
               .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
       destino.setDependenciaAdm(dependenciaAdm);
    }

}
