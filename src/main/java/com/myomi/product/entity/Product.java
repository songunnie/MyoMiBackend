package com.myomi.product.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.cart.entity.Cart;
import com.myomi.order.entity.OrderDetail;
import com.myomi.qna.entity.Qna;
import com.myomi.seller.entity.Seller;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@SequenceGenerator(name = "PRODUCT_SEQ_GENERATOR", sequenceName = "PRODUCT_SEQ", initialValue = 1, allocationSize = 1)
public class Product {
	@Id
	@Column(name = "num")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	   generator = "PRODUCT_SEQ_GENERATOR")
	private Long prodNum;
	
	@ManyToOne
	@JoinColumn(name = "seller_id")
	private Seller seller;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "origin_price")
	private Long originPrice;
	
	@Column(name = "percentage")
	private int percentage;
	
	@Column(name = "week")
	private int week;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "detail")
	private String detail;

	@Column(name = "review_cnt")
	private Long reviewCnt;
	
	@Column(name = "stars")
	private float stars;
	
	@Column(name = "fee")
	@ColumnDefault("9")
	private int fee;

	@Column(name = "modified_date")
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private LocalDateTime modifiedDate;
	
	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<OrderDetail> orderDetails;
	
	@OneToMany(mappedBy = "prodNum", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Qna> qnas;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Cart> cart;
	
	private String productImgUrl;
	
	@Builder
	public Product(Long prodNum, Seller seller, String category, String name, Long originPrice, int percentage, int week, int status,
			String detail, int fee, Long reviewCnt, float stars, List<OrderDetail> orderDetails, String productImgUrl, LocalDateTime modifiedDate) {
		this.prodNum = prodNum;
		this.seller = seller;
		this.category = category;
		this.name = name;
		this.originPrice = originPrice;
		this.percentage = percentage;
		this.week = week;
		this.status = status;
		this.detail = detail;
		this.fee = fee;
		this.reviewCnt = reviewCnt;
		this.stars = stars;
		this.orderDetails = orderDetails;
		this.productImgUrl = productImgUrl;
		this.modifiedDate = modifiedDate;
	}
	
	//상품 등록한 셀러
	public void registerSeller(Seller seller) {
		this.seller = seller;
	}
	
	public void addProductImgUrl(String productImgUrl) {
		this.productImgUrl = productImgUrl;
	}
	
	public void update(String detail, int status, LocalDateTime modifiedDate) {
		this.detail = detail;
		this.status = status;
		this.modifiedDate = modifiedDate;
	}
}
