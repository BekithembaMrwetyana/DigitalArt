package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Discount;
import za.ac.cput.repository.ContactRepository;
import za.ac.cput.repository.DiscountRepository;

import java.util.List;

@Service
public class DiscountService implements IDiscountService{

    private final DiscountRepository repository;

    @Autowired
    public DiscountService(DiscountRepository repository) {
        this.repository = repository;
    }

    public Discount create(Discount discount) {
        return repository.save(discount);
    }

    public Discount read(Long discountID) {
        return repository.findById(discountID).orElse(null);
    }

    public Discount update(Discount discount) {
        return repository.save(discount);
    }

    public void delete(Long discountID) {
        repository.deleteById(discountID);
    }

    public List<Discount> getAll() {
        return repository.findAll();
    }
}
