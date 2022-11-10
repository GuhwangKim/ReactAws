package com.example.demo.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;

@RestController
@RequestMapping(value = "/todo")
public class TodoController {
	@Autowired
	private TodoService service;
	
	@GetMapping("/test")
	public String testTodo(){
		String str =service.testService();
		return str;
	}
	
	//
	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto){
		try {
			String temporaryUserId = "temporary user";
			// 1) TodoEntity 로 변환
			TodoEntity entity=TodoDTO.toEntity(dto);
			
			// 2) id를 null 로 초기화, 생성 당시는 id 없어야하므로
			entity.setId(null);
			
			// 3) id 임시 사용자 아이디 설정 
			entity.setUserId(temporaryUserId);
			
			// 4) 서비스를 이용해 Todo Entity를 생성 
			List<TodoEntity> entities = service.create(entity);
			
			
			// 1) 자바 스트림을 통해 리턴된 엔티티 리스트를 TodoDTO 리스트로 반환  
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			// 2) 변환된 TodoDTO를 이용해 ResponseDTO를 초기
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			// 3) ResponseDTO 리턴 
			return ResponseEntity.ok().body(response);			
			
			// 4) 예외가 있는 경우 dto 대신 error 메세지를 넣어서 리턴 
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			
			return ResponseEntity.badRequest().body(response);	
		}
	}
	
	@GetMapping
	public ResponseEntity<?> retrieveTodoList(){
		String temporaryUserId = "temporary user";
		// 1) 서비스 메서드에서 TodoList를 가져온다 
		List<TodoEntity> entities = service.retrieve(temporaryUserId);
		
		// 2) 자바 스트림을 이용해서 리턴된 엔티티 메소드를 TodoDto 리스트로 변환한다 
		List<TodoDTO> dtos=entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		// 3) ResponseDto로 변환 
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		// 4) 리턴 
		return ResponseEntity.ok().body(response);
	}
	
}
