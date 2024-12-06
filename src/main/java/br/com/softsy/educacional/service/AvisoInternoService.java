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

import br.com.softsy.educacional.dto.AvisoInternoDTO;
import br.com.softsy.educacional.dto.CadastroAvisoInternoDTO;
import br.com.softsy.educacional.model.AvisoInterno;
import br.com.softsy.educacional.model.AvisoInternoDestinatario;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Professor;
import br.com.softsy.educacional.model.TipoAviso;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.repository.AvisoInternoDestinatarioRepository;
import br.com.softsy.educacional.repository.AvisoInternoRepository;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.ProfessorRepository;
import br.com.softsy.educacional.repository.TipoAvisoRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;
import br.com.softsy.educacional.utils.ImageManager;

@Service
public class AvisoInternoService {

    @Autowired
    private AvisoInternoRepository avisoInternoRepository;

    @Autowired
    private TipoAvisoRepository tipoAvisoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProfessorRepository professorRepository;
    
    @Autowired
	 private ContaRepository contaRepository;
    
    @Autowired
	private AvisoInternoDestinatarioRepository avisoInternoDestinatarioRepository;

    @Transactional(readOnly = true)
    public List<AvisoInternoDTO> listarTudo() {
        List<AvisoInterno> avisos = avisoInternoRepository.findAll();
        return avisos.stream()
                .map(AvisoInternoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AvisoInternoDTO buscarPorId(Long id) {
        return new AvisoInternoDTO(avisoInternoRepository.getReferenceById(id));
    }

    public String getLogoById(Long idAvisoInterno) throws IOException {
        Optional<AvisoInterno> avisoInternoOpcional = avisoInternoRepository.findById(idAvisoInterno);

        String imagemCarregada = null;
        if (avisoInternoOpcional.isPresent()) {
            imagemCarregada = ImageManager.buscaImagem(avisoInternoOpcional.get().getPathAnexo());
        }
        return imagemCarregada;
    }
    
    @Transactional(readOnly = true)
    public List<AvisoInternoDTO> buscarPorIdProfessor(Long idProfessor) {
        List<AvisoInterno> avisoInterno = avisoInternoRepository.findByProfessor_IdProfessor(idProfessor)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar aviso por ID do professor"));
        return avisoInterno.stream()
                .map(AvisoInternoDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AvisoInternoDTO> buscarPorIdConta(Long idConta) {
        List<AvisoInterno> avisoInterno = avisoInternoRepository.findByConta_IdConta(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar tipo de aviso por ID da conta"));
        return avisoInterno.stream()
                .map(AvisoInternoDTO::new)
                .collect(Collectors.toList());
   }

    @Transactional
    public CadastroAvisoInternoDTO salvar(CadastroAvisoInternoDTO dto) throws IOException {
       
        AvisoInterno avisoInterno = criarAvisoInternoAPartirDTO(dto);

        String base64 = avisoInterno.getPathAnexo();
        avisoInterno.setPathAnexo(null);

        avisoInterno = avisoInternoRepository.save(avisoInterno);
        if (avisoInterno.getIdAvisoInterno() == null) {
            throw new IllegalStateException("Erro ao salvar o aviso interno: ID não foi gerado.");
        }

        if (base64 != null && !base64.isEmpty()) {
            String caminhoIMG = ImageManager.salvaImagemAvisoInterno(
                base64, 
                avisoInterno.getIdAvisoInterno(),
                "anexoAvisoInterno" + dto.getTipoAvisoId()
            );

            avisoInterno.setPathAnexo(caminhoIMG);
            avisoInterno = avisoInternoRepository.save(avisoInterno);

            dto.setPathAnexo(caminhoIMG);
            dto.setIdAvisoInterno(avisoInterno.getIdAvisoInterno());
        }

        // Salva os destinatários do tipo "Usuário"
        if (dto.getDestinatarioUsuario() != null && !dto.getDestinatarioUsuario().isEmpty()) {
            List<Usuario> usuarios = usuarioRepository.findAllById(dto.getDestinatarioUsuario());
            for (Usuario usuario : usuarios) {
                AvisoInternoDestinatario avisoDestinatario = new AvisoInternoDestinatario();
                avisoDestinatario.setAvisoInterno(avisoInterno);
                avisoDestinatario.setUsuarioDestinatario(usuario);
                avisoDestinatario.setDataCadastro(LocalDateTime.now());

                avisoInternoDestinatarioRepository.save(avisoDestinatario);
            }
        }
        // Salva os destinatários do tipo "Professor"
        if (dto.getDestinatarioProfessor() != null && !dto.getDestinatarioProfessor().isEmpty()) {
            List<Professor> professores = professorRepository.findAllById(dto.getDestinatarioProfessor());
            for (Professor professor : professores) {
                AvisoInternoDestinatario avisoDestinatario = new AvisoInternoDestinatario();
                avisoDestinatario.setAvisoInterno(avisoInterno);
                avisoDestinatario.setProfessorDestinatario(professor);
                avisoDestinatario.setDataCadastro(LocalDateTime.now());

                avisoInternoDestinatarioRepository.save(avisoDestinatario);
            }
        }

        return new CadastroAvisoInternoDTO(avisoInterno);
    }


    private AvisoInterno criarAvisoInternoAPartirDTO(CadastroAvisoInternoDTO dto) {
        AvisoInterno avisoInterno = new AvisoInterno();
        BeanUtils.copyProperties(dto, avisoInterno, "idAvisoInterno", "dataCadastro");

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
		 avisoInterno.setConta(conta);

			
		if(dto.getUsuarioId() != null ) {
			Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
	                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
			avisoInterno.setUsuario(usuario);
		}
		
		else {
			Professor professor = professorRepository.findById(dto.getProfessorId())
	                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
			avisoInterno.setProfessor(professor);
		}
		
        avisoInterno.setDataCadastro(LocalDateTime.now());
        avisoInterno.setTipoAviso(tipoAviso);

        return avisoInterno;
    }
    
  

    @Transactional
    public AvisoInternoDTO atualizar(CadastroAvisoInternoDTO dto) {
        AvisoInterno avisoInterno = avisoInternoRepository.getReferenceById(dto.getIdAvisoInterno());
        atualizaDados(avisoInterno, dto);
        return new AvisoInternoDTO(avisoInterno);
    }

    @Transactional
    public AvisoInternoDTO alterarImagemAvisoInterno(Long idAvisoInterno, String novaImagemBase64) throws IOException {
        AvisoInterno avisoInterno = avisoInternoRepository.findById(idAvisoInterno)
                .orElseThrow(() -> new IllegalArgumentException("Aviso Interno não encontrado"));

        if (avisoInterno.getPathAnexo() != null) {
            File imagemExistente = new File(avisoInterno.getPathAnexo());
            if (imagemExistente.exists()) {
                imagemExistente.delete();
            }
        }

        String novoCaminhoIMG = ImageManager.salvaImagemConta(novaImagemBase64, idAvisoInterno, "avisoInterno" + avisoInterno.getTitulo());
        avisoInterno.setPathAnexo(novoCaminhoIMG);
        avisoInternoRepository.save(avisoInterno);

        AvisoInternoDTO avisoInternoAtualizado = new AvisoInternoDTO(avisoInterno);
        return avisoInternoAtualizado;
    }

    private void atualizaDados(AvisoInterno destino, CadastroAvisoInternoDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idAvisoInterno", "dataCadastro");
        
        TipoAviso tipoAviso = tipoAvisoRepository.findById(origem.getTipoAvisoId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo do aviso não encontrado"));
        
        if (origem.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(origem.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
            destino.setUsuario(usuario);
        } else {
            destino.setUsuario(null); 
        }
 
        if (origem.getProfessorId() != null) {
            Professor professor = professorRepository.findById(origem.getProfessorId())
                    .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
            destino.setProfessor(professor);
        } else {
            destino.setProfessor(null); 
        }
        
        destino.setTipoAviso(tipoAviso);
        destino.setPathAnexo(origem.getPathAnexo());
    }
    
    
    @Transactional
    public void excluir(Long id) {
        avisoInternoRepository.deleteById(id);
    }
}
