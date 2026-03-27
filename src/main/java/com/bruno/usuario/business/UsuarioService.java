package com.bruno.usuario.business;

import com.bruno.usuario.business.converter.UsuarioConverter;
import com.bruno.usuario.business.dto.UsuarioDTO;
import com.bruno.usuario.infrastructure.entity.Usuario;
import com.bruno.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    public final UsuarioConverter usuarioConverter;



    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario));
    }
}
