package com.chakrar.chartapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	@GetMapping("/")
    String index() {
        return "index";
    }
	
	@RequestMapping("/greeting")
	public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }	
	
	@RequestMapping("/showchart")
	public String showchart(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        return "showchart";
    }
	
	@RequestMapping("/showlinechart")
	public String showLineChart(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        return "showlinechart";
    }	
}
