package com.nhi.nguyen.friendmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface AddRelation {
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    class Request {
        String requester;
        String target;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    class Response  {
        private Boolean success;
    }
}
