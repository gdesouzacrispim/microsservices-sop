package br.com.cgv.payments.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {
    private List<OrderItems> items;
}
