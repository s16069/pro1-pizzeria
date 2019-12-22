package pro.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.backend.model.PizzaSize;
import pro.backend.service.SizeService;

import java.util.List;

@RestController
@RequestMapping("/sizes")
public class SizeController {

	@Autowired
	private SizeService sizeService;

	@GetMapping
	public ResponseEntity<List<PizzaSize>> list() {

		List<PizzaSize> sizes = sizeService.list();
		
		return ResponseEntity.ok().body(sizes);
	}
}