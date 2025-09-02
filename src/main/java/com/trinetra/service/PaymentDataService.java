package com.trinetra.service;

import com.trinetra.model.Game;
import com.trinetra.model.PaymentData;
import com.trinetra.repository.PaymentDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentDataService {

    @Autowired
    private PaymentDataRepository paymentDataRepository;

    public String validatePaymentDetails(String paymentMethod, String phone, String email,
                                        String khaltiMobile, String esewaId, String imepayMobile, String bankName,
                                        Game game) {
        if (phone == null || phone.isEmpty()) {
            return "Phone number is required";
        }
        if (email == null || email.isEmpty()) {
            return "Email is required";
        }

        PaymentData paymentData = paymentDataRepository.findByPhone(phone).orElse(null);
        if (paymentData == null) {
            return "Phone number not registered";
        }
        if (!paymentData.getEmail().equalsIgnoreCase(email)) {
            return "Email does not match registered phone number";
        }

        switch (paymentMethod) {
            case "khalti":
                if (khaltiMobile == null || !khaltiMobile.equals(paymentData.getKhaltiMobile())) {
                    return "Khalti mobile number does not match";
                }
                if (!paymentData.isKhaltiVerified()) {
                    return "Khalti account is not KYC-verified. Please complete KYC.";
                }
                break;
            case "esewa":
                if (esewaId == null || !esewaId.equals(paymentData.getEsewaId())) {
                    return "eSewa ID does not match";
                }
                break;
            case "imepay":
                if (imepayMobile == null || !imepayMobile.equals(paymentData.getImepayMobile())) {
                    return "IME Pay mobile number does not match";
                }
                break;
            case "bank":
                if (bankName == null || !bankName.equals(paymentData.getBankName())) {
                    return "Bank name does not match";
                }
                break;
            case "card":
                break;
            default:
                return "Invalid payment method";
        }

        if (paymentMethod.equals("khalti") || paymentMethod.equals("esewa") || paymentMethod.equals("imepay")) {
            if (paymentData.getBalance().compareTo(game.getGameprice()) < 0) {
                return "Balance isn't available to buy the game product";
            }
        }

        return null; // No errors
    }

    public void deductBalance(String phone, BigDecimal amount) {
        PaymentData paymentData = paymentDataRepository.findByPhone(phone)
            .orElseThrow(() -> new IllegalStateException("PaymentData not found after validation"));
        paymentData.setBalance(paymentData.getBalance().subtract(amount));
        paymentDataRepository.save(paymentData);
    }
}