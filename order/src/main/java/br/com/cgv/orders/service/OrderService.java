package br.com.cgv.orders.service;

import br.com.cgv.orders.dto.OrderDTO;
import br.com.cgv.orders.dto.StatusDto;
import br.com.cgv.orders.model.Order;
import br.com.cgv.orders.model.Status;
import br.com.cgv.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private final ModelMapper modelMapper;


    public List<OrderDTO> listaAll() {
        return repository.findAll().stream()
                .map(p -> modelMapper.map(p, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public OrderDTO findById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(order, OrderDTO.class);
    }

    public OrderDTO create(OrderDTO dto) {
        Order order = modelMapper.map(dto, Order.class);

        order.setDate(LocalDateTime.now());
        order.setStatus(Status.COMPLETED);
        order.getItems().forEach(item -> item.setOrder(order));
        Order salvo = repository.save(order);

        return modelMapper.map(order, OrderDTO.class);
    }

    public OrderDTO update(Long id, StatusDto dto) {

        Order order = repository.byIdWithItems(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(dto.getStatus());
        repository.updateStatus(dto.getStatus(), order);
        return modelMapper.map(order, OrderDTO.class);
    }

    public void approvePaymentOrder(Long id) {

        Order order = repository.byIdWithItems(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(Status.PAID);
        repository.updateStatus(Status.PAID, order);
    }
}
