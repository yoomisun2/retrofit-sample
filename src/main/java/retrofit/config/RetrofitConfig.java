package retrofit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit.api.GitHubRepository;
import retrofit.api.JenkinsRepository;
import retrofit.config.interceptor.AuthenticationInterceptor;
import retrofit.config.interceptor.HostSelectionInterceptor;
import retrofit.config.interceptor.InvocationLoggerInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {

	private static String BASE_URL = "https://api.github.com";
	
	@Autowired
	private HostSelectionInterceptor hostSelectionInterceptor;
	
	@Autowired
	private AuthenticationInterceptor authenticationInterceptor;
	
	@Bean
	public OkHttpClient okHttpClient() {
		HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
		InvocationLoggerInterceptor invocationLoggerInterceptor = new InvocationLoggerInterceptor();
		
		return new OkHttpClient.Builder()
				.addInterceptor(httpLoggingInterceptor)
				.addInterceptor(invocationLoggerInterceptor)
				.addInterceptor(hostSelectionInterceptor)
				.addInterceptor(authenticationInterceptor) //use @Header annotation instead of authenticationInterceptor
				.build();
	}

	@Bean
	public Retrofit retrofit() {
		return new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.callFactory(okHttpClient())
				.addConverterFactory(GsonConverterFactory.create())
				.build();
	}
	
	@Bean
	public GitHubRepository gitHubRepository() {
		return retrofit().create(GitHubRepository.class);
	}
	
	@Bean
	public JenkinsRepository jenkinsRepository() {
		return retrofit().create(JenkinsRepository.class);
	}
}
