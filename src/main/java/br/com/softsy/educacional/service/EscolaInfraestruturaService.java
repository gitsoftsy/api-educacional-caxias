package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.EscolaDestinacaoLixoDTO;
import br.com.softsy.educacional.dto.EscolaInfraestruturaDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaDestinacaoLixo;
import br.com.softsy.educacional.model.EscolaInfraestrutura;
import br.com.softsy.educacional.repository.EscolaInfraestruturaRepository;
import br.com.softsy.educacional.repository.EscolaRepository;

@Service
public class EscolaInfraestruturaService {

    @Autowired
    private EscolaInfraestruturaRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;
    
	@Transactional(readOnly = true)
	public List<EscolaInfraestruturaDTO> buscarPorIdEscola(Long id) {
		List<EscolaInfraestrutura> escolasDestinacaoLixo = repository.findByEscola_IdEscola(id)
				.orElseThrow(() -> new IllegalArgumentException("Erro ao buscar infraestrutura por id de escola"));
		return escolasDestinacaoLixo.stream()
				.map(EscolaInfraestruturaDTO::new)
				.collect(Collectors.toList());
	}

    @Transactional
    public EscolaInfraestruturaDTO salvar(EscolaInfraestruturaDTO dto) {
        EscolaInfraestrutura escolaInfraestrutura = criarEscolaInfraestruturaAPartirDTO(dto);
        escolaInfraestrutura = repository.save(escolaInfraestrutura);
        return new EscolaInfraestruturaDTO(escolaInfraestrutura);
    }

    @Transactional
    public EscolaInfraestruturaDTO atualizar(EscolaInfraestruturaDTO dto) {
        EscolaInfraestrutura escolaInfraestrutura = repository.getReferenceById(dto.getIdEscolaInfraestrutura());
        atualizarDados(escolaInfraestrutura, dto);
        return new EscolaInfraestruturaDTO(escolaInfraestrutura);
    }

    @Transactional
    public void remover(Long id) {
        repository.deleteById(id);
    }

    private EscolaInfraestrutura criarEscolaInfraestruturaAPartirDTO(EscolaInfraestruturaDTO dto) {
        EscolaInfraestrutura escolaInfraestrutura = new EscolaInfraestrutura();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        escolaInfraestrutura.setEscola(escola);
        escolaInfraestrutura.setEscolaAcessivel(dto.getEscolaAcessivel());
        escolaInfraestrutura.setDependenciasAcessiveis(dto.getDependenciasAcessiveis());
        escolaInfraestrutura.setSanitariosAcessiveis(dto.getSanitariosAcessiveis());
        escolaInfraestrutura.setAlimentacaoFornecida(dto.getAlimentacaoFornecida());
        escolaInfraestrutura.setAguaFiltrada(dto.getAguaFiltrada());
        escolaInfraestrutura.setSanitarioDentroEscola(dto.getSanitarioDentroEscola());
        escolaInfraestrutura.setBiblioteca(dto.getBiblioteca());
        escolaInfraestrutura.setCozinha(dto.getCozinha());
        escolaInfraestrutura.setLabInformatica(dto.getLabInformatica());
        escolaInfraestrutura.setLabCiencias(dto.getLabCiencias());
        escolaInfraestrutura.setSalaLeitura(dto.getSalaLeitura());
        escolaInfraestrutura.setQuadraEsportes(dto.getQuadraEsportes());
        escolaInfraestrutura.setSalaDiretoria(dto.getSalaDiretoria());
        escolaInfraestrutura.setSalaProfessores(dto.getSalaProfessores());
        escolaInfraestrutura.setSalaAtendimentoEspecial(dto.getSalaAtendimentoEspecial());
        escolaInfraestrutura.setInternet(dto.getInternet());
        escolaInfraestrutura.setBandaLarga(dto.getBandaLarga());
        return escolaInfraestrutura;
    }

    private void atualizarDados(EscolaInfraestrutura destino, EscolaInfraestruturaDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        destino.setEscolaAcessivel(origem.getEscolaAcessivel());
        destino.setDependenciasAcessiveis(origem.getDependenciasAcessiveis());
        destino.setSanitariosAcessiveis(origem.getSanitariosAcessiveis());
        destino.setAlimentacaoFornecida(origem.getAlimentacaoFornecida());
        destino.setAguaFiltrada(origem.getAguaFiltrada());
        destino.setSanitarioDentroEscola(origem.getSanitarioDentroEscola());
        destino.setBiblioteca(origem.getBiblioteca());
        destino.setCozinha(origem.getCozinha());
        destino.setLabInformatica(origem.getLabInformatica());
        destino.setLabCiencias(origem.getLabCiencias());
        destino.setSalaLeitura(origem.getSalaLeitura());
        destino.setQuadraEsportes(origem.getQuadraEsportes());
        destino.setSalaDiretoria(origem.getSalaDiretoria());
        destino.setSalaProfessores(origem.getSalaProfessores());
        destino.setSalaAtendimentoEspecial(origem.getSalaAtendimentoEspecial());
        destino.setInternet(origem.getInternet());
        destino.setBandaLarga(origem.getBandaLarga());
    }
}