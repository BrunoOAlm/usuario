package com.bruno.usuario.business;

import com.bruno.usuario.business.converter.UsuarioConverter;
import com.bruno.usuario.business.dto.EnderecoDTO;
import com.bruno.usuario.business.dto.TelefoneDTO;
import com.bruno.usuario.business.dto.UsuarioDTO;
import com.bruno.usuario.infrastructure.entity.Endereco;
import com.bruno.usuario.infrastructure.entity.Telefone;
import com.bruno.usuario.infrastructure.entity.Usuario;
import com.bruno.usuario.infrastructure.exceptions.ConflictException;
import com.bruno.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.bruno.usuario.infrastructure.repository.EnderecoRepository;
import com.bruno.usuario.infrastructure.repository.TelefoneRepository;
import com.bruno.usuario.infrastructure.repository.UsuarioRepository;
import com.bruno.usuario.infrastructure.security.JwtUtil;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor


public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    public final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario));
    }
    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado" + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado" + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscarUsuarioPorEmail (String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email)
                    .orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado " + email)
                    )
            );
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado " + email);
        }
    }
    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token,UsuarioDTO dto){

        //Aqui buscamos o email do usuário através do token(tirar a obrigatoriedade do email)
       String email = jwtUtil.extrairEmailToken(token.substring(7));

       //Criptografia de senha
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);


       //Busca os dados do usuário no banco de dados
       Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
               new ResourceNotFoundException("Email não localizado"));

       //Mesclou os dados que recebemos na requisição DTO com os dados do banco de dados.
        Usuario  usuario = usuarioConverter.updateUsuario(dto,usuarioEntity);

        //Salvou os dados do usuário convertido e depois pegou o retorno e converteu para UsuarioDTO.
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO) {

       Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
               new ResourceNotFoundException("Id não encontrado" + idEndereco));


        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO,entity);


        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO){

        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
            new ResourceNotFoundException("Id não encontrado" + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO , entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));

    }

    public EnderecoDTO cadastraEndereco(String token, EnderecoDTO dto){

        // Extrai o email do token (remove "Bearer ")
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        // Busca o usuário
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        // Converte DTO -> Entity
        Endereco endereco = usuarioConverter.paraEndereco(dto);

        // Associa com o usuário
        endereco.setUsuario(usuario);

        // Salva no banco
        return usuarioConverter.paraEnderecoDTO(
                enderecoRepository.save(endereco)
        );
    }

    public TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto){

        // Extrai o email do token
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        // Busca o usuário
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        // Converte DTO -> Entity
        Telefone telefone = usuarioConverter.paraTelefone(dto);

        // Associa com o usuário
        telefone.setUsuario(usuario);

        // Salva no banco
        return usuarioConverter.paraTelefoneDTO(
                telefoneRepository.save(telefone)
        );
    }


}
