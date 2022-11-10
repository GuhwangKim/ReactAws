package com.example.demo.controller;



import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;

@RestController // http 관련된 코드 및 요청/응답 매핑을 스프링이 알아서 
@RequestMapping(value = "test")
public class TestController {
	@GetMapping("/testRequestBody")
	public ResponseDTO<String> testControllerResponseBody(){
		List<String> list = new ArrayList<String>();
		list.add("Hello World");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return response;
	}

}

