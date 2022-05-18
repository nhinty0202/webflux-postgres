package com.nhi.nguyen.friendmanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorMessage {
    private Boolean success;
    private String message;
}
