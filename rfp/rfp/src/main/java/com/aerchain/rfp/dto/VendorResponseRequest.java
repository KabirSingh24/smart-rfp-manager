package com.aerchain.rfp.dto;

import lombok.Data;

@Data
public class VendorResponseRequest {
    private Long rfpId;
    private Long vendorId;
    private String emailText;
}
