package br.com.softsy.educacional.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.softsy.educacional.dto.AtualizarSenhaDTO;
import br.com.softsy.educacional.dto.ListaUsuarioContaDTO;
import br.com.softsy.educacional.dto.UsuarioDTO;
import br.com.softsy.educacional.model.AllResponse;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;
    
    
    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(service.listarTudo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaUsuarioContaDTO> buscarPorId(@PathVariable Long id) {
        ListaUsuarioContaDTO listaUsuarioContaDTO = service.buscarPorId(id);
        return ResponseEntity.ok(listaUsuarioContaDTO);
    }
    
    @GetMapping("/verificar-usuario")
    public ResponseEntity<String> verificarUsuario(@RequestParam String usuario) {
        if (service.existeUsuarioPorUsuario(usuario)) {
            return ResponseEntity.badRequest().body("Esse usuário já existe.");
        } else {
            return ResponseEntity.ok("Nenhum usuário encontrado com esse nome de usuário.");
        }
    }
    
    @GetMapping("/verificar-email")
    public ResponseEntity<String> verificarEmail(@RequestParam String email) {
        if (service.existeUsuarioPorEmail(email)) {
            return ResponseEntity.badRequest().body("Esse email já existe.");
        } else {
            return ResponseEntity.ok("Nenhum usuário encontrado com esse email.");
        }
    }
    
    @GetMapping("/verificar-cpf")
    public ResponseEntity<String> verificarCpf(@RequestParam String cpf) {
        if (service.existeUsuarioPorCpf(cpf)) {
            return ResponseEntity.badRequest().body("Esse CPF já existe.");
        } else {
            return ResponseEntity.ok("Nenhum usuário encontrado com esse CPF.");
        }
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody @Valid UsuarioDTO dto) {
        UsuarioDTO usuarioDTO = service.salvar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(usuarioDTO.getIdUsuario()).toUri();
        return ResponseEntity.created(uri).body(usuarioDTO);
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizar(@RequestBody @Valid UsuarioDTO dto) {
        return ResponseEntity.ok(service.atualizar(dto));
    }

    @PutMapping("/{idUsuario}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long idUsuario) {
        service.ativaDesativa('S', idUsuario);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idUsuario}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long idUsuario) {
        service.ativaDesativa('N', idUsuario);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}/atualizar-senha")
    public ResponseEntity<AllResponse> atualizarSenhaUsuario(
            @PathVariable Long id,
            @RequestBody AtualizarSenhaDTO atualizarSenhaDTO) {

    	service.atualizarSenhaUsuario(id, atualizarSenhaDTO.getSenhaAntiga(), atualizarSenhaDTO.getSenhaNova());

        AllResponse response = new AllResponse("Senha do usuário atualizada com sucesso", new ArrayList<>());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/avisos-usuario/{idUsuario}")
    public Map<String, Object> contarMensagensUsuario(@PathVariable Long idUsuario) {
        Long qtdMensagens = (Long) entityManager.createQuery(
                "SELECT COUNT(DISTINCT ad.idAvisoDestinatario) " +
                        "FROM AvisoDestinatario ad " +
                        "JOIN ad.aviso a " +
                        "WHERE a.usuario.idUsuario = :idUsuario " +
                        "AND CURRENT_TIMESTAMP BETWEEN a.dataInicio AND a.dataFim")
                .setParameter("idUsuario", idUsuario)
                .getSingleResult();

        Map<String, Object> response = new HashMap<>();
        response.put("qtdMensagens", qtdMensagens);
        return response;
    }
}