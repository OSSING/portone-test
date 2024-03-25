package ohsing.portonetest.presentation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RequestPayDto { // view로 전달할 결제 관련 데이터
    private String orderUid;
    private String itemName;
    private String buyerName;
    private Long paymentPrice;
    private String buyerEmail;
    private String buyerAddress;

    @Builder
    public RequestPayDto(String orderUid, String itemName, String buyerName, Long paymentPrice, String buyerEmail, String buyerAddress) {
        this.orderUid = orderUid;
        this.itemName = itemName;
        this.buyerName = buyerName;
        this.paymentPrice = paymentPrice;
        this.buyerEmail = buyerEmail;
        this.buyerAddress = buyerAddress;
    }
}
