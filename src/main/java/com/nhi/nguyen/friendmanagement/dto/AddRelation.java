package com.nhi.nguyen.friendmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

public interface AddRelation {
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    class Request {
        String requestor;
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
