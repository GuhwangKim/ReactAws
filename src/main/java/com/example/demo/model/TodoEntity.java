package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Todo") 
// 데이터에 테이블 이름 데이터베이스의 Todo 테이블과 매핑된다는 말 
// 명시하지 않으면 클래스 이름을 테이블 이름으로 간주 
public class TodoEntity {
	@Id
	// 기본키
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	// 임의로 id 생성 
	private String id;
	private String userId;
	private String title;
	private boolean done;

}
