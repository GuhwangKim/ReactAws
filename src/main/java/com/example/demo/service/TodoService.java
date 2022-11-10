package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoService {
	@Autowired
	private TodoRepository repository;
	// 의존성 주입

	public String testService() {
		// TodoEntity 생성
		TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
		// TodoEntity 저장
		repository.save(entity);
		// TodoEntity 검색
		TodoEntity savedEntity = repository.findById(entity.getId()).get();
		return savedEntity.getTitle();
	}
	
	// Post 
	public List<TodoEntity> create(final TodoEntity entity) {
		// validation
		repository.save(entity);

		log.info("Entity Id : {} is saved", entity.getId());

		return repository.findByUserId(entity.getUserId());
	}
	
	// Get
	public List<TodoEntity> retrieve(final String userId) {
		return repository.findByUserId(userId);
	}
	
	// Put
	public List<TodoEntity> update(final TodoEntity entity){
		// 1) 저장할 entity가 있는지 확인 
		validate(entity);
		
		// 2) 넘겨받은 id를 이용해 TodoEntity 를 가져옴 
		final Optional<TodoEntity> original = repository.findById(entity.getId());
		
		// 3) 반환된 TodoEntity가 존재하면 새 entity 값으로 덮어 씌운다 
		original.ifPresent(todo->{
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
		// 4) 데이터베이스에 새 값을 저장
			repository.save(todo);
		});
		// retrieve에서 만든 메소드를 이용해 사용자의 모든 Todo 리스트 반환
		return retrieve(entity.getUserId());
	}
		
	
	

	// Refactoring 다른 메서드에서도 계속 쓰일 예정이여서 따로 method로 정리 
	private void validate(final TodoEntity entity) {
		// validation
		if (entity == null) {
			log.warn("Entity cannot be null");
			throw new RuntimeException("Entity cannot be null");
		}
		if (entity.getUserId() == null) {
			log.warn("Unknown user");
			throw new RuntimeException("Unknown user");
		}
	}

}
