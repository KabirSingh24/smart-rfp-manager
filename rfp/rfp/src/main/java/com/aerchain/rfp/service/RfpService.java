package com.aerchain.rfp.service;

import com.aerchain.rfp.model.Rfp;
import com.aerchain.rfp.model.Vendor;
import com.aerchain.rfp.repository.RfpRepository;
import com.aerchain.rfp.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RfpService {


    private final RfpRepository rfpRepository;
    private final VendorRepository vendorRepository;
    private final EmailService emailService;
    private final AiService aiService;

    public Rfp createAndSendRfp(String text, List<Long> vendorIds) {

        String aiResponse = aiService.extractRfpStructure(text);

        String pureJson = extractJsonFromAiResponse(aiResponse);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(pureJson);

        Rfp rfp = new Rfp();
        rfp.setOriginalText(text);
        rfp.setStructuredData(pureJson);

        if (json.has("budget") && !json.get("budget").isNull()) {
            rfp.setBudget(json.get("budget").asDouble());
        }

        Rfp saved = rfpRepository.save(rfp);

        List<Vendor> vendors = vendorRepository.findAllById(vendorIds);

        for (Vendor v : vendors) {
            emailService.sendRfpEmail(
                    v.getEmail(),
                    "New RFP Request",
                    pureJson
            );
        }

        return saved;
    }


    public Rfp createRfp(String text) {

        String aiResponse = aiService.extractRfpStructure(text);

        String pureJson = extractJsonFromAiResponse(aiResponse);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(pureJson);

        Rfp rfp = new Rfp();
        rfp.setOriginalText(text);
        rfp.setStructuredData(pureJson);

        if (json.has("budget") && !json.get("budget").isNull()) {
            rfp.setBudget(json.get("budget").asDouble());
        }

        return rfpRepository.save(rfp);
    }

    private String extractJsonFromAiResponse(String aiResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(aiResponse);

            String content = root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            // remove ```json ``` wrapper
            int start = content.indexOf("{");
            int end = content.lastIndexOf("}");
            if (start == -1 || end == -1) {
                throw new RuntimeException("No JSON found in AI content");
            }

            return content.substring(start, end + 1);

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response", e);
        }
    }

}

