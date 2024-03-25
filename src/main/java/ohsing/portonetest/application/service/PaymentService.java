package ohsing.portonetest.application.service;

import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import ohsing.portonetest.presentation.dto.PaymentCallbackRequest;
import ohsing.portonetest.presentation.dto.RequestPayDto;

public interface PaymentService {

    // 결제 요청 데이터 조회
    RequestPayDto findRequestDto(String orderUid);

    // 결제 콜백 (callback)
    IamportResponse<Payment> paymentByCallback(PaymentCallbackRequest request);
}
