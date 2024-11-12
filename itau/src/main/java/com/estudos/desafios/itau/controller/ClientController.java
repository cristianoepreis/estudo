package com.estudos.desafios.itau.controller;

import com.estudos.desafios.itau.entity.Client;
import com.estudos.desafios.itau.entity.dto.ClientDTO;
import com.estudos.desafios.itau.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/save")
    public ResponseEntity<Client>saveClient(@RequestBody ClientDTO clientDTO){
        return ResponseEntity.ok(clientService.saveClient(clientDTO));
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Client>> listAllClients(){
        return ResponseEntity.ok(clientService.getAllClients());

    }
}
