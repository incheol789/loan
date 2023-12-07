package com.example.loan.controller;

import com.example.loan.dto.ApplicationDTO.Request;
import com.example.loan.dto.ApplicationDTO.Response;
import com.example.loan.dto.ResponseDTO;
import com.example.loan.service.ApplicationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/applications")
@RestController
public class ApplicationController extends AbstractController {

	private final ApplicationService applicationService;

	@PostMapping
	public ResponseDTO<Response> create(@RequestBody Request request) {
		return ok(applicationService.create(request));
	}

	@GetMapping("/{applicationId}")
	public ResponseDTO<Response> get(@PathVariable(name = "applicationId") Long applicationId) {
		return ok(applicationService.get(applicationId));
	}

	@PutMapping("/{applicationId}")
	public ResponseDTO<Response> update(@PathVariable(name = "applicationId") Long applicationId, @RequestBody Request request) {
		return ok(applicationService.update(applicationId, request));
	}

	@DeleteMapping("/{applicationId}")
	public ResponseDTO<Response> delete(@PathVariable(name = "applicationId") Long applicationId) {
		applicationService.delete(applicationId);
		
		return ok();
	}
}
