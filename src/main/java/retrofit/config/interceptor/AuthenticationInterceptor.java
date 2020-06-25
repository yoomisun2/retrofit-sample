package retrofit.config.interceptor;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Component
public final class AuthenticationInterceptor implements Interceptor {
	
	private volatile String token;
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public void setToken(String username, String password) {
		this.token = Credentials.basic(username, password);
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		
		if(!StringUtils.isEmpty(token)) {
			request = request.newBuilder().header("Authorization", token).build();
		}
		
		return chain.proceed(request);
	}

}
