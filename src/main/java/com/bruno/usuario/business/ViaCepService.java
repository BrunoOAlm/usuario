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
        String cepFormatado = processarCep(cep);
        return viaCepClient.buscarCep(cepFormatado);
    }

    private String processarCep(String cep) {
        String cepFormatado = cep.replace(" ", "")
                                 .replace("-", "");

        if (!cepFormatado.matches("\\d{8}")) {
            throw new IllegalArgumentException("CEP inválido, deve conter 8 números.");
        }

        return cepFormatado;
    }
}