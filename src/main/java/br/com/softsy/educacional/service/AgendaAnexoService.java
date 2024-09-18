package br.com.softsy.educacional.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.AgendaAnexoDTO;
import br.com.softsy.educacional.dto.CandidatoDTO;
import br.com.softsy.educacional.model.Agenda;
import br.com.softsy.educacional.model.AgendaAnexo;
import br.com.softsy.educacional.model.Candidato;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.repository.AgendaAnexoRepository;
import br.com.softsy.educacional.repository.AgendaRepository;
import br.com.softsy.educacional.utils.ImageManager;

@Service
public class AgendaAnexoService {

    @Autowired
    private AgendaAnexoRepository repository;

    @Autowired
    private AgendaRepository agendaRepository;

    @Transactional(readOnly = true)
    public List<AgendaAnexoDTO> listarTudo() {
        List<AgendaAnexo> anexos = repository.findAll();
        return anexos.stream()
                .map(AgendaAnexoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AgendaAnexoDTO buscarPorId(Long id) {
        return new AgendaAnexoDTO(repository.getReferenceById(id));
    }
    
	public String getLogoById(Long idAgendaAnexo, String caminho) throws IOException {
		Optional<AgendaAnexo> agendaOptional = repository.findById(idAgendaAnexo);

		String imagemCarregada;
		imagemCarregada = ImageManager.buscaImagem(caminho);

		if (agendaOptional.isPresent()) {
			return imagemCarregada;
		} else {
			return null;
		}
	}
	
    @Transactional(readOnly = true)
    public List<AgendaAnexoDTO> buscarPorIdAgenda(Long idAgenda) {
        List<AgendaAnexo> curso = repository.findByAgenda_IdAgenda(idAgenda)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar agendaAnexo por ID da agenda"));
        return curso.stream()
                .map(AgendaAnexoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public AgendaAnexoDTO salvar(AgendaAnexoDTO dto) {
    	
    	String base64 = "";
        AgendaAnexo anexo = criarAnexoAPartirDTO(dto);
        
        base64 = anexo.getCaminhoArquivo();
        
        anexo.setCaminhoArquivo(null);
        anexo = repository.save(anexo);
        
        String caminhoIMG = ImageManager.salvaImagemAgenda(base64, anexo.getIdAgendaAnexo(),"ANEXO" + anexo.getIdAgendaAnexo() );
        
        anexo.setCaminhoArquivo(caminhoIMG);
        dto.setCaminhoArquivo(caminhoIMG);
        dto.setIdAgendaAnexo(anexo.getIdAgendaAnexo());
        
        atualizaDados(anexo, dto);
        
        AgendaAnexoDTO anexoCriado = new AgendaAnexoDTO(anexo);
        
        return anexoCriado;
        
    }
    
    @Transactional
    public AgendaAnexoDTO atualizar(AgendaAnexoDTO dto) {
        AgendaAnexo anexo = repository.getReferenceById(dto.getIdAgendaAnexo());
        
        anexo = repository.save(anexo);
        
        String caminhoIMG = ImageManager.atualizaImagemAgenda(dto.getIdAgendaAnexo(), dto.getCaminhoArquivo());

        anexo.setCaminhoArquivo(caminhoIMG);
        dto.setCaminhoArquivo(caminhoIMG);
        dto.setIdAgendaAnexo(anexo.getIdAgendaAnexo());
        
        atualizaDados(anexo, dto);
        return new AgendaAnexoDTO(anexo);
    }

    private AgendaAnexo criarAnexoAPartirDTO(AgendaAnexoDTO dto) {
        AgendaAnexo anexo = new AgendaAnexo();
        Agenda agenda = agendaRepository.findById(dto.getAgendaId())
                .orElseThrow(() -> new IllegalArgumentException("Agenda não encontrada"));
        BeanUtils.copyProperties(dto, anexo, "idAgendaAnexo", "dataCadastro", "ativo");
        anexo.setAgenda(agenda);
        anexo.setAtivo('S');
        anexo.setDataCadastro(LocalDateTime.now());
        return anexo;
    }

    private void atualizaDados(AgendaAnexo destino, AgendaAnexoDTO origem) {
        destino.setAgenda(agendaRepository.findById(origem.getAgendaId())
                .orElseThrow(() -> new IllegalArgumentException("Agenda não encontrada")));
        BeanUtils.copyProperties(origem, destino, "idAgendaAnexo", "ativo", "dataCadastro");
    }

    @Transactional
    public void ativaDesativa(char status, Long id) {
        AgendaAnexo anexo = repository.getReferenceById(id);
        anexo.setAtivo(status);
    }
}