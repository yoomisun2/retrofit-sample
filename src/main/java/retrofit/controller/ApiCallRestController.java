package retrofit.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import retrofit.api.User;
import retrofit.service.GitHubService;
import retrofit.service.JenkinsService;

@RestController
public class ApiCallRestController {

	@Autowired
	private GitHubService gitHubService;
	
	@Autowired
	private JenkinsService jenkinsService;
	
	@GetMapping(value = "/sync")
	public List<User> getUsers() throws IOException {
		return gitHubService.getUsers();
	}

	@GetMapping(value = "/async")
	public void getUsersAsync() throws IOException {
		gitHubService.getUsersAsync();
	}

	@GetMapping(value = "/changeHost")
	public List<User> getUsersByHostSelection() throws IOException {
		return gitHubService.getUsersByHostSelection();
	}
	
	@GetMapping(value = "/buildJob")
	public void buildJobWithHeaderAnnotation() throws IOException {
		jenkinsService.buildJobWithHeaderAnnotation();
	}

	@GetMapping(value = "/buildJob2")
	public void buildJobWithAuthenticationInterceptor() throws IOException {
		jenkinsService.buildJobWithAuthenticationInterceptor();;
	}
}
