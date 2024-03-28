package ohsing.portonetest.presentation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsing.portonetest.application.service.MemberService;
import ohsing.portonetest.application.service.OrderService;
import ohsing.portonetest.domain.entity.Member;
import ohsing.portonetest.domain.entity.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;
    private final OrderService orderService;

    @GetMapping("/order")
    public String order(@RequestParam(name = "message", required = false) String message,
                        @RequestParam(name = "orderUid", required = false) String id,
                        Model model) {

        model.addAttribute("message", message);
        model.addAttribute("orderUid", id);

        return "order";
    }

    @PostMapping("/order")
    public String autoOrder() {

        log.info("주문하기 버튼 클릭");

        Member member = memberService.autoRegister();
        Order order = orderService.autoOrder(member);

        String message = "주문실패";
        if (order != null) {
            message = "주문성공";
        }

        String encode = URLEncoder.encode(message, StandardCharsets.UTF_8);

        assert order != null;
        return "redirect:/order?message="+encode+"&orderUid="+order.getOrderUid();
    }
}
