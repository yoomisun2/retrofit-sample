package retrofit.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import okhttp3.Credentials;
import retrofit.api.JenkinsRepository;
import retrofit.config.interceptor.AuthenticationInterceptor;
import retrofit.config.interceptor.HostSelectionInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Service
public class JenkinsService {

	private String baseUrl = "https://pog-dev-devops.cloudzcp.io";
	private String username = "cloudzcp-admin";
	private String apiToken = "";
	
	@Autowired
	private JenkinsRepository jenkinsRepository;
	
	@Autowired
	private HostSelectionInterceptor hostSelectionInterceptor;
	
	@Autowired
	private AuthenticationInterceptor authenticationInterceptor;
	
	public void buildJobWithHeaderAnnotation() throws IOException {
		System.out.println("== buildJobWithHeaderAnnotation start");

		hostSelectionInterceptor.setBaseUrl(baseUrl);
		String authToken = Credentials.basic(username, apiToken);
		
		Call<Void> call = jenkinsRepository.buildJob(authToken, "awesome-bff");
		call.enqueue(new Callback<Void>() {

			@Override
			public void onResponse(Call<Void> call, Response<Void> response) {
				System.out.println("== buildJobWithHeaderAnnotation onResponse");
				
				if(!response.isSuccessful()) {
					try {
						System.out.println(response.errorBody().string());
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
				
				System.out.println(response.message());
			}

			@Override
			public void onFailure(Call<Void> call, Throwable t) {
				System.out.println("== buildJobWithHeaderAnnotation onFailure");
				System.out.println(t.getMessage());
			}
			
		});
		
		System.out.println("== buildJobWithHeaderAnnotation end");
	}
	
	//use @Header annotation instead of authenticationInterceptor
	public void buildJobWithAuthenticationInterceptor() throws IOException {
		System.out.println("== buildJobWithAuthenticationInterceptor start");

		hostSelectionInterceptor.setBaseUrl(baseUrl);
		authenticationInterceptor.setToken(username, apiToken);
		
		Call<Void> call = jenkinsRepository.buildJob("awesome-bff");
		call.enqueue(new Callback<Void>() {

			@Override
			public void onResponse(Call<Void> call, Response<Void> response) {
				System.out.println("== buildJobWithAuthenticationInterceptor onResponse");
				
				if(!response.isSuccessful()) {
					try {
						System.out.println(response.errorBody().string());
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
				
				System.out.println(response.message());
			}

			@Override
			public void onFailure(Call<Void> call, Throwable t) {
				System.out.println("== buildJobWithAuthenticationInterceptor onFailure");
				System.out.println(t.getMessage());
			}
			
		});
		
		System.out.println("== buildJobWithAuthenticationInterceptor end");
	}
	
}
