package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroProvaDTO;
import br.com.softsy.educacional.dto.ProvaDTO;
import br.com.softsy.educacional.model.Prova;
import br.com.softsy.educacional.model.Turma;
import br.com.softsy.educacional.repository.ProvaRepository;
import br.com.softsy.educacional.repository.TurmaRepository;

@Service
public class ProvaService {

	@Autowired
	private ProvaRepository repository;
	
	@Autowired
    private TurmaRepository turmaRepository;
	
	
	@Transactional(readOnly = true)
	 public List<ProvaDTO> listarTudo() {
	    List<Prova> prova = repository.findAll();
	     return prova.stream()
	     .map(ProvaDTO::new)
	     .collect(Collectors.toList());
	    }
	
	@Transactional(readOnly = true)
    public ProvaDTO buscarPorId(Long id) {
        return new ProvaDTO(repository.getReferenceById(id));
    }
		
	@Transactional
	public ProvaDTO salvar(CadastroProvaDTO dto) {
	    Prova prova = criarProvaAPartirDTO(dto);
	    prova = repository.save(prova);
	    return new ProvaDTO(prova);
	}

	private Prova criarProvaAPartirDTO(CadastroProvaDTO dto) {
	    Prova prova = new Prova();

	    Turma turma = turmaRepository.findById(dto.getTurmaId())  
	            .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));

	    BeanUtils.copyProperties(dto, prova, "idProva", "dataCadastro");

	    prova.setTurma(turma); 
	    prova.setDataCadastro(LocalDateTime.now()); 

	    return prova;
	}

	    @Transactional
	    public ProvaDTO atualizar(CadastroProvaDTO dto) {
	        Prova prova = repository.findById(dto.getIdProva())
	                .orElseThrow(() -> new IllegalArgumentException("Prova não encontrada"));

	        atualizaDados(prova, dto);

	        prova = repository.save(prova);

	        return new ProvaDTO(prova);
	    }

	    private void atualizaDados(Prova destino, CadastroProvaDTO origem) {
	        Turma turma = turmaRepository.findById(origem.getTurmaId())
	                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));

	        BeanUtils.copyProperties(origem, destino, "idProva", "dataCadastro");

	        destino.setTurma(turma);
	    }


	    @Transactional
	    public void ativaDesativa(Character status, Long idProva) {
	        Prova prova = repository.findById(idProva)
	                .orElseThrow(() -> new IllegalArgumentException("Prova não encontrada"));

	        prova.setAtivo(status);
	    }
	
}
