package jae.api;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

public class ApiWebCall
{
	private final String _url;
	private final Gson _gson;
	private final CloseableHttpClient _httpClient;

	public ApiWebCall(String url)
	{
		this(url, new Gson());
	}

	public ApiWebCall(String url, Gson gson)
	{
		_url = url;
		_gson = gson;

		PoolingHttpClientConnectionManager _cm = new PoolingHttpClientConnectionManager();
		_cm.setMaxTotal(200);
		_cm.setDefaultMaxPerRoute(20);
		_cm.setValidateAfterInactivity(2000);

		_httpClient = HttpClients.custom().setConnectionManager(_cm).build();
	}

	public <T> T get(String resource, Class<T> clazz)
	{
		return get(resource, (Type)clazz);
	}

	public <T> T get(String resource, Type type)
	{
		T returnData = null;

		HttpGet httpGet = new HttpGet(_url + resource);
		try (CloseableHttpResponse response = _httpClient.execute(httpGet)) {
			returnData = parseResponse(response, type);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return returnData;
	}

	public <T> T post(String resource, Class<T> clazz, Object data)
	{
		T returnData = null;

		HttpPost httpPost = new HttpPost(_url + resource);
		try
		{
			if (data != null)
			{
				StringEntity params = new StringEntity(_gson.toJson(data));
				params.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				httpPost.setEntity(params);
			}

			try (CloseableHttpResponse response = _httpClient.execute(httpPost))
			{
				returnData = parseResponse(response, clazz);
			} catch (IOException e)
			{
				e.printStackTrace();
			}

		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		return returnData;
	}

	private <T> T parseResponse(CloseableHttpResponse response, Type type) throws IOException
	{
		HttpEntity entity = response.getEntity();
		T parsed = _gson.fromJson(new InputStreamReader(entity.getContent()), type);
		if (parsed instanceof HttpStatusCode && response.getStatusLine() != null)
		{
			((HttpStatusCode) parsed).setStatusCode(response.getStatusLine().getStatusCode());
		}
		return parsed;
	}
}
