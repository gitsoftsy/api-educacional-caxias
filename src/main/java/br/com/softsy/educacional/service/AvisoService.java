package br.com.softsy.educacional.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Professor;
import br.com.softsy.educacional.model.TipoAviso;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.repository.AlunoRepository;
import br.com.softsy.educacional.repository.AvisoDestinatarioRepository;
import br.com.softsy.educacional.repository.AvisoRepository;
import br.com.softsy.educacional.repository.ContaRepository;
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
	 private ContaRepository contaRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private AvisoDestinatarioRepository avisoDestinatarioRepository;


	@Transactional(readOnly = true)
	public List<AvisoDTO> listarTudo() {
	    List<Aviso> avisos = repository.findAll();
	    return avisos.stream()
	            .map(aviso -> {
	                try {
	                    return new AvisoDTO(aviso);
	                } catch (NullPointerException e) {
	                    System.err.println("Erro ao processar aviso: " + aviso.getIdAviso());
	                    return null;
	                }
	            })
	            .filter(Objects::nonNull)
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
    
	   @Transactional(readOnly = true)
	    public List<AvisoDTO> buscarPorIdConta(Long idConta) {
	        List<Aviso> aviso = repository.findByConta_IdConta(idConta)
	                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar tipo de aviso por ID da conta"));
	        return aviso.stream()
	                .map(AvisoDTO::new)
	                .collect(Collectors.toList());
	   }
    
	
	   @Transactional
	   public CadastroAvisoDTO salvar(CadastroAvisoDTO dto) throws IOException {

	       Aviso aviso = criarAvisoAPartirDTO(dto);

	       List<Aluno> alunos = alunoRepository.findAllById(dto.getDestinatarios());

	       String base64 = aviso.getPathAnexo();
	       aviso.setPathAnexo(null);

	       aviso = repository.save(aviso);
	       if (aviso.getIdAviso() == null) {
	           throw new IllegalStateException("Erro ao salvar o aviso: ID não foi gerado.");
	       }

	 
	       if (base64 != null && !base64.isEmpty()) {
	           String caminhoIMG = ImageManager.salvaImagemAviso(base64, aviso.getIdAviso(),
	                   "anexoAviso" + dto.getTipoAvisoId());

	           aviso.setPathAnexo(caminhoIMG);
	           aviso = repository.save(aviso);

	          
	           dto.setPathAnexo(caminhoIMG);
	           dto.setIdAviso(aviso.getIdAviso());
	       }

	       // Criar e salvar os destinatários do aviso
	       for (Aluno aluno : alunos) {
	           AvisoDestinatario avisoDestinatario = new AvisoDestinatario();
	           avisoDestinatario.setAviso(aviso);
	           avisoDestinatario.setAluno(aluno);
	           avisoDestinatario.setDataCadastro(LocalDateTime.now());

	           avisoDestinatarioRepository.save(avisoDestinatario);
	       }

	       return new CadastroAvisoDTO(aviso);
	   }

	
	private Aviso criarAvisoAPartirDTO(CadastroAvisoDTO dto) {
		Aviso aviso = new Aviso();
		BeanUtils.copyProperties(dto, aviso, "idAviso", "dataCadastro");
		
		
		
		if(dto.getUsuarioId() == null && dto.getProfessorId() == null) {
			throw new IllegalArgumentException("Pelo menos um dos campos usuarioId ou professorId deve ser preenchido");
		}
		if(dto.getUsuarioId() != null && dto.getProfessorId() != null) {
			throw new IllegalArgumentException("Informe apenas um dos campos, usuarioId ou professorId.");
		}
		
		
		
		TipoAviso tipoAviso = tipoAvisoRepository.findById(dto.getTipoAvisoId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo do aviso não encontrado"));
		
		 Conta conta = contaRepository.findById(dto.getContaId())
	                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
		 aviso.setConta(conta);

			
		if(dto.getUsuarioId() != null ) {
			Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
	                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
			aviso.setUsuario(usuario);
		}
		
		else {
			Professor professor = professorRepository.findById(dto.getProfessorId())
	                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
			aviso.setProfessor(professor);
		}
		
		
		aviso.setDataCadastro(LocalDateTime.now());
		aviso.setTipoAviso(tipoAviso);
	
	
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
