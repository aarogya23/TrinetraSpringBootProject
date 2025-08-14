package com.trinetra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.trinetra.model.UserClass;
import com.trinetra.repository.UserRepository;

import jakarta.mail.internet.MimeMessage;

@Controller
public class SignupController {
	@Autowired
	private JavaMailSender mailSender;

    @Autowired
    private UserRepository uRepo;

    @GetMapping("/signup")
    public String signupPage() {
        return "Signup"; // JSP or HTML view name
    }

    @PostMapping("/signup")
    public String signupPagePost(@ModelAttribute UserClass ur) {
    	
    	String username=ur.getUsername();
		String password=ur.getPassword();
		String email = ur.getEmail();	
		
        uRepo.save(ur);
        
        
        //sending email to the user who register from the 
        //application based upon the email address
        
        SimpleMailMessage message = new SimpleMailMessage();
        
//        message.setTo(email);
//        message.setSubject("Account Creation");
//        message.setText("Congratulation !! You have Successfully Login to our Trinetra Game Store");
//        
        try {
        	
        	
        	//Creating MiMe message for HTML

        	MimeMessage mimeMessage = mailSender.createMimeMessage();
        	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        	
        	
        	helper.setTo(email);
        	helper.setSubject("Account Creation - Trinetra Games Store");
        	
        	
        	//lets add html content for footer and logo
        	// HTML content with footer and logo
            String htmlContent = """
                    <div style="font-family: Arial, sans-serif; padding: 20px; background-color: #f9f9f9;">
                        <h2 style="color: #ff6600;">Welcome to Trinetra Game Store 🎮</h2>
                        <p>Dear <b>%s</b>,</p>
                        <p>Congratulations! You have successfully registered with us.</p>
                        
                        <hr style="margin: 20px 0;">

                        <p style="font-size: 14px; color: #555;">Enjoy the best games at unbeatable prices.</p>

                        <!-- Footer with logo -->
                        <div style="margin-top: 30px; text-align: center; padding-top: 10px; border-top: 1px solid #ddd;">
                            <img src=""  alt="Trinetra Logo" style="width: 120px; margin-bottom: 10px;">
                            <p style="font-size: 12px; color: #777;">© 2025 Trinetra Game Store. All rights reserved.</p>
                        </div>
                    </div>
                    """.formatted(username);

        	helper.setText(htmlContent,true);
        	mailSender.send(mimeMessage);
        	System.out.println("Email sent successfully with HTML Footer");
        }catch (Exception e) {
			System.err.println("Error sending email :" +e.getMessage());
		}
        return "Home"; // Redirect to front page after signup
    }
}
