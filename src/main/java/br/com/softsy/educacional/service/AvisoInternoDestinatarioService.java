package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.AvisoDestinatarioDTO;
import br.com.softsy.educacional.dto.AvisoInternoDestinatarioDTO;
import br.com.softsy.educacional.dto.CadastroAvisoDestinatarioDTO;
import br.com.softsy.educacional.dto.CadastroAvisoInternoDestinatarioDTO;
import br.com.softsy.educacional.model.Aluno;
import br.com.softsy.educacional.model.Aviso;
import br.com.softsy.educacional.model.AvisoDestinatario;
import br.com.softsy.educacional.model.AvisoInterno;
import br.com.softsy.educacional.model.AvisoInternoDestinatario;
import br.com.softsy.educacional.model.Professor;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.repository.AvisoInternoDestinatarioRepository;
import br.com.softsy.educacional.repository.AvisoInternoRepository;
import br.com.softsy.educacional.repository.ProfessorRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;

@Service
public class AvisoInternoDestinatarioService {

    @Autowired
    private AvisoInternoDestinatarioRepository repository;

    @Autowired
    private AvisoInternoRepository avisoInternoRepository;

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

    @Transactional(readOnly = true)
    public List<AvisoInternoDestinatarioDTO> buscarPorIdUsuario(Long idUsuario) {
        if (idUsuario == null) {
            throw new IllegalArgumentException("O ID do usuário é obrigatório.");
        }
        List<AvisoInternoDestinatario> avisoInternoDestinatario = repository.findByUsuarioDestinatario_IdUsuario(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar destinatário do aviso por ID do usuário"));
        return avisoInternoDestinatario.stream()
                .map(AvisoInternoDestinatarioDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AvisoInternoDestinatarioDTO> buscarPorIdProfessor(Long idProfessor) {
        if (idProfessor == null) {
            throw new IllegalArgumentException("O ID do professor é obrigatório.");
        }
        List<AvisoInternoDestinatario> avisoInternoDestinatario = repository.findByProfessorDestinatario_IdProfessor(idProfessor)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar destinatário do aviso por ID do professor"));
        return avisoInternoDestinatario.stream()
                .map(AvisoInternoDestinatarioDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CadastroAvisoInternoDestinatarioDTO salvar(CadastroAvisoInternoDestinatarioDTO dto) {
        if (dto.getAvisoInternoId() == null) {
            throw new IllegalArgumentException("O ID do aviso interno é obrigatório.");
        }
        if (dto.getUsuarioDestinatarioId() == null && dto.getProfessorDestinatarioId() == null) {
            throw new IllegalArgumentException("É necessário informar pelo menos um destinatário: usuário ou professor.");
        }

        AvisoInternoDestinatario avisoInternoDestinatario = criarAvisoInternoDestinatarioAPartirDTO(dto);
        avisoInternoDestinatario = repository.save(avisoInternoDestinatario);

        return new CadastroAvisoInternoDestinatarioDTO(avisoInternoDestinatario);
    }

    private AvisoInternoDestinatario criarAvisoInternoDestinatarioAPartirDTO(CadastroAvisoInternoDestinatarioDTO dto) {
        AvisoInternoDestinatario avisoInternoDestinatario = new AvisoInternoDestinatario();

        BeanUtils.copyProperties(dto, avisoInternoDestinatario, "idAvisoInternoDestinatario", "dataCadastro");

        AvisoInterno avisoInterno = avisoInternoRepository.findById(dto.getAvisoInternoId())
                .orElseThrow(() -> new IllegalArgumentException("Aviso não encontrado"));

        Usuario usuarioDestinatario = null;
        if (dto.getUsuarioDestinatarioId() != null) {
            usuarioDestinatario = usuarioRepository.findById(dto.getUsuarioDestinatarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário destinatário não encontrado"));
        }

        Professor professorDestinatario = null;
        if (dto.getProfessorDestinatarioId() != null) {
            professorDestinatario = professorRepository.findById(dto.getProfessorDestinatarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Professor destinatário não encontrado"));
        }

        avisoInternoDestinatario.setDataCadastro(LocalDateTime.now());
        avisoInternoDestinatario.setAvisoInterno(avisoInterno);
        avisoInternoDestinatario.setUsuarioDestinatario(usuarioDestinatario);
        avisoInternoDestinatario.setProfessorDestinatario(professorDestinatario);
        avisoInternoDestinatario.setDataLeitura(dto.getDataLeitura());

        return avisoInternoDestinatario;
    }

    @Transactional
    public AvisoInternoDestinatarioDTO atualizar(CadastroAvisoInternoDestinatarioDTO dto) {
        if (dto.getIdAvisoInternoDestinatario() == null) {
            throw new IllegalArgumentException("O ID do destinatário do aviso interno é obrigatório para atualização.");
        }

        AvisoInternoDestinatario avisoInternoDestinatario = repository.getReferenceById(dto.getIdAvisoInternoDestinatario());
        atualizaDados(avisoInternoDestinatario, dto);

        return new AvisoInternoDestinatarioDTO(avisoInternoDestinatario);
    }

    private void atualizaDados(AvisoInternoDestinatario destino, CadastroAvisoInternoDestinatarioDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idAvisoDestinatario", "dataCadastro");

        AvisoInterno avisoInterno = avisoInternoRepository.findById(origem.getAvisoInternoId())
                .orElseThrow(() -> new IllegalArgumentException("Aviso não encontrado"));

        Usuario usuarioDestinatario = null;
        if (origem.getUsuarioDestinatarioId() != null) {
            usuarioDestinatario = usuarioRepository.findById(origem.getUsuarioDestinatarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario não encontrado"));
        }

        Professor professorDestinatario = null;
        if (origem.getProfessorDestinatarioId() != null) {
            professorDestinatario = professorRepository.findById(origem.getProfessorDestinatarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
        }

        destino.setAvisoInterno(avisoInterno);
        destino.setUsuarioDestinatario(usuarioDestinatario);
        destino.setProfessorDestinatario(professorDestinatario);
    }

    @Transactional
    public AvisoInternoDestinatarioDTO atualizarDataLeitura(Long idAvisoInternoDestinatario, LocalDateTime dataLeitura) {
        if (idAvisoInternoDestinatario == null) {
            throw new IllegalArgumentException("O ID do destinatário do aviso interno é obrigatório.");
        }
        if (dataLeitura == null) {
            throw new IllegalArgumentException("A data de leitura é obrigatória.");
        }

        AvisoInternoDestinatario avisoInternoDestinatario = repository.getReferenceById(idAvisoInternoDestinatario);
        avisoInternoDestinatario.setDataLeitura(dataLeitura);

        repository.save(avisoInternoDestinatario);
        return new AvisoInternoDestinatarioDTO(avisoInternoDestinatario);
    }

    public List<Map<String, Object>> listaDestinatariosAvisoInterno(Long idAvisoInterno) {
        StringBuilder sql = new StringBuilder();
        sql.append("CALL PROC_AVISO_LISTA_DESTINATARIO_INTERNO(:pIdAvisoInterno)");

        Query query = entityManager.createNativeQuery(sql.toString());

        query.setParameter("pIdAvisoInterno", idAvisoInterno);

        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> mappedResultList = new ArrayList<>();

        for (Object[] result : resultList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("idAvisoInterno", result[0]);
            resultMap.put("idAvisoInternoDestinatario", result[1]);
            resultMap.put("tipo", result[2]);
            resultMap.put("nome", result[3]);
            resultMap.put("cpf", result[4]);
            resultMap.put("email", result[5]);
            resultMap.put("dataLeitura", result[6]);

            mappedResultList.add(resultMap);
        }

        return mappedResultList;
    }
    
    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

}
