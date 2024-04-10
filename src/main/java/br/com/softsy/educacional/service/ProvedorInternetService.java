package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.ProvedorInternetDTO;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.DependenciaAdministrativa;
import br.com.softsy.educacional.model.ProvedorInternet;
import br.com.softsy.educacional.repository.DependenciaAdministrativaRepository;
import br.com.softsy.educacional.repository.ProvedorInternetRepository;

@Service
public class ProvedorInternetService {

    @Autowired
    private ProvedorInternetRepository repository;
    
	@Autowired 
	private DependenciaAdministrativaRepository dependenciaAdministrativaRepository;

    public List<ProvedorInternet> listarTudo() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public ProvedorInternetDTO buscarPorId(Long id) {
        return new ProvedorInternetDTO(repository.getReferenceById(id));
    }

    @Transactional
    public ProvedorInternetDTO salvar(ProvedorInternetDTO dto) {
        ProvedorInternet provedorInternet = criarProvedorInternetAPartirDTO(dto);

        provedorInternet = repository.save(provedorInternet);
        return new ProvedorInternetDTO(provedorInternet);
    }


    private ProvedorInternet criarProvedorInternetAPartirDTO(ProvedorInternetDTO dto) {
        ProvedorInternet provedorInternet = new ProvedorInternet();
        DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(dto.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
        BeanUtils.copyProperties(dto, provedorInternet, "idProvedorInternet", "ativo", "dataCadastro");
        provedorInternet.setDependenciaAdm(dependenciaAdm);
        provedorInternet.setDataCadastro(LocalDateTime.now());
        provedorInternet.setAtivo('S');
        return provedorInternet;
    }

    @Transactional
    public void ativaDesativa(char status, Long idProvedorInternet) {
        ProvedorInternet provedorInternet = repository.getReferenceById(idProvedorInternet);
        provedorInternet.setAtivo(status);
    }

    @Transactional
    public ProvedorInternetDTO atualizar(ProvedorInternetDTO dto) {
        ProvedorInternet provedorInternet = repository.getReferenceById(dto.getIdProvedorInternet());
        atualizaDados(provedorInternet, dto);
        return new ProvedorInternetDTO(provedorInternet);
    }

    private void atualizaDados(ProvedorInternet destino, ProvedorInternetDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idProvedorInternet", "ativo", "dataCadastro");
        DependenciaAdministrativa dependenciaAdm = dependenciaAdministrativaRepository.findById(origem.getDependenciaAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Dependência administrativa não encontrada"));
        destino.setDependenciaAdm(dependenciaAdm);
    }
}
