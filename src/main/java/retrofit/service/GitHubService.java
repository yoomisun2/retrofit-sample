package retrofit.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import retrofit.api.GitHubRepository;
import retrofit.api.User;
import retrofit.config.interceptor.HostSelectionInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@Service
public class GitHubService {
	
//	@Autowired
//	private GitHubRepository gitHubRepository;
	
	@Autowired
	private Retrofit retrofit;
	
	private GitHubRepository gitHubRepository;
	
	@PostConstruct
	public void initialize() {
		gitHubRepository = retrofit.create(GitHubRepository.class);
	}

	@Autowired
	private HostSelectionInterceptor hostSelectionInterceptor;
	
	public List<User> getUsers() throws IOException {
		hostSelectionInterceptor.setBaseUrl("https://api.github.com");
		Call<List<User>> call = gitHubRepository.getUsers(10, 1);

		Response<List<User>> response = call.execute();
		if(!response.isSuccessful()) {
			throw new IOException(response.errorBody() != null ? response.errorBody().toString() : "Unknown error");
		}
		
		return response.body();
	}
	
	public void getUsersAsync() throws IOException {
		hostSelectionInterceptor.setBaseUrl("https://api.github.com");
		Call<List<User>> call = gitHubRepository.getUsers(10, 1);

		call.enqueue(new Callback<List<User>>() {
			@Override
			public void onResponse(Call<List<User>> call, Response<List<User>> response) {
				if(!response.isSuccessful()) {
					System.out.println(response.errorBody().toString());
					return;
				}
				
				response.body().forEach(System.out::println);
			}
			
			@Override
			public void onFailure(Call<List<User>> call, Throwable t) {
				System.out.println(t.getMessage());
				
			}
		});
	}

	public List<User> getUsersByHostSelection() throws IOException {
		hostSelectionInterceptor.setBaseUrl("https://unknown.github.com");
		Response<List<User>> list = gitHubRepository.getUsers(10, 1).execute();
		return list.body();
	}
}
