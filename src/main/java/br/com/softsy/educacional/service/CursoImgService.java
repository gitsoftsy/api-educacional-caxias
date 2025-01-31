package br.com.softsy.educacional.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroCursoImgDTO;
import br.com.softsy.educacional.dto.CursoImgDTO;
import br.com.softsy.educacional.model.Conta;
import br.com.softsy.educacional.model.Curso;
import br.com.softsy.educacional.model.CursoImg;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.repository.ContaRepository;
import br.com.softsy.educacional.repository.CursoImgRepository;
import br.com.softsy.educacional.repository.CursoRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;
import br.com.softsy.educacional.utils.ImageManager;

@Service
public class CursoImgService {

    @Autowired
    private CursoImgRepository cursoImgRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ContaRepository contaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Transactional
    public List<CursoImgDTO> listarImagensCurso(Long idCurso, Long idConta) {
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new IllegalArgumentException("O curso com ID " + idCurso + " não existe."));

        if (!curso.getConta().getIdConta().equals(idConta)) {
            throw new IllegalArgumentException("A conta com ID " + idConta + " não está associada ao curso com ID " + idCurso + ".");
        }

        List<CursoImg> imagens = cursoImgRepository.findByCursoIdCursoAndCursoContaIdConta(idCurso, idConta);
        if (imagens.isEmpty()) {
            throw new IllegalArgumentException("O curso com ID " + idCurso + " não possui imagens associadas à conta com ID " + idConta);
        }
        return imagens.stream()
                      .map(CursoImgDTO::new)
                      .collect(Collectors.toList());
    }

    @Transactional
    public CadastroCursoImgDTO salvar(CadastroCursoImgDTO dto) throws IOException {
    	String base64 = "";
    	
        CursoImg cursoImg = criarCursoImgAPartirDTO(dto);
        base64 = cursoImg.getPathImg();
        
        cursoImg.setPathImg("a");

        cursoImg = cursoImgRepository.save(cursoImg);
        String caminhoIMG = ImageManager.salvaImagemCurso(base64, cursoImg.getIdCursoImg(), "anexoCursoImg");

 
        cursoImg.setPathImg(caminhoIMG);
        dto.setPathImg(caminhoIMG);
        dto.setIdCursoImg(cursoImg.getIdCursoImg());
        
        atualizaDados(cursoImg, dto);
        CadastroCursoImgDTO cursoImgCriada = new CadastroCursoImgDTO(cursoImg);
        return cursoImgCriada;
    }

    private void atualizaDados(CursoImg destino, CadastroCursoImgDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idCursoImg", "dataCadastro");

        Curso curso = cursoRepository.findById(origem.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        destino.setCurso(curso);
        destino.setPathImg(origem.getPathImg());
    }

    private CursoImg criarCursoImgAPartirDTO(CadastroCursoImgDTO dto) {
 
        if (!"D".equals(dto.getTipoDispositivo()) && !"M".equals(dto.getTipoDispositivo())) {
            throw new IllegalArgumentException("O tipo de dispositivo deve ser 'D' para Desktop ou 'M' para Mobile.");
        }

        List<CursoImg> imagensExistentes = cursoImgRepository.findByCurso_IdCursoAndOrdem(dto.getCursoId(), dto.getOrdem());
        if (!imagensExistentes.isEmpty()) {
            throw new IllegalArgumentException("Já existe uma imagem cadastrada com a ordem " + dto.getOrdem() + " para o curso com ID " + dto.getCursoId());
        }

        CursoImg cursoImg = new CursoImg();
        BeanUtils.copyProperties(dto, cursoImg, "idCursoImg", "pathImg");

        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        cursoImg.setCurso(curso);
        cursoImg.setDataCadastro(LocalDateTime.now());
        cursoImg.setConta(conta);
        cursoImg.setUsuario(usuario);
        cursoImg.setTipoDispositivo(dto.getTipoDispositivo());
        cursoImg.setPathImg(dto.getPathImg());
        cursoImg.setOrdem(dto.getOrdem());
        cursoImg.setUrl(dto.getUrl());

        return cursoImg;
    }
    
    public CursoImgDTO obterImagemCurso(Long idCursoImg, Long idConta) {
        Optional<CursoImg> cursoImgOptional = cursoImgRepository.findById(idCursoImg);

        if (!cursoImgOptional.isPresent()) {
            throw new IllegalArgumentException("A imagem com ID " + idCursoImg + " não foi encontrada.");
        }
        CursoImg cursoImg = cursoImgOptional.get();

        if (!cursoImg.getCurso().getConta().getIdConta().equals(idConta)) {
            throw new IllegalArgumentException("O curso de imagem com ID " + idCursoImg + " não está vinculado à conta com ID " + idConta + ".");
        }

        return new CursoImgDTO(cursoImg);
    }
    
    private static final String IMAGE_FOLDER_PATH = "/path/to/images/";
    
    @Transactional
    public void removerImagemDescricao(Long idCursoImg, Long idConta) {
        CursoImg imagemRemover = cursoImgRepository.findById(idCursoImg)
                .orElseThrow(() -> new IllegalArgumentException("A imagem com ID " + idCursoImg + " não existe."));
        
        if (!imagemRemover.getCurso().getConta().getIdConta().equals(idConta)) {
            throw new IllegalArgumentException("A imagem com ID " + idCursoImg + " não está vinculada à conta com ID " + idConta + ".");
        }
        
        cursoImgRepository.delete(imagemRemover);
        List<CursoImg> imagensRestantes = cursoImgRepository.findByCursoIdCursoAndCursoContaIdContaOrderByOrdem(imagemRemover.getCurso().getIdCurso(), idConta);
        
        for (CursoImg imagem : imagensRestantes) {
            if (imagem.getOrdem() > imagemRemover.getOrdem()) {
                imagem.setOrdem(imagem.getOrdem() - 1);  
                cursoImgRepository.save(imagem); 
            }
        }

        Path path = Paths.get(imagemRemover.getPathImg());
        try {
            Files.deleteIfExists(path); // Exclui o arquivo
        } catch (IOException e) {
            throw new RuntimeException("Erro ao excluir o arquivo da imagem no servidor.", e);
        }
    }

}
