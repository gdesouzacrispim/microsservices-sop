package br.com.cgv.payments.dto;

import br.com.cgv.payments.model.OrderItems;
import br.com.cgv.payments.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PaymentDTO {

    private Long id;
    private BigDecimal value;
    private String name;
    private String number;
    private String expiration;
    private String code;
    private Status status;
    private Long orderId;
    private Long paymentMethodId;
    private List<OrderItems> items;
}
