package br.com.softsy.educacional.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.AvisoInternoDestinatarioDTO;
import br.com.softsy.educacional.dto.CadastroAvisoDestinatarioDTO;
import br.com.softsy.educacional.dto.CadastroAvisoInternoDestinatarioDTO;
import br.com.softsy.educacional.model.AvisoDestinatario;
import br.com.softsy.educacional.model.AvisoInternoDestinatario;
import br.com.softsy.educacional.repository.AvisoInternoDestinatarioRepository;
import br.com.softsy.educacional.repository.AvisoInternoRepository;
import br.com.softsy.educacional.repository.ProfessorRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;

@Service
public class AvisoInternoDestinatarioService {
	
	@Autowired
	private AvisoInternoDestinatarioRepository repository;
	
	@Autowired
	private AvisoInternoRepository avisoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
    @Autowired
    private EntityManager entityManager;
    
    @Transactional(readOnly = true)
    public List<AvisoInternoDestinatarioDTO> listarTudo() {
        List<AvisoInternoDestinatario> avisoDestinatario = repository.findAll();
        return avisoDestinatario.stream()
                .map(AvisoInternoDestinatarioDTO::new)
                .collect(Collectors.toList());
    }
	
    @Transactional(readOnly = true)
	public AvisoInternoDestinatarioDTO buscarPorId(Long id) {
		return new AvisoInternoDestinatarioDTO(repository.getReferenceById(id));
	}
    
//    @Transactional(readOnly = true)
//    public List<AvisoInternoDestinatarioDTO> buscarPorIdUsuario(Long idUsuario) {
//        List<AvisoInternoDestinatario> avisoInternoDestinatario = repository.findByUsuario_IdUsuario(idUsuario)
//                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar destinatário do aviso por ID do usuario"));
//        return avisoInternoDestinatario.stream()
//                .map(AvisoInternoDestinatarioDTO::new)
//                .collect(Collectors.toList());
//    }
//    
//    @Transactional(readOnly = true)
//    public List<AvisoInternoDestinatarioDTO> buscarPorIdProfessor(Long idProfessor) {
//        List<AvisoInternoDestinatario> avisoInternoDestinatario = repository.findByProfessor_IdProfessor(idProfessor)
//                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar destinatário do aviso por ID do professor"));
//        return avisoInternoDestinatario.stream()
//                .map(AvisoInternoDestinatarioDTO::new)
//                .collect(Collectors.toList());
//    }
    
//    @Transactional
//    public CadastroAvisoInternoDestinatarioDTO salvar(CadastroAvisoInternoDestinatarioDTO dto) {
//    	AvisoInternoDestinatario avisoInternoDestinatario = criarAvisoInternoDestinatarioAPartirDTO(dto); 
//    	avisoInternoDestinatario = repository.save(avisoInternoDestinatario);
//        return new CadastroAvisoDestinatarioDTO(avisoInternoDestinatario);
//    }
//    
    
    
    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

}
