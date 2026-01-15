package com.aerchain.rfp.controller;

import com.aerchain.rfp.dto.CreateRfpRequest;
import com.aerchain.rfp.model.Rfp;
import com.aerchain.rfp.service.RfpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rfps")
@RequiredArgsConstructor
public class RfpController {

    private final RfpService rfpService;

    @PostMapping
    public Rfp create(@RequestBody CreateRfpRequest request) {
        return rfpService.createRfp(request.getText());
    }

    @PostMapping("/send")
    public Rfp createAndSend(@RequestBody CreateRfpRequest request) {
        return rfpService.createAndSendRfp(
                request.getText(),
                request.getVendorIds()
        );
    }

}
