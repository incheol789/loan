package com.example.loan.controller;

import com.example.loan.dto.CounselDTO.Request;
import com.example.loan.dto.CounselDTO.Response;
import com.example.loan.dto.ResponseDTO;
import com.example.loan.service.CounselService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/counsels")
@RestController
public class CounselController extends AbstractController {

	private final CounselService counselService;

	@PostMapping
	public ResponseDTO<Response> create(@RequestBody Request request) {
		return ok(counselService.create(request));
	}

	@GetMapping("/{counselId}")
	public ResponseDTO<Response> get(@PathVariable(name = "counselId") Long counselId) {
		return ok(counselService.get(counselId));
	}

	@PutMapping("/{counselId}")
	public ResponseDTO<Response> update(@PathVariable(name = "counselId") Long counselId, @RequestBody Request request) {
		return ok(counselService.update(counselId, request));
	}

	@DeleteMapping("/{counselId}")
	public ResponseDTO<Response> delete(@PathVariable(name = "counselId") Long counselId) {
		counselService.delete(counselId);
		return ok();
	}
}
