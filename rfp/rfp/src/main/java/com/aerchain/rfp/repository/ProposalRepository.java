package com.aerchain.rfp.repository;

import com.aerchain.rfp.model.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProposalRepository extends JpaRepository<Proposal,Long> {

    List<Proposal> findByRfpId(Long aLong);
}
