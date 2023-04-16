package com.myomi.product.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myomi.common.status.AddException;
import com.myomi.common.status.ErrorCode;
import com.myomi.common.status.ExceedMaxUploadSizeException;
import com.myomi.common.status.NoResourceException;
import com.myomi.common.status.ResponseDetails;
import com.myomi.common.status.TokenValidFailedException;
import com.myomi.common.status.UnqualifiedException;
import com.myomi.product.dto.ProductDto;
import com.myomi.product.dto.ProductReadOneDto;
import com.myomi.product.dto.ProductSaveDto;
import com.myomi.product.dto.ProductUpdateDto;
import com.myomi.product.entity.Product;
import com.myomi.product.repository.ProductRepository;
import com.myomi.qna.repository.QnaRepository;
import com.myomi.review.repository.ReviewRepository;
import com.myomi.s3.FileUtils;
import com.myomi.s3.S3Uploader;
import com.myomi.seller.entity.Seller;
import com.myomi.seller.repository.SellerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
	@Autowired
	private S3Uploader s3Uploader;
	private final ProductRepository productRepository;
	private final SellerRepository sellerRepository;
	private final ReviewRepository reviewRepository;
	private final QnaRepository qnaRepository;
	private LocalDateTime date = LocalDateTime.now();
	/**
	 * TODO:
	 * 1. 셀러 등록 후 상품 등록하기
	 * 2. 등록된 상품 리스트 조회
	 * 3. 셀러별 상품 리스트 조회
	 * 4. 상품 상세 조회
	 * 5. 상품 수정
	 * 6. 상품 삭제 
	 */
	
	//상품등록
	@Transactional
	public ResponseDetails addProduct(ProductSaveDto productSaveDto, Authentication seller) throws NoResourceException,IOException,UnqualifiedException,ExceedMaxUploadSizeException,AddException {
		String path = "/api/product";
		log.info("상품 등록이 가능한 판매자인지 확인합니다. [sellerId : []", seller.getName());
		Seller s = sellerRepository.findById(seller.getName())
				.orElseThrow(() -> new TokenValidFailedException(ErrorCode.UNAUTHORIZED, "로그인한 판매자만 상품등록이 가능합니다."));
			
		if(s.getStatus() == 3) {
			log.info("탈퇴 신청한 판매자가 상품등록을 시도. [sellerId : {}]", s.getId());
			throw new UnqualifiedException(ErrorCode.BAD_REQUEST, "UNQUALIFIED_SELLER");
		}
		
		if(productSaveDto.getName().length() > 30) {
			log.info("상품명 글자수 30자 초과. [prodName : {}", productSaveDto.getName().length());
			throw new AddException(ErrorCode.BAD_REQUEST, "EXCEED_MAX_CHAR");
		}
		
		MultipartFile file = productSaveDto.getFile();
		
		if(file != null) {
			InputStream inputStream = file.getInputStream();
			
			boolean isValid = FileUtils.validImgFile(inputStream);
			
			if(!isValid) {
				throw new ExceedMaxUploadSizeException(ErrorCode.BAD_REQUEST,"EXCEED_FILE_SIZE");
			}
		} else {
			log.info("첨부된 이미지 파일이 없습니다.");
			throw new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "NOT_FOUND_FILE");
		}

		String fileUrl = s3Uploader.upload(file, "상품이미지", seller, productSaveDto);
		
		Product product = productSaveDto.toEntity(productSaveDto, s, fileUrl);
		
		//상품등록
		productRepository.save(product);
		return new ResponseDetails(product.getProdNum(), 200, path);
	}
	
	//판매자모드 - 판매 상품 조회
	@Transactional
	public ResponseDetails getProductBySellerId(String sellerId) throws NoResourceException{
		String path = "/api/product";
		List<Product> prods = productRepository.findAllBySellerIdOrderByReviewCntDesc(sellerId);
		List<ProductDto> list = new ArrayList<>();
		if (prods.size() == 0) {
		        log.info("등록된 상품이 없습니다.");
		        throw new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "PRODUCT_NOT_FOUND");
		} else {
		    for (Product p : prods) {
		    	ProductDto dto = new ProductDto();
		        list.add(dto.toDto(p));
		    }
		}
		return new ResponseDetails(list, 200, path);
	}
	
	//일반모드 - 상품 상세 조회 + 베스트리뷰,리뷰,문의
	@Transactional
	public ResponseDetails getOneProd(Long prodNum) throws NoResourceException {
		String path = "/api/product";
		List<ProductReadOneDto> product = productRepository.findProdInfo(prodNum);
		if(product == null) {
			throw new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "NOT_EXIST_PRODUCT");
		}
		return new ResponseDetails(product, 200, path);
	}
	
	//상품 정보 수정
	@Transactional
	public ResponseDetails modifyProduct(Long prodNum, ProductUpdateDto productUpdateDto, Authentication seller) throws NoResourceException {
		String path = "/api/product";
		Product p = productRepository.findBySellerIdAndProdNum(seller.getName(), prodNum)
				.orElseThrow(() -> new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "PRODUCT_NOT_FOUND"));
		
		if(p != null) {
			p.update(productUpdateDto.getDetail(), productUpdateDto.getStatus(), date);
		} else {
			log.info("존재하지 않는 상품을 수정할 수 없습니다.");
			throw new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "NOT_FOUND_PRODUCT");
		}
		return new ResponseDetails(p.getProdNum(), 200, path);
	}
	
	//상품 삭제
	@Transactional
	public ResponseDetails removeProduct(Long prodNum, Authentication seller) throws NoResourceException {
		String path = "/api/product";
		Product p = productRepository.findById(prodNum)
				.orElseThrow(() -> new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "PRODUCT_NOT_FOUND"));
		Seller s = sellerRepository.findById(seller.getName())
				.orElseThrow(() -> new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND, "NOT_FOUND_SELLER"));
		if(p.getSeller().getId() != s.getId()) {
			throw new UnqualifiedException(ErrorCode.BAD_REQUEST, "DISCORD_SELLER");
		}
		productRepository.deleteById(prodNum);
		return new ResponseDetails(p.getProdNum(), 200, path);
	}
	
	//일반모드 - 모든 상품 리스트 조회
	@Transactional
	public ResponseDetails getAllProduct() { 
		String path = "/api/product";
		List<Product> pList = productRepository.findAll();
		
		if(pList.size() == 0) {
			log.info("등록된 상품이 없습니다.");
		}
		
		List<ProductDto> list = new ArrayList<>();
		
		for(Product p : pList) {
			ProductDto dto = new ProductDto();
	        list.add(dto.toDto(p));
		}
		return new ResponseDetails(list, 200, path);
	}
	
	//일반모드 - 상품 키워드로 검색
	@Transactional
	public ResponseDetails getAllProduct(String keyword) {
		String path = "/api/product";
		List<Product> pList = productRepository.findAllByNameContaining(keyword);
		List<ProductDto> list = new ArrayList<>();
		
		if(pList.size() == 0) {
			log.error("해당 키워드가 포함된 상품이 없습니다.");
		}
		
		for(Product p : pList) {
			ProductDto dto = new ProductDto();
			list.add(dto.toDto(p));
		}
		
		return new ResponseDetails(list, 200, path);
	}
	
	//판매자모드 - 판매자 판매 상품 상세 조회
	@Transactional
	public ResponseDetails getOneProdBySeller(Long prodNum, Authentication seller) throws NoResourceException {
		String path = "/api/product";

		Product product = productRepository.findBySellerIdAndProdNum(seller.getName(),prodNum)
				.orElseThrow(()-> new NoResourceException(ErrorCode.RESOURCE_NOT_FOUND,"PRODUCT_NOT_FOUND"));

		ProductDto dto = new ProductDto();
		
		return new ResponseDetails(dto.toDto(product), 200, path);
	}
}
