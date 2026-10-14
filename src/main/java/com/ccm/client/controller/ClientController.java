package com.ccm.client.controller;

import com.ccm.client.pojos.ClientPojo;
import com.ccm.client.responsepojos.ClientResponse;
import com.ccm.client.responsepojos.ResponsePojo;
import com.ccm.client.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("check")
    public ResponseEntity<String> check() {
        return new ResponseEntity<>("OK from CCM-Client", HttpStatus.OK);
    }

    @GetMapping("getClientList")
    public ResponseEntity<List<ClientResponse>> getClientList() {
        return new ResponseEntity<>(clientService.getClientList(), HttpStatus.OK);
    }

    @PostMapping("saveClient")
    public ResponseEntity<ResponsePojo> saveClient(@RequestBody ClientPojo clientPojo) {
        return new ResponseEntity<>(clientService.saveClient(clientPojo), HttpStatus.OK);
    }

    @GetMapping("getClientById")
    public ResponseEntity<com.ccm.common.responsepojos.ClientMapResponse> getClientById(@RequestParam("id") Integer id) {
        return new ResponseEntity<>(clientService.getClientById(id), HttpStatus.OK);
    }
}
