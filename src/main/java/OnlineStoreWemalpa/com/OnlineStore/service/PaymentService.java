package OnlineStoreWemalpa.com.OnlineStore.service;

import OnlineStoreWemalpa.com.OnlineStore.model.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> findAllPayment();
    Payment savePayment(Payment payment);
    Payment updatePayment(Payment payment);
    void deletePayment(Long id);
}
