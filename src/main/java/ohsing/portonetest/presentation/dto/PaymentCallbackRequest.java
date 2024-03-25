package ohsing.portonetest.presentation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaymentCallbackRequest { // 결제가 이루어진 뒤 서버가 전달받는 데이터
    private String paymentUid; // 결제 고유 번호
    private String orderUid; // 주문 고유 번호
}
