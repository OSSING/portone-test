package ohsing.portonetest.presentation.controller;

import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsing.portonetest.application.service.PaymentService;
import ohsing.portonetest.presentation.dto.PaymentCallbackRequest;
import ohsing.portonetest.presentation.dto.RequestPayDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 요청 데이터 조회
    @GetMapping("/payment/{id}")
    public String paymentPage(@PathVariable(name = "id", required = false) String orderUid, Model model) {
        RequestPayDto requestDto = paymentService.findRequestDto(orderUid);
        model.addAttribute("requestDto", requestDto);
        return "payment";
    }

    // 결제 콜백
    @ResponseBody // 해당 메서드가 반환하는 값을 HTTP 응답의 Body로 사용하도록 지정
    @PostMapping("/payment")
    public ResponseEntity<IamportResponse<Payment>> validationPayment(@RequestBody PaymentCallbackRequest request) {

        // 결제 고유 번호와 주문 고유 번호를 통해 정상적인 결제인지 검증
        IamportResponse<Payment> iamportResponse = paymentService.paymentByCallback(request);

        log.info("결제 응답 : {}", iamportResponse.getResponse().toString());

        return new ResponseEntity<>(iamportResponse, HttpStatus.OK);
    }

    @GetMapping("/success-payment")
    public String successPaymentPage() {
        return "success-payment";
    }

    @GetMapping("/fail-payment")
    public String failPaymentPage() {
        return "fail-payment";
    }
}
