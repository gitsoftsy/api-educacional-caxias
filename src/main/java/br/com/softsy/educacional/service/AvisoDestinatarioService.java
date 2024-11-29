package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.AvisoDestinatarioDTO;
import br.com.softsy.educacional.dto.CadastroAvisoDestinatarioDTO;
import br.com.softsy.educacional.model.Aluno;
import br.com.softsy.educacional.model.Aviso;
import br.com.softsy.educacional.model.AvisoDestinatario;
import br.com.softsy.educacional.repository.AlunoRepository;
import br.com.softsy.educacional.repository.AvisoDestinatarioRepository;
import br.com.softsy.educacional.repository.AvisoRepository;

@Service
public class AvisoDestinatarioService {

	@Autowired
	private AvisoDestinatarioRepository repository;
	
	@Autowired
	private AvisoRepository avisoRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;
	
    @Transactional(readOnly = true)
    public List<AvisoDestinatarioDTO> listarTudo() {
        List<AvisoDestinatario> avisoDestinatario = repository.findAll();
        return avisoDestinatario.stream()
                .map(AvisoDestinatarioDTO::new)
                .collect(Collectors.toList());
    }
	
	@Transactional(readOnly = true)
	public AvisoDestinatarioDTO buscarPorId(Long id) {
		return new AvisoDestinatarioDTO(repository.getReferenceById(id));
	}
	
    @Transactional(readOnly = true)
    public List<AvisoDestinatarioDTO> buscarPorIdAluno(Long idAluno) {
        List<AvisoDestinatario> aviso = repository.findByAluno_IdAluno(idAluno)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar destinatário do aviso por ID do aluno"));
        return aviso.stream()
                .map(AvisoDestinatarioDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public CadastroAvisoDestinatarioDTO salvar(CadastroAvisoDestinatarioDTO dto) {
    	AvisoDestinatario avisoDestinatario = criarAvisoDestinatarioAPartirDTO(dto); 
    	avisoDestinatario = repository.save(avisoDestinatario);
        return new CadastroAvisoDestinatarioDTO(avisoDestinatario);
    }
    
	private AvisoDestinatario criarAvisoDestinatarioAPartirDTO(CadastroAvisoDestinatarioDTO dto) {
		AvisoDestinatario avisoDestinatario = new AvisoDestinatario();
		
		BeanUtils.copyProperties(dto, avisoDestinatario, "idAvisoDestinatario", "dataCadastro");
				
		Aviso aviso = avisoRepository.findById(dto.getAvisoId())
                .orElseThrow(() -> new IllegalArgumentException("Aviso não encontrado"));
		
		Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
	
		
		avisoDestinatario.setDataCadastro(LocalDateTime.now());
		avisoDestinatario.setAviso(aviso);
		avisoDestinatario.setAluno(aluno);
		avisoDestinatario.setDataLeitura(dto.getDataLeitura());
		return avisoDestinatario;
	}
	
	@Transactional
	public AvisoDestinatarioDTO atualizar(CadastroAvisoDestinatarioDTO dto) {
		AvisoDestinatario avisoDestinatario = repository.getReferenceById(dto.getIdAvisoDestinatario());
		atualizaDados(avisoDestinatario, dto);
		return new AvisoDestinatarioDTO(avisoDestinatario);
	}
	
	private void atualizaDados(AvisoDestinatario destino, CadastroAvisoDestinatarioDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idAvisoDestinatario", "dataCadastro");
		
		
		Aviso aviso = avisoRepository.findById(origem.getAvisoId())
                .orElseThrow(() -> new IllegalArgumentException("Aviso não encontrado"));
		
		Aluno aluno = alunoRepository.findById(origem.getAlunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
		
		
		destino.setAviso(aviso);
		destino.setAluno(aluno);
	}
	
    @Transactional
    public AvisoDestinatarioDTO atualizarDataLeitura(Long idAvisoDestinatario, LocalDateTime dataLeitura) {
    	AvisoDestinatario avisoDestinatario = repository.getReferenceById(idAvisoDestinatario);
    	avisoDestinatario.setDataLeitura(dataLeitura);
 
        repository.save(avisoDestinatario);
        return new AvisoDestinatarioDTO(avisoDestinatario);
    }
	
    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
	
}
