package com.data.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.data.model.Line;

@Controller
@RequestMapping("/")
public class MVCController {
	@RequestMapping("/hello")
	public String hello()
	{
		return "hello";
	}
	
	@ResponseBody
	@RequestMapping("/json")
	public List get()
	{
		List a = new ArrayList<Line>();
		
		a.add(new Line("Microsoft",   "Amazon",   "licensing"));
		a.add(new Line("Microsoft",   "HTC",   "licensing"));
		a.add(new Line("Samsung",   "Apple",   "suit"));
		a.add(new Line("Motorola",   "Apple",   "suit"));
		a.add(new Line("Nokia",   "Apple",   "resolved"));
		a.add(new Line("HTC",   "Apple",   "suit"));
		a.add(new Line("Kodak",   "Apple",   "suit"));
		a.add(new Line("Microsoft",   "Barnes & Noble",   "suit"));
		a.add(new Line("Microsoft",   "Foxconn",   "suit"));
		a.add(new Line("Oracle",   "Google",   "suit"));
		a.add(new Line("Apple",   "HTC",   "suit"));
		a.add(new Line("Microsoft",   "Inventec",   "suit"));
		a.add(new Line("Samsung",   "Kodak",   "resolved"));
		a.add(new Line("LG",   "Kodak",   "resolved"));
		a.add(new Line("RIM",   "Kodak",   "suit"));
		a.add(new Line("Sony",   "LG",   "suit"));
		a.add(new Line("Kodak",   "LG",   "resolved"));
		a.add(new Line("Apple",   "Nokia",   "resolved"));
		a.add(new Line("Qualcomm",   "Nokia",   "resolved"));
		a.add(new Line("Apple",   "Motorola",   "suit"));
		a.add(new Line("Microsoft",   "Motorola",   "suit"));
		a.add(new Line("Motorola",   "Microsoft",   "suit"));
		a.add(new Line("Huawei",   "ZTE",   "suit"));
		a.add(new Line("Ericsson",   "ZTE",   "suit"));
		a.add(new Line("Kodak",   "Samsung",   "resolved"));
		a.add(new Line("Apple",   "Samsung",   "suit"));
		a.add(new Line("Kodak",   "RIM",   "suit"));
		a.add(new Line("Nokia",   "Qualcomm",   "suit"));
		
		return a;
	}
}