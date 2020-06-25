//package retrofit.config;
//
//import org.springframework.util.StringUtils;
//
//import okhttp3.Credentials;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit.config.interceptor.AuthenticationInterceptor;
//import retrofit.config.interceptor.HostSelectionInterceptor;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
////reusable service generator class
//public class ServiceGenerator {
//
//	private static final String BASE_URL = "https://api.github.com";
//	
//	private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());
//	
//	private static Retrofit retrofit = builder.build();
//	
//	private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//	
//	private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
//	
//	private static HostSelectionInterceptor selection = new HostSelectionInterceptor();
//	
//	private static AuthenticationInterceptor authentication = new AuthenticationInterceptor();
//	
//	public static <S> S createService(Class<S> serviceClass) {
//		return createService(serviceClass, null);
//	}
//	
//	public static <S> S createService(Class<S> serviceClass, String username, String password) {
//		if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
//			String authToken = Credentials.basic(username, password);
//			return createService(serviceClass, authToken);
//		}
//		
//		return createService(serviceClass, null);
//	}
//	
//	
//	public static <S> S createService(Class<S> serviceClass, final String token) {
//		if(!StringUtils.isEmpty(token)) {
//			authentication.setToken(token);
//		}
//		
//		if(!httpClient.interceptors().contains(authentication)) {
//			httpClient.addInterceptor(authentication);
//		}
//
//		if(!httpClient.interceptors().contains(logging)) {
//			httpClient.addInterceptor(logging);
//		}
//		
//		if(!httpClient.interceptors().contains(selection)) {
//			httpClient.addInterceptor(selection);
//		}
//
//
//		builder.client(httpClient.build());
//		retrofit = builder.build();
//
//		return retrofit.create(serviceClass);
//	}
//	
//}
