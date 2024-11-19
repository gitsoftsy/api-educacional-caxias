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
import br.com.softsy.educacional.model.TipoAviso;
import br.com.softsy.educacional.repository.AvisoInternoRepository;
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

    @Transactional
    public CadastroAvisoInternoDTO salvar(CadastroAvisoInternoDTO dto) throws IOException {
        String base64 = "";
        AvisoInterno avisoInterno = criarAvisoInternoAPartirDTO(dto);

        base64 = avisoInterno.getPathAnexo();
        avisoInterno.setPathAnexo(null);
        avisoInterno = avisoInternoRepository.save(avisoInterno);

        String caminhoIMG = ImageManager.salvaImagemAvisoInterno(base64, avisoInterno.getIdAvisoInterno(), "anexoAviso" + dto.getTipoAvisoId());
        avisoInterno.setPathAnexo(caminhoIMG);
        dto.setPathAnexo(caminhoIMG);
        dto.setIdAvisoInterno(avisoInterno.getIdAvisoInterno());

        atualizaDados(avisoInterno, dto);

        CadastroAvisoInternoDTO avisoInternoCriado = new CadastroAvisoInternoDTO(avisoInterno);
        return avisoInternoCriado;
    }

    private AvisoInterno criarAvisoInternoAPartirDTO(CadastroAvisoInternoDTO dto) {
        AvisoInterno avisoInterno = new AvisoInterno();
        BeanUtils.copyProperties(dto, avisoInterno, "idAvisoInterno", "dataCadastro");

        
        TipoAviso tipoAviso = tipoAvisoRepository.findById(dto.getTipoAvisoId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo do aviso não encontrado"));
      
        avisoInterno.setDataCadastro(LocalDateTime.now());
        avisoInterno.setTipoAviso(tipoAviso);

        return avisoInterno;
    }
    
    public AvisoInterno salvarAviso(AvisoInterno aviso) {
        if (!professorRepository.existsById(aviso.getIdProfessorEnvio())) {
            throw new IllegalArgumentException("O professor de envio não existe!");
        }

        return avisoInternoRepository.save(aviso);
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

        // Atualizando o caminho do anexo
        destino.setPathAnexo(origem.getPathAnexo());

        // Outras possíveis atualizações, dependendo do DTO
    }

    @Transactional
    public void excluir(Long id) {
        avisoInternoRepository.deleteById(id);
    }
}
