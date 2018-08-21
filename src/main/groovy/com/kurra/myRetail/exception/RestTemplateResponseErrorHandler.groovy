package com.kurra.myRetail.exception

import groovy.util.logging.Slf4j
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpResponse
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component
import org.springframework.util.FileCopyUtils
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.UnknownHttpStatusCodeException

import java.nio.charset.Charset

/**
 * User: Niranjan.kurra - Date: 8/21/18 7:16 AM
 */
@Component
@Slf4j
class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {

        return (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR)
    }

    @Override
    void handleError(ClientHttpResponse response)
            throws IOException {
        HttpStatus statusCode = response.getStatusCode()

        switch (statusCode.series()) {
            case HttpStatus.Series.CLIENT_ERROR:
                if (statusCode == HttpStatus.NOT_FOUND) {
                    throw new ResourceNotFoundException()
                }
                throw new HttpClientErrorException(statusCode, response.getStatusText(),
                        response.getHeaders(), getResponseBody(response), getCharset(response))
            case HttpStatus.Series.SERVER_ERROR:
                throw new HttpServerErrorException(statusCode, response.getStatusText(),
                        response.getHeaders(), getResponseBody(response), getCharset(response))
            default:
                throw new UnknownHttpStatusCodeException(statusCode.value(), response.getStatusText(),
                        response.getHeaders(), getResponseBody(response), getCharset(response))
        }
    }

    private byte[] getResponseBody(ClientHttpResponse response) {
        try {
            return FileCopyUtils.copyToByteArray(response.getBody())
        }
        catch (IOException ex) {
            // ignore
        }
        return new byte[0]
    }

    @Nullable
    protected Charset getCharset(ClientHttpResponse response) {
        HttpHeaders headers = response.getHeaders()
        MediaType contentType = headers.getContentType()
        return (contentType != null ? contentType.getCharset() : null)
    }
}
