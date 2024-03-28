package ohsing.portonetest.application.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsing.portonetest.application.service.RefundService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final IamportClient iamportClient;

    // AccessToken과 주문 정보, 환불 사유를 포트원에 요청하여 결제 취소 처리하는 메서드
    @Override
    public void refundRequestHttp(String accessToken, String merchant_uid, String reason) throws IOException {
        URL url = new URL("https://api.iamport.kr/payments/cancel");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        // 요청 방식을 POST로 설정
        conn.setRequestMethod("POST");

        // 요청의 Content-Type, Accept, Authorization 헤더 설정
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", accessToken);

        // 해당 연결을 출력 스트림(요청)으로 사용
        conn.setDoOutput(true);

        // JSON 객체에 해당 API가 필요로하는 데이터 추가
        JsonObject json = new JsonObject();
        json.addProperty("merchant_uid", merchant_uid);
        json.addProperty("reason", reason);

        // 출력 스트림으로 해당 conn 요청
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(json.toString());
        bw.flush();
        bw.close();

        // 입력 스트림으로 conn 요청에 대한 응답 반환
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        br.close();
        conn.disconnect();

        log.info("결제 취소 완료!! 주문 번호 : {} ", merchant_uid);
    }

    @Override
    public String refundRequest(String imp_uid) throws IamportResponseException, IOException {

        // 결제 단건 조회 (포트원)
        IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(imp_uid);
        log.info("결제 단건 조회 결과 : {}", iamportResponse.toString());

        // 결제된 가격 조회
        int iamportPrice = iamportResponse.getResponse().getAmount().intValue();

        // 결제 취소 (포트원)
        // new BigDecimal(iamportPrice) 부분은 얼마를 환불해줄지 정하는 부분이며, CheckSum을 받아 포트원에서 처리한다.
        IamportResponse<Payment> cancelResponse =
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getResponse().getImpUid(), true, new BigDecimal(iamportPrice)));

        log.info("결제 취소 결과 : {}", cancelResponse.toString());

        if (cancelResponse.getCode() == 0) {
            return "결제 취소가 성공적으로 완료되었습니다! : " + cancelResponse.getMessage();
        } else {
            return "결제 취소에 실패하였습니다! : " + cancelResponse.getMessage();
        }
    }

    // 포트원에 apiKey와 secretKey를 요청하여 반환 받은 AccessToken을 반환하는 메서드
    @Override
    public String getToken(String apiKey, String secretKey) throws IOException {
        URL url = new URL("https://api.iamport.kr/users/getToken");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        // 요청 방식을 POST로 설정
        conn.setRequestMethod("POST");

        // 요청의 Content-Type과 Accept 헤더 설정
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");

        // 해당 연결을 출력 스트림(요청)으로 사용
        conn.setDoOutput(true);

        // JSON 객체에 해당 API가 필요로하는 데이터 추가
        JsonObject json = new JsonObject();
        json.addProperty("imp_key", apiKey);
        json.addProperty("imp_secret", secretKey);

        // 출력 스트림으로 해당 conn에 요청
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(json.toString());
        bw.flush();
        bw.close();

        // 입력 스트림으로 conn 요청에 대한 응답 반환
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        Gson gson = new Gson(); // 응답 데이터를 자바 객체로 변환
        String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();
        String accessToken = gson.fromJson(response, Map.class).get("access_token").toString();
        br.close();

        conn.disconnect();

        log.info("Iamport AccessToken 발급 성공!! : {}", accessToken);

        return accessToken;
    }
}
