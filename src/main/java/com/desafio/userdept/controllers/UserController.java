package com.desafio.userdept.controllers;

import com.desafio.userdept.dto.UserDTO;
import com.desafio.userdept.entities.User;
import com.desafio.userdept.repositories.UserRepository;
import com.desafio.userdept.services.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private FileService fileService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<UserDTO> findAll() {
        List<User> users = repository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        Optional<User> userOptional = repository.findById(id);
        return userOptional.map(user -> ResponseEntity.ok(convertToDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public User insert(@RequestBody User user) {
        User existingUser = repository.findByNameAndEmailAndDepartmentId(
                user.getName(), user.getEmail(), user.getDepartment().getId());

        if (existingUser != null) {
               throw new RuntimeException("Usuário com os mesmos dados já existe!");
        }
        return repository.save(user);
    }

    @PutMapping("{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        User entity = repository.getReferenceById(id);
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setDepartment(user.getDepartment());
        fileService.extrairDadosParaArquivo(entity);
        return repository.save(entity);
    }

    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

}