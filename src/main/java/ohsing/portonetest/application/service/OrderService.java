package ohsing.portonetest.application.service;

import ohsing.portonetest.domain.entity.Member;
import ohsing.portonetest.domain.entity.Order;

public interface OrderService {

    Order autoOrder(Member member); // 주문하기 버튼을 누르면 자동으로 상품을 주문
}
