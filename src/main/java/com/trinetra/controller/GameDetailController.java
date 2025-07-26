package com.trinetra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameDetailController {
	
	 @GetMapping("/eafc25")
	    public String eafcMapping() {
	    	
	        return "eafc25"; // JSP or HTML view name
	    }

	 @GetMapping("/cart")
	 public String cartpageMapping() {
	    	
	        return "cart"; // JSP or HTML view name
	    }
}
