package br.com.softsy.educacional.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.CadastroCursoImgDTO;
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
    public List<CursoImg> listarImagensCurso(Long idCurso, Long idConta) {

        return cursoImgRepository.findByCurso_IdCursoAndConta(idCurso, idConta);
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
            throw new IllegalArgumentException("Já existe uma imagem cadastrada com essa ordem para este curso.");
        }

        CursoImg cursoImg = new CursoImg();
        BeanUtils.copyProperties(dto, cursoImg, "idCursoImg", "pathImg");

        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario não encontrada"));

       
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
}
