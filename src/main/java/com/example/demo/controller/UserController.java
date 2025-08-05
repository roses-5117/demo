package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.data.entity.User;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.form.UserForm;

@Controller
public class UserController {
	// @Autowiredアノテーションを付けると、Spring Bootが自動でインスタンスをInjectします。
	@Autowired
	private UserRepository userRepository;

	// @RequestMapping(path = "/user", method = RequestMethod.GET)の省略版。
	// HTTPのメソッドGETのみ受け付けます。
	@GetMapping("/users")
	// 引数にorg.springframework.ui.Modelを追加
	public String getUsers(Model model) {
		// ユーザーリスト取得処理を追加
		List<User> users = userRepository.findAll();
		// 取得したリストをテンプレートに渡す
		model.addAttribute("users", users);
		return "users";
	}

	// getNewUserメソッドを追加
	@GetMapping("/newuser")
	public String getNewUser(Model model) {
		// Modelに空のUserFormを追加
		UserForm userForm = new UserForm();
		model.addAttribute("userForm", userForm);
		// テンプレートは src/main/resources/templates/newuser.html とします。
		return "newuser";
	}

	// マッピング設定
	@PostMapping("/newuser")
	// 引数のuserFormにValidatedアノテーションを追加
	public String registerUser(@Validated UserForm userForm) {
		User user = new User();
		user.setName(userForm.getName());
		user.setEmail(userForm.getEmail());

		// データベースに保存
		userRepository.save(user);
		// ユーザ一覧画面へリダイレクト
		return "redirect:/users";
	}
}