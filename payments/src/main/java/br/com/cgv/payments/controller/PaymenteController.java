package br.com.cgv.payments.controller;

import br.com.cgv.payments.dto.PaymentDTO;
import br.com.cgv.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

@RestController
@RequestMapping("/payments")
public class PaymenteController {

    @Autowired
    private PaymentService service;

    @GetMapping
    public Page<PaymentDTO> listar(@PageableDefault(size = 10) Pageable paginacao) {
        return service.listAll(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getById(
            @PathVariable @NotNull Long id){
        PaymentDTO payment = service.findById(id);
        return ResponseEntity.ok(payment);
    }
    
    @PostMapping
    public ResponseEntity<PaymentDTO> create(
            @RequestBody @Valid PaymentDTO createdPaymentDto,
            UriComponentsBuilder uriComponentsBuilder){
        PaymentDTO paymentDTO = service.create(createdPaymentDto);
        URI uri = uriComponentsBuilder.path("/payments/{id}").buildAndExpand(paymentDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(paymentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> update(
            @PathVariable @NotNull Long id,
            @RequestBody @Valid PaymentDTO paymentUpdateDTO){
        PaymentDTO paymentDTO = service.update(id, paymentUpdateDTO);
        return ResponseEntity.ok(paymentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDTO> delete(@PathVariable @NotNull Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
        
}
