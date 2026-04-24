package com.bruno.usuario.business;

import com.bruno.usuario.infrastructure.clients.ViaCepClient;
import com.bruno.usuario.infrastructure.clients.ViaCepDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final ViaCepClient viaCepClient;

    public ViaCepDTO buscarDadosEndereco(String cep) {
        cep = cep.replace("-", "");
        return viaCepClient.buscarCep(cep);
    }
}