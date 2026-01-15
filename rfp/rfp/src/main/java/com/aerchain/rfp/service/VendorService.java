package com.aerchain.rfp.service;

import com.aerchain.rfp.dto.VendorRequest;
import com.aerchain.rfp.model.Vendor;
import com.aerchain.rfp.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;

    public Vendor createVendor(VendorRequest request) {
        Vendor v = new Vendor();
        v.setName(request.getName());
        v.setEmail(request.getEmail());
        return vendorRepository.save(v);
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }
}
