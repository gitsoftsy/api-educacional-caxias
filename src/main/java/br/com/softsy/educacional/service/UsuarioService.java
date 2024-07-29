package br.com.softsy.educacional.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softsy.educacional.dto.ListaUsuarioContaDTO;
import br.com.softsy.educacional.dto.UsuarioContaDTO;
import br.com.softsy.educacional.dto.UsuarioDTO;
import br.com.softsy.educacional.infra.config.PasswordEncrypt;
import br.com.softsy.educacional.infra.exception.NegocioException;
import br.com.softsy.educacional.infra.exception.UniqueException;
import br.com.softsy.educacional.model.Usuario;
import br.com.softsy.educacional.model.UsuarioConta;
import br.com.softsy.educacional.repository.UsuarioContaRepository;
import br.com.softsy.educacional.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;
    
    @Autowired
    private UsuarioContaRepository usuarioContarepository;
	@Autowired
	private PasswordEncrypt encrypt;

    public List<Usuario> listarTudo() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public ListaUsuarioContaDTO buscarPorId(Long id) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(repository.getReferenceById(id));

        List<UsuarioConta> usuarioContas = usuarioContarepository.findByUsuario_IdUsuario(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao buscar conta por usuarioId"));

        List<UsuarioContaDTO> usuarioContaDTOs = usuarioContas.stream()
                .map(UsuarioContaDTO::new)
                .collect(Collectors.toList());

        // Criar o DTO combinado
        ListaUsuarioContaDTO listaUsuarioContaDTO = new ListaUsuarioContaDTO();
        listaUsuarioContaDTO.setUsuario(usuarioDTO);
        listaUsuarioContaDTO.setUsuarioConta(usuarioContaDTOs); // Adaptei para uma lista de UsuarioContaDTO

        return listaUsuarioContaDTO;
    }
    
    public boolean existeUsuarioPorUsuario(String usuario) {
        return repository.findByUsuario(usuario).stream().findFirst().isPresent();
    }
    
    public boolean existeUsuarioPorEmail(String email) {
        return repository.findByEmail(email).stream().findFirst().isPresent();
    }
    
    public boolean existeUsuarioPorCpf(String cpf) {
        return repository.findByCpf(cpf).stream().findFirst().isPresent();
    }

    @Transactional
    public UsuarioDTO salvar(UsuarioDTO dto) {
    	
        validarUsuario(dto.getUsuario());
        validarCpf(dto.getCpf());
        validarEmail(dto.getEmail());
        validarSenha(dto.getSenha());

        Usuario usuario = criarUsuarioAPartirDTO(dto);
        usuario.setSenha(encrypt.hashPassword(dto.getSenha()));
        usuario = repository.save(usuario);
        return new UsuarioDTO(usuario);
    }

    private Usuario criarUsuarioAPartirDTO(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(dto, usuario, "idUsuario", "ativo", "dataCadastro");
        usuario.setAtivo('S');
        usuario.setDataCadastro(LocalDateTime.now());
        return usuario;
    }

    @Transactional
    public UsuarioDTO atualizar(UsuarioDTO dto) {
        Usuario usuario = repository.getReferenceById(dto.getIdUsuario());
        atualizaDados(usuario, dto);
        return new UsuarioDTO(usuario);
    }

    @Transactional
    public void ativaDesativa(char status, Long idUsuario) {
        Usuario usuario = repository.getReferenceById(idUsuario);
        usuario.setAtivo(status);
    }

    private void validarUsuario(String usuario) {
        Optional<Usuario> usuarioExistente = repository.findByUsuario(usuario).stream().findFirst();
        if (usuarioExistente.isPresent()) {
            throw new UniqueException("Esse usuário já existe.");
        }
    }
    
    private void validarEmail(String email) {
        Optional<Usuario> usuarioExistente = repository.findByEmail(email).stream().findFirst();
        if (usuarioExistente.isPresent()) {
            throw new UniqueException("Esse email já existe.");
        }
    }
    
    private void validarCpf(String cpf) {
        Optional<Usuario> usuarioExistente = repository.findByCpf(cpf).stream().findFirst();
        if (usuarioExistente.isPresent()) {
            throw new UniqueException("Esse CPF já existe.");
        }
    }

	private void validarSenha(String senha) {
		if (senha == null) {
			throw new NegocioException("A senha precisa ser informada!");
		}
	}
    
    private void atualizaDados(Usuario destino, UsuarioDTO origem) {
        BeanUtils.copyProperties(origem, destino, "idUsuario", "ativo", "dataCadastro", "senha");
		if(origem.getSenha() != null) {
			destino.setSenha(encrypt.hashPassword(origem.getSenha()));
		}
    }
}