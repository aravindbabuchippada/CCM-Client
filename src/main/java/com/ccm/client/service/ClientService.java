package com.ccm.client.service;

import com.ccm.client.entity.Clients;
import com.ccm.client.pojos.ClientPojo;
import com.ccm.client.repository.ClientsRepository;
import com.ccm.client.responsepojos.ClientResponse;
import com.ccm.client.responsepojos.ResponsePojo;
import com.ccm.common.exception.UpdateException;
import com.ccm.common.jwt.JwtUtils;
import com.ccm.common.responsepojos.ClientMapResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClientService {
    private final RedisTemplate<String, String> redisTemplate;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final ClientsRepository clientsRepository;

    public ClientService(@Qualifier("redisTemplate")  RedisTemplate<String, String> redisTemplate,
                     BCryptPasswordEncoder passwordEncoder,
                     JwtUtils jwtUtils,
                     AuthenticationManager authenticationManager,
                     ClientsRepository clientsRepository) {
        this.redisTemplate = redisTemplate;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.clientsRepository = clientsRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    public List<ClientResponse> getClientList() {
        try {
            List<Clients> clients = clientsRepository.findAll(Sort.by(Sort.Direction.ASC, "clientName"));

            List<ClientResponse> clientResponses = clients.stream().map(client -> {
                ClientResponse clientResponse = new ClientResponse();
                BeanUtils.copyProperties(client, clientResponse);
                clientResponse.setTotalCount(clients.size());
                return clientResponse;
            }).toList();

            return clientResponses;
        } catch (RuntimeException e) {
            throw new UpdateException("Failed to call getClientList => "+e.getMessage());
        }
    }

    public ResponsePojo saveClient(ClientPojo clientPojo) {
        try {
            Clients client;

            if (clientPojo.getId() > 0) {
                client = clientsRepository.findById(clientPojo.getId()).orElseThrow(() ->
                        new NoSuchElementException("There is no record in Client table with id = " + clientPojo.getId())
                );
                BeanUtils.copyProperties(clientPojo, client);
                client.setModifiedOn(LocalDateTime.now());
            } else {
                client = new Clients();
                BeanUtils.copyProperties(clientPojo, client, "id");
                client.setCreatedOn(LocalDateTime.now());
            }
            clientsRepository.save(client);
            return new ResponsePojo();
        } catch (RuntimeException e) {
            throw new UpdateException("Failed to call saveClient => "+e.getMessage());
        }
    }

    public ClientMapResponse getClientById(Integer id) {
        Clients client = clientsRepository.findById(id)
                .orElseThrow(() -> new UpdateException("There is no data in Client with the id: " + id));
        return toClientMapResponse(client);
    }

    private ClientMapResponse toClientMapResponse(Clients client) {
        ClientMapResponse clientMapResponse = ClientMapResponse.builder()
                .id(client.getId())
                .clientName(client.getClientName())
                .build();
        return clientMapResponse;
    }
}
