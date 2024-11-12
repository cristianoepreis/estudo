package com.estudos.desafios.itau.service;

import com.estudos.desafios.itau.entity.Client;
import com.estudos.desafios.itau.entity.dto.ClientDTO;
import com.estudos.desafios.itau.repository.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client saveClient(ClientDTO clientDTO){

        Client client = new Client();
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setParticipation(clientDTO.getParticipation());

        if(clientDTO.getFirstName() == null || clientDTO.getLastName() == null || clientDTO.getParticipation() == null){
            throw new NullPointerException();
        }

        return clientRepository.save(client);
    }

    public List<Client> getAllClients(){

        List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()){
            throw new NullPointerException();
        }
        return clientRepository.findAll();
    }
}
