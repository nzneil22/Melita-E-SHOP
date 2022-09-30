package com.melita_task.api.controllers;

import com.melita_task.api.models.FullNameUpdate;
import com.melita_task.api.models.InstallationAddressUpdate;
import com.melita_task.api.models.Order;
import com.melita_task.api.models.OrdersUpdate;
import com.melita_task.api.service.ClientsService;
import com.melita_task.contract.ClientDto;
import com.melita_task.contract.ClientStatus;
import com.melita_task.contract.NewClientRequestDto;
import com.melita_task.contract.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/clients")
@RequiredArgsConstructor
public class CustomersController {

    private final MapperFacade mapper;
    private final ClientsService clientService;

    @PostMapping
    public ClientDto createCustomer(@RequestBody @NotNull @Valid final NewClientRequestDto newClientRequest) {
        log.info("Received request to register new client: {}", newClientRequest);
        return mapper.map(clientService.registerClient(newClientRequest), ClientDto.class);
    }

    @GetMapping(path = "/{clientId}")
    public ResponseEntity<ClientDto> getCustomer(@PathVariable final UUID clientId) {
        log.info("Received request to get client with id: {}", clientId);
        // if service returns empty optional, respond with 404
        return clientService.findClient(clientId)
                .map(client -> mapper.map(client, ClientDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{clientId}")
    public ResponseEntity<ClientDto> deleteCustomer(@PathVariable final UUID clientId) {
        log.info("Received request to deactivate client with id: {}", clientId);
        // if service returns empty optional, respond with 404
        return clientService.findClient(clientId)
                .map(client -> mapper.map(client, ClientDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{clientId}/full-name")
    public ClientDto editCustomerFullName(@PathVariable @NotNull final UUID clientId,
                                          @RequestBody @Valid final FullNameUpdate fullName) {
        log.info("Received request to edit client with id: {} using full-name: {}", clientId, fullName);
        return mapper.map(clientService.updateClient(clientId, fullName, null), ClientDto.class);
    }

    @PutMapping(path = "/{clientId}/installation-address")
    public ClientDto editCustomerInstallationAddress(@PathVariable @NotNull final UUID clientId,
                                          @RequestBody @Valid final InstallationAddressUpdate installationAddress) {
        log.info("Received request to edit client with id: {} using installation-address: {}", clientId, installationAddress);
        return mapper.map(clientService.updateClient(clientId, null, installationAddress), ClientDto.class);
    }

    @PostMapping("/{clientId}/orders")
    public OrderDto addOrder(@PathVariable @NotNull final UUID clientId,
                             @RequestBody @NotNull @Valid final OrderDto order) {
        log.info("Received request to add order to client with id: {}", clientId);
        return mapper.map(clientService.addOrder(clientId, mapper.map(order, Order.class)), OrderDto.class);
    }

    @GetMapping("/{clientId}/orders")
    public List<OrderDto> getOrders(@PathVariable @NotNull final UUID clientId) {
        log.info("Received request to get orders of client with id: {}", clientId);
        return clientService
                .getClientOrders(clientId)
                .stream()
                .map(o -> mapper.map(o, OrderDto.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/{clientId}/orders/submit")
    public List<OrderDto> submitClientOrders(@PathVariable @NotNull final UUID clientId){
        log.info("Received request to submit all orders of client with id: {}", clientId);
        return clientService
                .submitOrders(clientId)
                .stream()
                .map(o -> mapper.map(o, OrderDto.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/{clientId}/orders/{orderId}")
    public OrderDto editOrder(@PathVariable @NotNull final UUID clientId,
                              @PathVariable @NotNull final UUID orderId,
                              @RequestBody @NotNull OrdersUpdate oUpdate) {
        log.info("Received request to edit order: {} of client: {}", orderId, clientId);
        return mapper.map(clientService.editOrder(clientId, orderId, oUpdate), OrderDto.class);
    }

    @DeleteMapping("/{clientId}/orders/{orderId}")
    public String deleteOrder(@PathVariable @NotNull final UUID clientId,
                              @PathVariable @NotNull final UUID orderId) {
        log.info("Received request to delete order: {} of client: {}", orderId, clientId);
        return clientService.cancelOrder(clientId, orderId);
    }

    @PutMapping("/{clientId}/status")
    public ClientDto clientStatus(@PathVariable @NotNull final UUID clientId,
                                  @RequestBody @NotNull @Valid final ClientStatus status) {
        log.info("Received request to change state of client: {}", clientId);
        return mapper.map(clientService.clientStatus(clientId, status), ClientDto.class);
    }
}
