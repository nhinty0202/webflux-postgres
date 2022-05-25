package com.nhi.nguyen.friendmanagement.dto;

import lombok.*;

import java.util.List;

public interface AddFriend {
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
    }
}
