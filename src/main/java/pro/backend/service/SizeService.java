package pro.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.backend.model.PizzaSize;

import java.util.Arrays;
import java.util.List;
@Service
@Transactional(readOnly = false)
public class SizeService {
	public List<PizzaSize> list() {
		return Arrays.asList(PizzaSize.values());
	}
}
