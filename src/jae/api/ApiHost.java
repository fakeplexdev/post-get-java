package jae.api;

public class ApiHost
{
	private static final Object LOCK = new Object();
	private static ApiHost host;

	public static ApiHost getAPIHost()
	{
		if (host == null)
		{
			synchronized (LOCK)
			{
				if (host == null)
				{
					try
					{
						host = new ApiHost("127.0.0.1", 3000);
					}
					catch (Throwable t)
					{
						t.printStackTrace();
					}
				}
			}
		}

		return host;
	}

	private final String _host;
	private final int _port;

	private ApiHost(String host, int port)
	{
		_host = host;
		_port = port;
	}

	public String getHost()
	{
		return _host;
	}

	public int getPort()
	{
		return _port;
	}
}
