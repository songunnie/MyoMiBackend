package com.myomi.order.entity;

import com.myomi.user.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "orders_detail")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailEmbedded id = new OrderDetailEmbedded();

    @MapsId("oNum")
    @ManyToOne
    @JoinColumn(name = "order_num")
    private Order order;

    @MapsId("pNum") // 복합키
    @ManyToOne
    @JoinColumn(name = "prod_num")
    private Product product;

    @Column(name = "prod_cnt", nullable = false)
    private Long prodCnt;
}
