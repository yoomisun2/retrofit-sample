package retrofit.config.interceptor;

import java.io.IOException;
import java.net.URL;

import org.springframework.stereotype.Component;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Component
public final class HostSelectionInterceptor implements Interceptor {

	private volatile String baseUrl;
	
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		
		if(this.baseUrl != null) {
			URL url = new URL(this.baseUrl);
			HttpUrl newUrl = request.url().newBuilder()
					.scheme(url.getProtocol())
					.host(url.getHost())
					.port(url.getPort() == -1 ? HttpUrl.defaultPort(url.getProtocol()) : url.getPort())
					.build();
			request = request.newBuilder().url(newUrl).build();
		}
		
		return chain.proceed(request);
	}
}
