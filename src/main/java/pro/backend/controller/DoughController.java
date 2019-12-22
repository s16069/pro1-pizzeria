package pro.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.backend.model.PizzaDough;
import pro.backend.service.DoughService;

import java.util.List;

@RestController
@RequestMapping("/doughs")
public class DoughController {

	@Autowired
	private DoughService doughService;

	@GetMapping
	public ResponseEntity<List<PizzaDough>> list() {

		List<PizzaDough> doughs = doughService.list();
		
		return ResponseEntity.ok().body(doughs);
	}
}