package com.nassir.documentcomparison.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nassir.documentcomparison.domain.ComparisonResponse;
import com.nassir.documentcomparison.domain.ComparisonResult;
import com.nassir.documentcomparison.service.ComparisonService;

@CrossOrigin(origins = { "http://localhost:5173",
		"http://localhost:3000",
		"https://comparify-frontend-production.up.railway.app",
		"https://comparify-frontend.vercel.app",
		"https://comparify.bynassir.dev"})
@RestController
public class ComparisonController {

	private static final Logger log = LoggerFactory.getLogger(ComparisonController.class);
	private final ComparisonService service;

	public ComparisonController (ComparisonService service){
		this.service = service;
	}

	@PostMapping("/api/compare")
	private ResponseEntity<ComparisonResponse> compare(@RequestParam MultipartFile file1,
			@RequestParam MultipartFile file2) {

		if (!"text/plain".equals(file1.getContentType()) || !"text/plain".equals(file2.getContentType())) {
			return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		}

        var result = new ComparisonResult(0,"");
		try {
			result = service.compare(new String(file1.getBytes()), new String(file2.getBytes()));
		} catch (IOException e) {
			log.error("Failed to read uploaded files", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new ComparisonResponse(result.similarityScore(), result.aiInsights()), HttpStatus.OK);
	}

}
