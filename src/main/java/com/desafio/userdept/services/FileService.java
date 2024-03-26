package com.desafio.userdept.services;

import com.desafio.userdept.entities.User;
import com.desafio.userdept.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class FileService {
    private final ObjectMapper objectMapper;

    public FileService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void extrairDadosParaArquivo(User user) {
        String filePath = "C://Java/cadastro_alterado.txt";

        try (FileWriter writer = new FileWriter(filePath)) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());

            String userJson = objectMapper.writeValueAsString(userDTO);
            writer.write(userJson);

            System.out.println("Dados do cadastro alterado foram salvos em " + filePath);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }
}