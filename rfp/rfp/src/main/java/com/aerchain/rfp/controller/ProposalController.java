package com.aerchain.rfp.controller;

import com.aerchain.rfp.dto.VendorResponseRequest;
import com.aerchain.rfp.model.Proposal;
import com.aerchain.rfp.repository.ProposalRepository;
import com.aerchain.rfp.service.AiService;
import com.aerchain.rfp.service.ProposalEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/proposals")
@RequiredArgsConstructor
public class ProposalController {

    private final AiService aiService;
    private final ProposalRepository proposalRepository;
    private final ProposalEvaluationService proposalEvaluationService;

    @PostMapping("/parse")
    public Proposal parseVendorProposal(@RequestBody VendorResponseRequest request) {

        String extractedJson =
                aiService.extractVendorProposal(request.getEmailText());

        Proposal proposal = new Proposal();
        proposal.setRfpId(request.getRfpId());
        proposal.setVendorId(request.getVendorId());
        proposal.setExtractedProposal(extractedJson);

        return proposalRepository.save(proposal);
    }


    @GetMapping("/rfp/{rfpId}/evaluate")
    public List<Proposal> evaluateProposals(@PathVariable Long rfpId) throws Exception {

        List<Proposal> proposals = proposalRepository.findByRfpId(rfpId);
        ObjectMapper mapper = new ObjectMapper();

        for (Proposal proposal : proposals) {

            String extracted = proposal.getExtractedProposal();

            // Parse full AI response
            JsonNode root = mapper.readTree(extracted);

            // Navigate to content
            JsonNode contentNode = root
                    .get("choices")
                    .get(0)
                    .get("message")
                    .get("content");

            // Parse actual JSON inside content
            JsonNode actualJson = mapper.readTree(contentNode.asText());

            double price = actualJson.get("total_price").asDouble();
            int deliveryDays = actualJson.get("delivery_days").asInt();

            double score =
                    proposalEvaluationService.calculateScore(price, deliveryDays);

            proposal.setScore(score);
        }

        return proposalRepository.saveAll(proposals);
    }

}
