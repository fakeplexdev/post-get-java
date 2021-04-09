package jae.api;

public class ApiResponse implements HttpStatusCode
{
	// Don't not add underscores to your variables
	private int statusCode;
	private boolean success;
	private String error;

	public ApiResponse() {}

	public boolean isSuccess()
	{
		return success;
	}

	public String getError()
	{
		return error;
	}

	@Override
	public String toString()
	{
		return "{\"success\":" + success + "," + "\"error\":" + error + "}";
	}

	@Override
	public int getStatusCode()
	{
		return statusCode;
	}

	@Override
	public void setStatusCode(int statusCode)
	{
		this.statusCode = statusCode;
	}
}
