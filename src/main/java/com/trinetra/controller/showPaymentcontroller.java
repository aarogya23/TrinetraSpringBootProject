package com.trinetra.controller;

import com.trinetra.model.Payment;
import com.trinetra.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class showPaymentcontroller {

    @Autowired
    private PaymentRepository paymentRepository;

    // View page for displaying all payments
    @GetMapping("/paymentapi")
    public String showAllPayments(Model model) {
        System.out.println("PaymentController: /payments endpoint called");
        List<Payment> paymentList = paymentRepository.findAll();
        System.out.println("Found " + paymentList.size() + " payments");

        // Debug: print each payment
        for (Payment payment : paymentList) {
            System.out.println("Payment: " + payment.getFullname() +
                    " - " + payment.getUsername() +
                    " - Method: " + payment.getPaymentMethod() +
                    " - Amount: " + payment.getPaymentAmount() + "- Game name" + payment.getGameNames());
        }

        model.addAttribute("paymentList", paymentList);
        return "Paymentadmindisplay"; // Resolves to PaymentList.jsp or .html
    }

    // API endpoint to get all payments as JSON
    @GetMapping("/api/paymentapi")
    @ResponseBody
    public List<Payment> getAllPaymentsApi() {
        System.out.println("PaymentController: /api/payments API endpoint called");
        List<Payment> paymentList = paymentRepository.findAll();
        System.out.println("API: Found " + paymentList.size() + " payments");

        return paymentList;
    }

    // View to show total count of payments
    @GetMapping("/paymentapi/count")
    public String showPaymentCount(Model model) {
        long totalPayments = paymentRepository.count();
        model.addAttribute("totalPayments", totalPayments);
        return "PaymentCount"; // Optional view page
    }

    // API to return total count as JSON
    @GetMapping("/api/payments/count")
    @ResponseBody
    public long getPaymentCountApi() {
        System.out.println("PaymentController: /api/payments/count API called");
        long totalPayments = paymentRepository.count();
        System.out.println("API: Total payments: " + totalPayments);
        return totalPayments;
    }
}
