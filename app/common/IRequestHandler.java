package common;

import play.http.DefaultHttpRequestHandler;
import play.http.HttpRequestHandler;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Random;

/**
 * Called for every request, sets the appropriate tenant id in context
 * Created by RP on 1/7/16.
 */
public class IRequestHandler  implements HttpRequestHandler {

    @Override
    public Action createAction(Http.Request request, Method actionMethod) {
        return new Action.Simple() {
            @Override
            public F.Promise<Result> call(Http.Context ctx) throws Throwable {
                String[] tenants = new String[]{"tenant1", "tenant2"};
                Map<String, Object> args = ctx.args;
                Random r = new Random();
                String tenant = tenants[r.nextInt(2)];
                System.out.println("Random tenant"+tenant);
                args.put("tenantId", tenant);
                return delegate.call(ctx);
            }
        };
    }

    @Override
    public Action wrapAction(Action action) {
        return action;
    }
}
