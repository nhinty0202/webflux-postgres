package com.nhi.nguyen.friendmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public interface RetrieveFriendBetween {
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    class Request {
        List<String> friends;
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
