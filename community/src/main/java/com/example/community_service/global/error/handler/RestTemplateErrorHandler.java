package com.example.community_service.global.error.handler;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
@Slf4j
public class RestTemplateErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(@NonNull final ClientHttpResponse response) throws IOException {
        final HttpStatusCode statusCode = response.getStatusCode();
        // response.getBody() 넘겨 받은 body 값으로 적절한 예외 상태 확인 이후 boolean return
        return !statusCode.is2xxSuccessful();
    }

    @Override
    public void handleError(@NonNull final ClientHttpResponse response) throws IOException {
        // hasError에서 true를 return하면 해당 메서드 실행
        // 상황에 알맞는 Error handling 로직 작성
        logErrorResponse(response);
    }

    private void logErrorResponse(ClientHttpResponse response) throws IOException {

        StringBuilder resLog = new StringBuilder();
        resLog.append("\n[RESPONSE Status code] ")
                .append(response.getStatusCode());
        //body는 Stream 이므로 한번 읽으면 다시 읽지 못한다.
        //.append("\n[RESPONSE Response Body] ")
        //.append(StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));

        log.error(resLog.toString());
    }
}
