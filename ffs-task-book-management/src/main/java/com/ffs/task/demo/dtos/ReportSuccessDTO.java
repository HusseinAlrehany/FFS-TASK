package com.ffs.task.demo.dtos;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ReportSuccessDTO {
    private HttpStatus httpStatus;
    private String reportPath;
}
