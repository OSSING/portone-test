package ohsing.portonetest.application.service;

import java.io.IOException;

public interface RefundService {

    void refundRequest(String accessToken, String merchant_uid, String reason) throws IOException;

    String getToken(String apiKey, String secretKey) throws IOException;
}
