package com.aerchain.rfp.service;

import org.springframework.stereotype.Service;

@Service
public class ProposalEvaluationService {

    private static final double PRICE_WEIGHT = 0.7;
    private static final double DELIVERY_WEIGHT = 0.3;

    public double calculateScore(double price, int deliveryDays) {

        double priceScore = 1 / price;          // lower price → higher score
        double deliveryScore = 1.0 / deliveryDays; // faster delivery → higher score

        return (PRICE_WEIGHT * priceScore) +
                (DELIVERY_WEIGHT * deliveryScore);
    }
}
