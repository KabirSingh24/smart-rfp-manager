package com.aerchain.rfp.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateRfpRequest {
    private String text;
    private List<Long> vendorIds;
}
