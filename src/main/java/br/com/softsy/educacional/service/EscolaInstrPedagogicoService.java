package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroEscolaInstrPedagogicoDTO;
import br.com.softsy.educacional.dto.EscolaInstrPedagogicoDTO;
import br.com.softsy.educacional.model.Escola;
import br.com.softsy.educacional.model.EscolaInstrPedagogico;
import br.com.softsy.educacional.model.InstrPedagogico;
import br.com.softsy.educacional.model.Localizacao;
import br.com.softsy.educacional.repository.EscolaInstrPedagogicoRepository;
import br.com.softsy.educacional.repository.EscolaRepository;
import br.com.softsy.educacional.repository.InstrPedagogicoRepository;

@Service
public class EscolaInstrPedagogicoService {

    @Autowired
    private EscolaInstrPedagogicoRepository repository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private InstrPedagogicoRepository instrPedagogicoRepository;

    @Transactional(readOnly = true)
    public List<EscolaInstrPedagogicoDTO> listarTudo() {
        List<EscolaInstrPedagogico> escolasInstrPedagogico = repository.findAll();
        return escolasInstrPedagogico.stream()
                .map(EscolaInstrPedagogicoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EscolaInstrPedagogicoDTO> buscarPorIdEscola(Long id) {
        List<EscolaInstrPedagogico> escolasInstrPedagogico = repository.findByEscola_IdEscola(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar instrução pedagógica por id de escola"));
        return escolasInstrPedagogico.stream()
                .map(EscolaInstrPedagogicoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public EscolaInstrPedagogicoDTO salvar(CadastroEscolaInstrPedagogicoDTO dto) {
        EscolaInstrPedagogico escolaInstrPedagogico = criarEscolaInstrPedagogicoAPartirDTO(dto);
        escolaInstrPedagogico = repository.save(escolaInstrPedagogico);
        return new EscolaInstrPedagogicoDTO(escolaInstrPedagogico);
    }

    @Transactional
    public EscolaInstrPedagogicoDTO atualizar(CadastroEscolaInstrPedagogicoDTO dto) {
        EscolaInstrPedagogico escolaInstrPedagogico = repository.getReferenceById(dto.getIdEscolaInstrPedagogico());
        atualizaDados(escolaInstrPedagogico, dto);
        return new EscolaInstrPedagogicoDTO(escolaInstrPedagogico);
    }

	@Transactional
	public void ativaDesativa(char status, Long idEscolaInstrPedagogico) {
		EscolaInstrPedagogico escolaInstrPedagogico = repository.getReferenceById(idEscolaInstrPedagogico);
		escolaInstrPedagogico.setAtivo(status);
	}
	

    private EscolaInstrPedagogico criarEscolaInstrPedagogicoAPartirDTO(CadastroEscolaInstrPedagogicoDTO dto) {
        EscolaInstrPedagogico escolaInstrPedagogico = new EscolaInstrPedagogico();
        Escola escola = escolaRepository.findById(dto.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada"));
        InstrPedagogico instrPedagogico = instrPedagogicoRepository.findById(dto.getInstrPedagogicoId())
                .orElseThrow(() -> new IllegalArgumentException("Instrução pedagógica não encontrada"));
        escolaInstrPedagogico.setEscola(escola);
        escolaInstrPedagogico.setInstrPedagogico(instrPedagogico);
        escolaInstrPedagogico.setDataCadastro(LocalDateTime.now());
        escolaInstrPedagogico.setAtivo('S');
        return escolaInstrPedagogico;
    }

    private void atualizaDados(EscolaInstrPedagogico destino, CadastroEscolaInstrPedagogicoDTO origem) {
        destino.setEscola(escolaRepository.findById(origem.getEscolaId())
                .orElseThrow(() -> new IllegalArgumentException("Escola não encontrada")));
        destino.setInstrPedagogico(instrPedagogicoRepository.findById(origem.getInstrPedagogicoId())
                .orElseThrow(() -> new IllegalArgumentException("Instrução pedagógica não encontrada")));
    }
}
