package br.com.cgv.orders.dto;

import br.com.cgv.orders.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private LocalDateTime date;
    private Status status;
    private List<OrderItemDTO> items = new ArrayList<>();



}
