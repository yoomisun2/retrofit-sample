package retrofit.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import retrofit.api.User;
import retrofit.service.GitHubService;

@RestController
public class ApiCallRestController {

	@Autowired
	private GitHubService gitHubService;
	
	@GetMapping(value = "/users")
	public List<User> getUsers() throws IOException {
		return gitHubService.getUsers();
	}

	@GetMapping(value = "/users1")
	public void getUsersAsync() throws IOException {
		gitHubService.getUsersAsync();
	}

	@GetMapping(value = "/users2")
	public List<User> getUsersByHostSelection() throws IOException {
		return gitHubService.getUsersByHostSelection();
	}
}
