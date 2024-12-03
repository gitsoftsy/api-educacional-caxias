package br.com.softsy.educacional.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.AvisoDTO;
import br.com.softsy.educacional.dto.CadastroAvisoDTO;
import br.com.softsy.educacional.model.Aluno;
import br.com.softsy.educacional.model.Aviso;
import br.com.softsy.educacional.model.AvisoDestinatario;
import br.com.softsy.educacional.model.Professor;
import br.com.softsy.educacional.model.TipoAviso;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.repository.AlunoRepository;
import br.com.softsy.educacional.repository.AvisoDestinatarioRepository;
import br.com.softsy.educacional.repository.AvisoRepository;
import br.com.softsy.educacional.repository.ProfessorRepository;
import br.com.softsy.educacional.repository.TipoAvisoRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;
import br.com.softsy.educacional.utils.ImageManager;

@Service
public class AvisoService {
	
	@Autowired
	private AvisoRepository repository;
	
	@Autowired
	private TipoAvisoRepository tipoAvisoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private AvisoDestinatarioRepository avisoDestinatarioRepository;


    @Transactional(readOnly = true)
    public List<AvisoDTO> listarTudo() {
        List<Aviso> aviso = repository.findAll();
        return aviso.stream()
                .map(AvisoDTO::new)
                .collect(Collectors.toList());
    }
	
	@Transactional(readOnly = true)
	public AvisoDTO buscarPorId(Long id) {
		return new AvisoDTO(repository.getReferenceById(id));
	}
	
	public String getLogoById(Long idAviso) throws IOException {
		Optional<Aviso> avisoOpcional = repository.findById(idAviso);

		String imagemCarregada;
		imagemCarregada = ImageManager.buscaImagem(avisoOpcional.get().getPathAnexo());

		if (avisoOpcional.isPresent()) {
			return imagemCarregada;
		} else {
			return null;
		}
	}
	
    @Transactional(readOnly = true)
    public List<AvisoDTO> buscarPorIdProfessor(Long idProfessor) {
        List<Aviso> aviso = repository.findByProfessor_IdProfessor(idProfessor)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar aviso por ID do professor"));
        return aviso.stream()
                .map(AvisoDTO::new)
                .collect(Collectors.toList());
    }
    
	
    @Transactional
    public CadastroAvisoDTO salvar(CadastroAvisoDTO dto) throws IOException {

        String base64 = "";
        Aviso aviso = criarAvisoAPartirDTO(dto);

  
        List<Aluno> alunos = alunoRepository.findAllById(dto.getDestinatarios());

        base64 = aviso.getPathAnexo();

        aviso.setPathAnexo(null);
        aviso = repository.save(aviso); 

       
        String caminhoIMG = ImageManager.salvaImagemAviso(base64, aviso.getIdAviso(), "anexoAviso" + dto.getTipoAvisoId());
        aviso.setPathAnexo(caminhoIMG);
        dto.setPathAnexo(caminhoIMG);
        dto.setIdAviso(aviso.getIdAviso());

        atualizaDados(aviso, dto);

       
        for (Aluno aluno : alunos) {
            AvisoDestinatario avisoDestinatario = new AvisoDestinatario();
            avisoDestinatario.setAviso(aviso);
            avisoDestinatario.setAluno(aluno);  
          
            avisoDestinatario.setDataCadastro(LocalDateTime.now());

            avisoDestinatarioRepository.save(avisoDestinatario);  
        }

        // Criando o DTO com os dados atualizados
        CadastroAvisoDTO avisoCriado = new CadastroAvisoDTO(aviso);

        return avisoCriado;
    }

	
	private Aviso criarAvisoAPartirDTO(CadastroAvisoDTO dto) {
		Aviso aviso = new Aviso();
		BeanUtils.copyProperties(dto, aviso, "idAviso", "dataCadastro");
		
		if(dto.getUsuarioId() == null && dto.getProfessorId() == null) {
			throw new IllegalArgumentException("Pelo menos um dos campos usuarioId ou professorId deve ser preenchido");
		}
		
		
		TipoAviso tipoAviso = tipoAvisoRepository.findById(dto.getTipoAvisoId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo do aviso não encontrado"));
		
		Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
		
		Professor professor = professorRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
		
		aviso.setDataCadastro(LocalDateTime.now());
		aviso.setTipoAviso(tipoAviso);
		aviso.setUsuario(usuario);
		aviso.setProfessor(professor);
		return aviso;
	}
	
	@Transactional
	public AvisoDTO atualizar(CadastroAvisoDTO dto) {
		Aviso aviso = repository.getReferenceById(dto.getIdAviso());
		atualizaDados(aviso, dto);
		return new AvisoDTO(aviso);
	}
	
	@Transactional
	public AvisoDTO alterarImagemAviso(Long idAviso, String novaImagemBase64) throws IOException {
		Aviso aviso = repository.findById(idAviso).orElseThrow(() -> new IllegalArgumentException("Aviso não encontrado"));

	    if (aviso.getPathAnexo() != null) {
	        File imagemExistente = new File(aviso.getPathAnexo());
	        if (imagemExistente.exists()) {
	            imagemExistente.delete();
	        }
	    }

	    String novoCaminhoIMG = ImageManager.salvaImagemAviso(novaImagemBase64, idAviso, "aviso" + aviso.getTitulo());

	    aviso.setPathAnexo(novoCaminhoIMG);
	    repository.save(aviso);

	    AvisoDTO avisoAtualizado = new AvisoDTO(aviso);
	    return avisoAtualizado;
	}
	
	private void atualizaDados(Aviso destino, CadastroAvisoDTO origem) {
		BeanUtils.copyProperties(origem, destino, "idAviso", "dataCadastro");
		
		
		TipoAviso tipoAviso = tipoAvisoRepository.findById(origem.getTipoAvisoId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo do aviso não encontrado"));
		
		Usuario usuario = usuarioRepository.findById(origem.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
		
		Professor professor = professorRepository.findById(origem.getProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
		
		destino.setTipoAviso(tipoAviso);
		destino.setUsuario(usuario);
		destino.setProfessor(professor);
		destino.setPathAnexo(origem.getPathAnexo());
	}
	
    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
    

	
}
