package com.nhi.nguyen.friendmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.List;

public interface RetrieveFriend {
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    class Request {
        String email;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    class Response  {
        private Boolean success;
        private List<String> friends;
        private Integer count;
    }
}
