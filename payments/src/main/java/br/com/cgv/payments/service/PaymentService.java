package br.com.cgv.payments.service;

import br.com.cgv.payments.dto.PaymentDTO;
import br.com.cgv.payments.httpClient.OrderClient;
import br.com.cgv.payments.model.Order;
import br.com.cgv.payments.model.Payment;
import br.com.cgv.payments.model.Status;
import br.com.cgv.payments.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderClient order;

    public Page<PaymentDTO> listAll(Pageable pageable){
        return repository
                .findAll(pageable)
                .map(payment -> modelMapper.map(payment, PaymentDTO.class));
    }

    public PaymentDTO findById(Long id){
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        PaymentDTO paymentDTO = modelMapper.map(payment, PaymentDTO.class);

        Order orderItems = order.getOrderItems(paymentDTO.getOrderId());
        paymentDTO.setItems(orderItems.getItems());

        return paymentDTO;
    }


    public PaymentDTO create(PaymentDTO paymentDTO){
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        payment.setStatus(Status.CREATED);
        repository.save(payment);

        return modelMapper.map(payment, PaymentDTO.class);
    }

    public PaymentDTO update(Long id, PaymentDTO paymentDTO){
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        payment.setId(id);
        payment = repository.save(payment);
        repository.save(payment);
        return modelMapper.map(payment, PaymentDTO.class);
    }

    public void delete (Long id){
        repository.deleteById(id);
    }

    public void confirmPayment(Long id){
        Optional<Payment> payment = repository.findById(id);

        if (!payment.isPresent()) {
            throw new EntityNotFoundException();
        }

        payment.get().setStatus(Status.CONFIRMED);
        repository.save(payment.get());
        order.updatePayment(payment.get().getOrderId());
    }

    public void alterStatus(Long id){
        Optional<Payment> payment = repository.findById(id);

        if (!payment.isPresent()) {
            throw new EntityNotFoundException();
        }

        payment.get().setStatus(Status.CONFIRMED_WITHOUT_INTEGRATION);
        repository.save(payment.get());
    }
}
