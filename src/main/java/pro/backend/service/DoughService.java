package pro.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.backend.model.PizzaDough;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class DoughService {
	public List<PizzaDough> list() {
		return Arrays.asList(PizzaDough.values());
	}
}
