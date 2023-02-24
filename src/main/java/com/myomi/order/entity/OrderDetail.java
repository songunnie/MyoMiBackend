package com.myomi.order.entity;

import com.myomi.product.entity.Product;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders_detail")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailEmbedded id = new OrderDetailEmbedded();

    @MapsId("oNum")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_num", referencedColumnName = "num")
//    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    private Order order;

    @MapsId("pNum") // 복합키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_num", insertable = false, updatable = false)
    private Product product;

    @Column(name = "prod_cnt", nullable = false)
    private int prodCnt;

    @Builder
    public OrderDetail(Order order, Product product, int prodCnt) {
        this.order = order;
        this.product = product;
        this.prodCnt = prodCnt;
    }

    // 연관관계 편의 메소드
    public void registerOrder(Order order) {
//        if(this.order != null) {
//            this.order.getOrderDetails().remove(this);
//        }
        this.order = order;
        order.getOrderDetails().add(this);

//        if(!order.getOrderDetails().contains(this)) {
//            order.getOrderDetails().add(this);
//        }
    }
}
