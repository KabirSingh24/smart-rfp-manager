package com.aerchain.rfp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AiService {

    private final WebClient openAiWebClient;

    public String extractRfpStructure(String userText) {

        try {
            String prompt = """
                    Convert the following procurement request into JSON.
                    
                    Fields:
                        - items (name, quantity, specs)
                        - budget
                        - delivery_days
                        - payment_terms
                        - warranty
                    
                    Return ONLY valid JSON.
                    
                    Text:
                    """ + userText;

            Map<String, Object> requestBody = Map.of(
                    "model", "llama-3.1-8b-instant",
                    "messages", List.of(
                            Map.of("role", "user", "content", prompt)
                    ),
                    "temperature", 0,
                    "max_tokens", 512
            );

            return openAiWebClient.post()
                    .uri("/chat/completions")
                    .bodyValue(requestBody)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.bodyToMono(String.class)
                                    .map(errorBody -> {
                                        System.err.println("Groq API Error: " + errorBody);
                                        return new RuntimeException(errorBody);
                                    })
                    )
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(20))
                    .block();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("AI service failed", e);
        }
    }


    public String extractVendorProposal(String emailText) {

        try {
            String prompt = """
                    Extract proposal details into JSON.
                    
                    Fields:
                    - total_price
                    - delivery_days
                    - warranty
                    - notes
                    
                    Return ONLY JSON.
                    
                    Email:
                    """ + emailText;

            Map<String, Object> requestBody = Map.of(
                    "model", "llama-3.1-8b-instant",
                    "messages", List.of(
                            Map.of("role", "user", "content", prompt)
                    ),
                    "temperature", 0,
                    "max_tokens", 512
            );


            return openAiWebClient.post()
                    .uri("/chat/completions")
                    .bodyValue(requestBody)
                    .retrieve()
                    .onStatus(
                            status -> status.isError(),
                            response -> response.bodyToMono(String.class)
                                    .map(body -> new RuntimeException(
                                            "Groq error: " + body
                                    ))
                    )
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(20))
                    .block();


        } catch (Exception e) {
            e.printStackTrace();
            return "...fallback...";
        }
    }
}
