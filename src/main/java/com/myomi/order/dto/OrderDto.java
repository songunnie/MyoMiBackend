package com.myomi.order.dto;

import com.myomi.order.entity.Delivery;
import com.myomi.order.entity.Order;
import com.myomi.order.entity.OrderDetail;
import com.myomi.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderDto { // 주문 기본, 상세, 배송정보 한번에 다 받아옴
    private Long oNum;
    private User user;
    private String msg;
    private Long couponNum;
    private Long usedPoint;
    private Long totalPrice;
    private Long savePoint;
    private LocalDateTime createdDate;
    private LocalDateTime payCreatedDate;
    private LocalDateTime canceledDate;
    // 주문정보
    private List<OrderDetail> orderDetail;
    private Order order;
//    private Product product;
    private int prodNum;
    private int prodCnt;
    // 배송정보
    private Delivery delivery;

    // QueryDsl용 변수
    private String pName; // 상품 이름
    private Long rNum; // 리뷰 번호


    //-----------------
    private DeliveryDto deliveryDto;
//    private List<OrderDetailDto> orderDetailDtos;

    @Builder
    public OrderDto (User user, String msg, Long couponNum, Long usedPoint,
                     Long savePoint, Long totalPrice, List<OrderDetail> orderDetails,
                     Delivery delivery) {
        this.user = user;
        this.createdDate = LocalDateTime.now();
        this.msg = msg;
        this.couponNum = couponNum;
        this.usedPoint = usedPoint;
        this.savePoint = savePoint; // TODO: 그냥 받아와도 될까?
        this.totalPrice = totalPrice;
//        this.orderDetails = orderDetails;
//        this.delivery = delivery;
    }

    public Order toEntity(User u, OrderDto orderDto) {
//        Order order = new Order();
//        this.user = orderDto.getUser();
//        this.createdDate = orderDto.getCreatedDate();
//        this.msg = orderDto.getMsg();
//        this.couponNum = orderDto.getCouponNum();
//        this.usedPoint = orderDto.getUsedPoint();
//        this.savePoint = orderDto.getSavePoint(); // TODO: 그냥 받아와도 될까?
//        this.totalPrice = orderDto.getTotalPrice();
//        return order;
        return Order.builder()
                .user(u)
                .createdDate(LocalDateTime.now())
                .msg(orderDto.getOrder().getMsg())
                .couponNum(orderDto.getOrder().getCouponNum())
                .usedPoint(orderDto.getOrder().getUsedPoint())
                .savePoint(orderDto.getOrder().getSavePoint())
                .totalPrice(orderDto.getOrder().getTotalPrice())
                .orderDetails(orderDto.getOrderDetail())
                .delivery(orderDto.getDelivery())
                .build();
    }

    // 주문 상세 넣기
//    public OrderDetail createOrderDetail(OrderDetail detail) {
//        return OrderDetail.builder()
//                .order()
//                .product(detail.getProduct())
//                .prodCnt(detail.getProdCnt())
//                .build();
//    }
//
//    // 배송정보 넣기
//    public Delivery createDelivery(Delivery delivery) {
//        return Delivery.builder()
//                .order()
//                .name(delivery.getName())
//                .tel(delivery.getTel())
//                .addr(delivery.getAddr())
//                .deliveryMsg(delivery.getDeliveryMsg())
//                .receiveDate(delivery.getReceiveDate())
//                .build();
//    }
}
