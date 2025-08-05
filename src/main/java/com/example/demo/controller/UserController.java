package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.data.entity.User;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.form.UserForm;

@Controller
public class UserController {

	// @Autowiredアノテーションを付けると、Spring Bootが自動でインスタンスをInjectします。
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/users")
	// 引数にorg.springframework.ui.Modelを追加
	public String getUsers(Model model) {
		List<User> users = userRepository.findAll();
		// Modelにusersを追加
		model.addAttribute("users", users);
		return "users";
	}

	// getNewUserメソッドを追加
	@GetMapping("/newuser")
	// 引数にModelを追加
	public String getNewUser(Model model) {
		// Modelに空のUserFormを追加
		UserForm userForm = new UserForm();
		model.addAttribute("userForm", userForm);
		return "newuser";
	}

	// マッピング設定
	@PostMapping("/newuser")
	// 引数にUserFormを追加
	public String registerUser(@Validated UserForm userForm, BindingResult bindingResult) {
		// バリデーションの結果、エラーがあるかどうかチェック
		if (bindingResult.hasErrors()) {
			// エラーがある場合はユーザー登録画面を返す
			return "newuser";
		}

		// UserFormの値をUserクラス（Entity）にセットする
		User user = new User();
		user.setName(userForm.getName());
		user.setEmail(userForm.getEmail());

		// データベースに保存
		userRepository.save(user);

		return "redirect:/users";
	}

	// deleteUserメソッドを追加
	// リクエストマッピング設定を追加
	@PostMapping("/users/delete/{id}")
	// 処理の中でidを使えるように、引数にidを追加
	public String deleteUser(@PathVariable Long id) {
		// 指定したIDのユーザーを削除
		userRepository.deleteById(id);
		return "redirect:/users";
	}
}