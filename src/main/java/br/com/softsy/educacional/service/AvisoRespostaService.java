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
import br.com.softsy.educacional.dto.AvisoRespostaDTO;
import br.com.softsy.educacional.dto.CadastroAvisoDTO;
import br.com.softsy.educacional.dto.CadastroAvisoRespostaDTO;
import br.com.softsy.educacional.model.Aluno;
import br.com.softsy.educacional.model.Aviso;
import br.com.softsy.educacional.model.AvisoResposta;
import br.com.softsy.educacional.model.Professor;
import br.com.softsy.educacional.model.TipoAviso;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.repository.AlunoRepository;
import br.com.softsy.educacional.repository.AvisoRepository;
import br.com.softsy.educacional.repository.AvisoRespostaRepository;
import br.com.softsy.educacional.repository.ProfessorRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;
import br.com.softsy.educacional.utils.ImageManager;

@Service
public class AvisoRespostaService {

    @Autowired
    private AvisoRespostaRepository repository;
    
    @Autowired
    private AvisoRepository avisoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional(readOnly = true)
    public List<AvisoRespostaDTO> listarTudo() {
        List<AvisoResposta> respostas = repository.findAll();
        return respostas.stream()
                .map(AvisoRespostaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AvisoRespostaDTO buscarPorId(Long id) {
        return new AvisoRespostaDTO(repository.getReferenceById(id));
    }

    public String getAnexoById(Long idAvisoResposta) throws IOException {
        Optional<AvisoResposta> respostaOpcional = repository.findById(idAvisoResposta);

        if (respostaOpcional.isPresent()) {
            return ImageManager.buscaImagem(respostaOpcional.get().getPathAnexo());
        } else {
            return null;
        }
    }

    @Transactional
    public CadastroAvisoRespostaDTO salvar(CadastroAvisoRespostaDTO dto) throws IOException {
    	
    	
        String base64 = "";
        AvisoResposta avisoResposta = criarAvisoRespostaAPartirDTO(dto);

        base64 = avisoResposta.getPathAnexo();
        avisoResposta.setPathAnexo(null);
        avisoResposta = repository.save(avisoResposta);

        // Ajuste para quando TipoAvisoId não estiver disponível, usando "aviso" como identificador
        String tipoAvisoId = (dto.getAvisoId() != null) ? dto.getAvisoId().toString() : "default"; // Usando o ID do aviso como tipo de identificação

        String caminhoIMG = ImageManager.salvaImagemAvisoResposta(base64, avisoResposta.getIdAvisoResposta(), "anexoAvisoResposta" + tipoAvisoId);

        avisoResposta.setPathAnexo(caminhoIMG);
        dto.setPathAnexo(caminhoIMG);
        dto.setIdAvisoResposta(avisoResposta.getIdAvisoResposta());

        atualizaDados(avisoResposta, dto);

        CadastroAvisoRespostaDTO avisoRespostaCriado = new CadastroAvisoRespostaDTO(avisoResposta);
        return avisoRespostaCriado;
    }



    private AvisoResposta criarAvisoRespostaAPartirDTO(CadastroAvisoRespostaDTO dto) {
        AvisoResposta avisoResposta = new AvisoResposta();
        
        System.out.println("Aviso ID: " + dto.getAvisoId());
        System.out.println("Aluno ID: " + dto.getAlunoId());
        System.out.println("Usuario ID: " + dto.getUsuarioId());
        System.out.println("Professor ID: " + dto.getProfessorId());
        
        BeanUtils.copyProperties(dto, avisoResposta, "idAvisoResposta", "dataCadastro");
       
        Aviso aviso = avisoRepository.findById(dto.getAvisoId())
                .orElseThrow(() -> new IllegalArgumentException("Aviso não encontrado"));

        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
		
		Professor professor = professorRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));

        avisoResposta.setDataCadastro(LocalDateTime.now());
        avisoResposta.setAviso(aviso);
        avisoResposta.setAluno(aluno);
        avisoResposta.setUsuario(usuario);
        avisoResposta.setProfessor(professor);
        return avisoResposta;
    }

    @Transactional
    public AvisoRespostaDTO atualizar(CadastroAvisoRespostaDTO dto) {
        AvisoResposta avisoResposta = repository.getReferenceById(dto.getIdAvisoResposta());
        atualizaDados(avisoResposta, dto);
        return new AvisoRespostaDTO(avisoResposta);
    }

    private void atualizaDados(AvisoResposta destino, CadastroAvisoRespostaDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idAvisoResposta", "dataCadastro");
        
        Aviso aviso = avisoRepository.findById(origem.getAvisoId())
                .orElseThrow(() -> new IllegalArgumentException("Aviso não encontrado"));

        Aluno aluno = alunoRepository.findById(origem.getAlunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        Usuario usuario = usuarioRepository.findById(origem.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
		
		Professor professor = professorRepository.findById(origem.getProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));

        destino.setAviso(aviso);
        destino.setAluno(aluno);
        destino.setUsuario(usuario);
        destino.setProfessor(professor);
        destino.setPathAnexo(origem.getPathAnexo());
    }
    
  
    
    @Transactional
    public AvisoRespostaDTO alterarAnexo(Long idAvisoResposta, String novaImagemBase64) throws IOException {
        AvisoResposta avisoResposta = repository.findById(idAvisoResposta)
                .orElseThrow(() -> new IllegalArgumentException("Resposta não encontrada"));

        if (avisoResposta.getPathAnexo() != null) {
            File imagemExistente = new File(avisoResposta.getPathAnexo());
            if (imagemExistente.exists()) {
                imagemExistente.delete();
            }
        }

        String novoCaminhoAnexo = ImageManager.salvaImagemConta(novaImagemBase64, idAvisoResposta, "resposta");
        avisoResposta.setPathAnexo(novoCaminhoAnexo);
        repository.save(avisoResposta);

        return new AvisoRespostaDTO(avisoResposta);
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
