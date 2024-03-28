package ohsing.portonetest.application.service;

import com.siot.IamportRestClient.exception.IamportResponseException;

import java.io.IOException;

public interface RefundService {

    void refundRequestHttp(String accessToken, String merchant_uid, String reason) throws IOException;

    String refundRequest(String imp_uid) throws IamportResponseException, IOException;

    String getToken(String apiKey, String secretKey) throws IOException;
}
