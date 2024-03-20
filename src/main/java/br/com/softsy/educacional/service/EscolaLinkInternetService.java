package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEscolaLinkInternetDTO;
import br.com.softsy.educacional.dto.EscolaLinkInternetDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaLinkInternet;
import br.com.softsy.educacional.model.ProvedorInternet;
import br.com.softsy.educacional.repository.EscolaLinkInternetRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.ProvedorInternetRepository;

@Service
public class EscolaLinkInternetService {

    @Autowired
    private EscolaLinkInternetRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private ProvedorInternetRepository provedorInternetRepository;

    @Transactional(readOnly = true)
    public List<EscolaLinkInternetDTO> listarTudo() {
        List<EscolaLinkInternet> escolasLinkInternet = repository.findAll();
        return escolasLinkInternet.stream()
                .map(EscolaLinkInternetDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EscolaLinkInternetDTO> buscarPorIdEscola(Long id) {
        List<EscolaLinkInternet> escolasLinkInternet = repository.findByEscola_IdEscola(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar links de internet por id de escola"));
        return escolasLinkInternet.stream()
                .map(EscolaLinkInternetDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public EscolaLinkInternetDTO salvar(CadastroEscolaLinkInternetDTO dto) {
        EscolaLinkInternet escolaLinkInternet = criarEscolaLinkInternetAPartirDTO(dto);
        escolaLinkInternet = repository.save(escolaLinkInternet);
        return new EscolaLinkInternetDTO(escolaLinkInternet);
    }

    @Transactional
    public EscolaLinkInternetDTO atualizar(CadastroEscolaLinkInternetDTO dto) {
        EscolaLinkInternet escolaLinkInternet = repository.getReferenceById(dto.getIdEscolaLinkInternet());
        atualizarDados(escolaLinkInternet, dto);
        return new EscolaLinkInternetDTO(escolaLinkInternet);
    }

    @Transactional
    public void ativarDesativar(Character status, Long idEscolaLinkInternet) {
        EscolaLinkInternet escolaLinkInternet = repository.getReferenceById(idEscolaLinkInternet);
        escolaLinkInternet.setAtivo(status);
    }

    private EscolaLinkInternet criarEscolaLinkInternetAPartirDTO(CadastroEscolaLinkInternetDTO dto) {
        EscolaLinkInternet escolaLinkInternet = new EscolaLinkInternet();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        ProvedorInternet provedorInternet = provedorInternetRepository.findById(dto.getProvedorInternetId())
                .orElseThrow(() -> new IllegalArgumentException("Provedor de internet não encontrado"));
        escolaLinkInternet.setEscola(escola);
        escolaLinkInternet.setProvedorInternet(provedorInternet);
        escolaLinkInternet.setVelocidadeMb(dto.getVelocidadeMb());
        escolaLinkInternet.setAdministrativo(dto.getAdministrativo());
        escolaLinkInternet.setEstudante(dto.getEstudante());
        escolaLinkInternet.setAtivo('S');
        return escolaLinkInternet;
    }

    private void atualizarDados(EscolaLinkInternet destino, CadastroEscolaLinkInternetDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        destino.setProvedorInternet(provedorInternetRepository.findById(origem.getProvedorInternetId())
                .orElseThrow(() -> new IllegalArgumentException("Provedor de internet não encontrado")));
        destino.setVelocidadeMb(origem.getVelocidadeMb());
        destino.setAdministrativo(origem.getAdministrativo());
        destino.setEstudante(origem.getEstudante());
    }
}