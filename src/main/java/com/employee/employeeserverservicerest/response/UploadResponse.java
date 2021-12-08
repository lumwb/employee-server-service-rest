package com.employee.employeeserverservicerest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadResponse {
    private Integer success;
    private String error;

    public static UploadResponse success() {
        UploadResponse response = new UploadResponse();
        response.success = 1;
        return response;
    }

    public static UploadResponse failure() {
        UploadResponse response = new UploadResponse();
        response.setSuccess(0);
        return response;
    }

    // overload failure
    public static UploadResponse failure(String error) {
        UploadResponse response = new UploadResponse();
        response.setSuccess(0);
        response.setError(error);
        return response;
    }
}
