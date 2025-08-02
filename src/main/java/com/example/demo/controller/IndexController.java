package com.example.demo.controller;

//import追加
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//アノテーションを追加
@Controller
public class IndexController {
	// アノテーション付きのメソッド追加
	@RequestMapping("/")
	public String index() {
		return "index";
	}
}