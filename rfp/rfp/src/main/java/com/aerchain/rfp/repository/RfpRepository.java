package com.aerchain.rfp.repository;

import com.aerchain.rfp.model.Rfp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RfpRepository extends JpaRepository<Rfp,Long> {
}
