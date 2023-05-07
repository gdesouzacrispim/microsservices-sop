package br.com.cgv.orders.controller;

import br.com.cgv.orders.dto.OrderDTO;
import br.com.cgv.orders.dto.StatusDto;
import br.com.cgv.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderControllers {

    @Autowired
    private OrderService service;

    @GetMapping()
    public List<OrderDTO> listAll() {
        return service.listaAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable @NotNull Long id) {
        OrderDTO dto = service.findById(id);

        return  ResponseEntity.ok(dto);
    }

    @PostMapping()
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody @Valid OrderDTO dto, UriComponentsBuilder uriBuilder) {
        OrderDTO orderPlaced = service.create(dto);

        URI uri = uriBuilder.path("/orders/{id}").buildAndExpand(orderPlaced.getId()).toUri();

        return ResponseEntity.created(uri).body(orderPlaced);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateStatus(@PathVariable Long id, @RequestBody StatusDto status){
       OrderDTO dto = service.update(id, status);

        return ResponseEntity.ok(dto);
    }


    @PutMapping("/{id}/paid")
    public ResponseEntity<Void> approvePayment(@PathVariable @NotNull Long id) {
        service.approvePaymentOrder(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/port")
    public String getPort(@Value("${local.server.port}") String port){
        return String.format("Request answered by instance executing on port %s", port);
    }
}
