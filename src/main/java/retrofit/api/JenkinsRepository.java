package retrofit.api;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JenkinsRepository {

	@POST("/job/{jobName}/build")
	public Call<Void> buildJob(@Header("Authorization") String authorization, @Path("jobName") String jobName);

	@POST("/job/{jobName}/build")
	public Call<Void> buildJob(@Path("jobName") String jobName);
}
