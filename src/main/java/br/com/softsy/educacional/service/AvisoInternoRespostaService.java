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

import br.com.softsy.educacional.dto.AvisoInternoRespostaDTO;
import br.com.softsy.educacional.dto.CadastroAvisoInternoRespostaDTO;
import br.com.softsy.educacional.model.AvisoInterno;
import br.com.softsy.educacional.model.AvisoInternoResposta;
import br.com.softsy.educacional.model.Professor;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.repository.AvisoInternoRepository;
import br.com.softsy.educacional.repository.AvisoInternoRespostaRepository;
import br.com.softsy.educacional.repository.ProfessorRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;
import br.com.softsy.educacional.utils.ImageManager;

@Service
public class AvisoInternoRespostaService {

    @Autowired
    private AvisoInternoRespostaRepository repository;

    @Autowired
    private AvisoInternoRepository avisoInternoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional(readOnly = true)
    public List<AvisoInternoRespostaDTO> listarTudo() {
        List<AvisoInternoResposta> respostas = repository.findAll();
        return respostas.stream()
                .map(AvisoInternoRespostaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AvisoInternoRespostaDTO buscarPorId(Long id) {
        return new AvisoInternoRespostaDTO(repository.getReferenceById(id));
    }

    public String getAnexoById(Long idAvisoInternoResposta) throws IOException {
        Optional<AvisoInternoResposta> respostaOpcional = repository.findById(idAvisoInternoResposta);

        if (respostaOpcional.isPresent()) {
            return ImageManager.buscaImagem(respostaOpcional.get().getPathAnexo());
        } else {
            return null;
        }
    }

    @Transactional
    public CadastroAvisoInternoRespostaDTO salvar(CadastroAvisoInternoRespostaDTO dto) throws IOException {
        String base64 = "";
        AvisoInternoResposta avisoInternoResposta = criarAvisoInternoRespostaAPartirDTO(dto);

        base64 = avisoInternoResposta.getPathAnexo();
        avisoInternoResposta.setPathAnexo(null);
        avisoInternoResposta = repository.save(avisoInternoResposta);

        String caminhoIMG = ImageManager.salvaImagemAvisoInternoResposta(base64, avisoInternoResposta.getIdAvisoInternoResposta(), "anexoAvisoInternoResposta");
        avisoInternoResposta.setPathAnexo(caminhoIMG);
        dto.setPathAnexo(caminhoIMG);
        dto.setIdAvisoInternoResposta(avisoInternoResposta.getIdAvisoInternoResposta());

        atualizaDados(avisoInternoResposta, dto);

        return new CadastroAvisoInternoRespostaDTO(avisoInternoResposta);
    }

    private AvisoInternoResposta criarAvisoInternoRespostaAPartirDTO(CadastroAvisoInternoRespostaDTO dto) {
        AvisoInternoResposta avisoInternoResposta = new AvisoInternoResposta();

        BeanUtils.copyProperties(dto, avisoInternoResposta, "idAvisoInternoResposta", "dataCadastro");

        AvisoInterno avisoInterno = avisoInternoRepository.findById(dto.getAvisoInternoId())
                .orElseThrow(() -> new IllegalArgumentException("Aviso Interno não encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Professor professor = dto.getProfessorId() != null
                ? professorRepository.findById(dto.getProfessorId())
                    .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"))
                : null;

        avisoInternoResposta.setDataCadastro(LocalDateTime.now());
        avisoInternoResposta.setAvisoInterno(avisoInterno);
        avisoInternoResposta.setUsuario(usuario);
        avisoInternoResposta.setProfessor(professor);

        return avisoInternoResposta;
    }

    @Transactional
    public AvisoInternoRespostaDTO atualizar(CadastroAvisoInternoRespostaDTO dto) {
        AvisoInternoResposta avisoInternoResposta = repository.getReferenceById(dto.getIdAvisoInternoResposta());
        atualizaDados(avisoInternoResposta, dto);
        return new AvisoInternoRespostaDTO(avisoInternoResposta);
    }

    private void atualizaDados(AvisoInternoResposta destino, CadastroAvisoInternoRespostaDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idAvisoInternoResposta", "dataCadastro");

        AvisoInterno avisoInterno = avisoInternoRepository.findById(origem.getAvisoInternoId())
                .orElseThrow(() -> new IllegalArgumentException("Aviso Interno não encontrado"));

        Usuario usuario = usuarioRepository.findById(origem.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
		
		Professor professor = professorRepository.findById(origem.getProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));


        destino.setAvisoInterno(avisoInterno);
        destino.setUsuario(usuario);
        destino.setProfessor(professor);
        destino.setPathAnexo(origem.getPathAnexo());
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
