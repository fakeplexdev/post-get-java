package jae.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ApiEndpoint
{
	private final Gson _gson;
	private final ApiWebCall _webCall;

	public ApiEndpoint(ApiHost host, String path)
	{
		this(host, path, new GsonBuilder()
				.setFieldNamingStrategy(new ApiFieldNamingStrategy())
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create()
		);
	}

	public ApiEndpoint(ApiHost host, String path, Gson gson)
	{
		this(host.getHost(), host.getPort(), path, gson);
	}

	public ApiEndpoint(String host, int port, String path, Gson gson)
	{
		String url = "http://" + host + ":" + port + path;

		_webCall = new ApiWebCall(url, gson);
		_gson = gson;
	}

	protected ApiWebCall getWebCall()
	{
		return _webCall;
	}

	public Gson getGson()
	{
		return _gson;
	}
}
