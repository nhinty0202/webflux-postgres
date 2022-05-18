package com.nhi.nguyen.friendmanagement.exception;

import com.nhi.nguyen.friendmanagement.contant.MessageConstant;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@Order(-2)
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources,
                                  ApplicationContext applicationContext, ServerCodecConfigurer codecConfigurer) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageWriters(codecConfigurer.getWriters());
        this.setMessageReaders(codecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::formatErrorResponse );
    }

    private Mono<ServerResponse> formatErrorResponse(ServerRequest request){
        Map<String, Object> errorAttributes  =  getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setSuccess(false);
        errorMessage.setMessage((String) Optional.ofNullable(errorAttributes.get("message")).orElse(MessageConstant.INTERNAL_SERVER_ERROR));
        int status = (int) Optional.ofNullable(errorAttributes.get("status")).orElse(500);
        return ServerResponse
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorMessage));

    }
}
