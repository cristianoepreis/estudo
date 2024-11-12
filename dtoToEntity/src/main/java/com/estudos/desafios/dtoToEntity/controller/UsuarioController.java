package com.estudos.desafios.dtoToEntity.controller;

import com.estudos.desafios.dtoToEntity.business.UsuarioService;
import com.estudos.desafios.dtoToEntity.business.dto.UsuarioRequestDTO;
import com.estudos.desafios.dtoToEntity.business.dto.UsuarioResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService service;


    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> save(@RequestBody UsuarioRequestDTO dto){
        return ResponseEntity.ok(service.saveUsuario(dto));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UsuarioResponseDTO> findByEmail(@PathVariable("email") String email){
        return ResponseEntity.ok(service.findUsuarioByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> findAllUsuarios(){
        return ResponseEntity.ok(service.findAllUsuarios());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUsuario(@RequestParam("email") String email){
        service.deleteUsuarioForEmail(email);
        return ResponseEntity.noContent().build();
    }

}
