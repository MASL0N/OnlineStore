package OnlineStoreWemalpa.com.OnlineStore.service.impl;

import OnlineStoreWemalpa.com.OnlineStore.model.Payment;
import OnlineStoreWemalpa.com.OnlineStore.repository.PaymentRepository;
import OnlineStoreWemalpa.com.OnlineStore.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repository;
    @Override
    public List<Payment> findAllPayment() {
        return repository.findAll();
    }

    @Override
    public Payment savePayment(Payment payment) {
        return repository.save(payment);
    }

    @Override
    public Payment updatePayment(Payment payment) {
        return repository.save(payment);
    }

    @Override
    public void deletePayment(Long id) {
        repository.deleteById(id);
    }
}
