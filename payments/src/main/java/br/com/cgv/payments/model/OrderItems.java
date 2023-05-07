package br.com.cgv.payments.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItems {

    private Long id;
    private Integer quantity;
    private String description;

}
