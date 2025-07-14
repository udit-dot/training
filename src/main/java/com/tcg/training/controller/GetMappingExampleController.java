package com.tcg.training.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tcg")
public class GetMappingExampleController {

	@GetMapping("/users")
	public List<String> getUsers() {
	    return List.of("Alice", "Bob");
	}
	
	// 1. @PathVariable example
	@GetMapping("/hello/{name}/{id}")
	public String helloPathVariable(@PathVariable String name, @PathVariable Integer id) {
		return "Hello, " + name + "! User id : " + id;
	}

	// 2. @RequestParam example
	@GetMapping("/greet")
	public String greetRequestParam(@RequestParam String name, @RequestParam Integer id) {
		return "Greetings, " + name + "! User id : " + id;
	}

	// 3. @RequestBody example
	@GetMapping(value = "/echo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> echoRequestBody(@RequestBody Map<String, Object> payload) {
		payload.put("response", "User Data");
		return payload;
	}

}
