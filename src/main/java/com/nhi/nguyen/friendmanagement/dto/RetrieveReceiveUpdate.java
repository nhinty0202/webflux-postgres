package com.nhi.nguyen.friendmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public interface RetrieveReceiveUpdate {
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    class Request {
        String sender;
        String text;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    class Response  {
        private Boolean success;
        private List<String> recipients;
    }
}
