package com.perfios.banking.dto;

import lombok.Data;

@Data
public class ResponseFileDTO {
    private String name;
    private String url;
    private String type;
    private long size;
}