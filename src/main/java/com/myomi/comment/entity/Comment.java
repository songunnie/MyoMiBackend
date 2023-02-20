package com.myomi.comment.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.myomi.board.entity.Board;
import com.myomi.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="comments")
@SequenceGenerator(
name = "COMMENTS_SEQ_GENERATOR",
sequenceName = "COMMENTS_SEQ", 
initialValue = 1, allocationSize = 1 )
@DynamicInsert
@DynamicUpdate
public class Comment {
	@Id
	@Column(name = "comment_num",  updatable =  false)
	@GeneratedValue(
				strategy = GenerationType.SEQUENCE,
				generator = "COMMENTS_SEQ_GENERATOR") 
	private Integer cNum;
	
    @ManyToOne
    @JoinColumn(name="board_num", nullable = false ,
    		                      updatable =  false)
	private Board board;
	//게시판 글 번호 -> 마이페이지에서 글 정보 필요함 
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false,
			                      updatable =  false)
	private User user;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "parent", updatable =  false)
	private Integer parent;
	//부모 댓글 번호 
	
	@Column(name = "created_date", updatable =  false)
	private LocalDateTime createdDate;
}
