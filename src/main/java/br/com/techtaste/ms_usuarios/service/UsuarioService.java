package br.com.techtaste.ms_usuarios.service;

import br.com.techtaste.ms_usuarios.dto.EmailDto;
import br.com.techtaste.ms_usuarios.dto.UsuarioDto;
import br.com.techtaste.ms_usuarios.model.Usuario;
import br.com.techtaste.ms_usuarios.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public List<UsuarioDto> obterTodos() {
        return this.repository.findAll().stream()
                .map(u -> new UsuarioDto(u.getId(), u.getCpf(), u.getNome(),
                        u.getEmail()))
                .collect(Collectors.toList());
    }


    public UsuarioDto cadastrarUsuario(UsuarioDto usuario) {
        Usuario usuarioEntity = new Usuario();
        BeanUtils.copyProperties(usuario, usuarioEntity);
        this.repository.save(usuarioEntity);
        return new UsuarioDto(usuarioEntity.getId(), usuarioEntity.getCpf(),
                usuarioEntity.getNome(), usuarioEntity.getEmail());
    }

    public void enviarMensagem(EmailDto mensagem) {
        Optional<Usuario> usuario = this.repository.findByCpf(mensagem.cpf());
        // Lógica de envio de email
        System.out.println("Email enviado! " + mensagem);
    }
}
