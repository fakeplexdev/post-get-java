package jae;

import com.google.gson.JsonObject;
import jae.api.ApiEndpoint;
import jae.api.ApiHost;

import java.util.function.Consumer;

/**
 * These endpoints are shown as an example of how it works
 */
public class CallHandler extends ApiEndpoint
{
    public CallHandler()
    {
        super(ApiHost.getAPIHost(), "/profile");
    }

    public String getName()
    {
        return getWebCall().get("/name", String.class);
    }

    public void setName(Consumer<Boolean> callback, String name)
    {
        JsonObject body = new JsonObject();
        body.addProperty("name", name);

        callback.accept(getWebCall().post("/name", Boolean.class, body));
    }
}
