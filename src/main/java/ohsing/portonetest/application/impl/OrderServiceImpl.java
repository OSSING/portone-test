package ohsing.portonetest.application.impl;

import lombok.RequiredArgsConstructor;
import ohsing.portonetest.application.service.OrderService;
import ohsing.portonetest.domain.entity.Member;
import ohsing.portonetest.domain.entity.Order;
import ohsing.portonetest.domain.entity.Payment;
import ohsing.portonetest.domain.entity.PaymentStatus;
import ohsing.portonetest.domain.repository.OrderRepository;
import ohsing.portonetest.domain.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;


    @Override
    public Order autoOrder(Member member) {
        // 임시 결제내역 생성
        Payment payment = Payment.builder()
                .price(100L)
                .status(PaymentStatus.READY)
                .build();

        paymentRepository.save(payment);

        // 주문 생성
        Order order = Order.builder()
                .member(member)
                .price(100L)
                .itemName("RealMoment 화장품")
                .orderUid(UUID.randomUUID().toString())
                .payment(payment)
                .build();

        return orderRepository.save(order);
    }
}
