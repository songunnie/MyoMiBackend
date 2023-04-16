package com.myomi.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.myomi.product.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>, ProductCustomRepository{
	//셀러가 등록한 모든 상품 찾기
	List<Product> findAllBySellerIdOrderByReviewCntDesc(String sellerId);
	
	//셀러가 등록한 특정 상품 찾기
	Optional<Product> findBySellerIdAndProdNum(String sellerId, Long prodNum);
	
	//특정 상품 조회하기
	Optional<Product> findById(Long prodNum);

	//기본으로 status가 판매중인 상품만 보이게. 
	List<Product> findAllByStatusOrderByWeek(int status);
	
	//키워드로 상품찾기
	List<Product> findAllByNameContaining(String keyword);
	
	//모든 상품 보기
	List<Product> findAll();	
}
