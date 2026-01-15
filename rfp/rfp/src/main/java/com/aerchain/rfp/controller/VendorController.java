package com.aerchain.rfp.controller;

import com.aerchain.rfp.dto.VendorRequest;
import com.aerchain.rfp.model.Vendor;
import com.aerchain.rfp.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @PostMapping
    public Vendor create(@RequestBody VendorRequest request) {
        return vendorService.createVendor(request);
    }

    @GetMapping
    public List<Vendor> getAll() {
        return vendorService.getAllVendors();
    }
}
