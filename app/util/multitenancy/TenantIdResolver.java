package util.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import play.Logger;
import play.mvc.Http;

import java.util.Map;

/**
 * Created by RP on 1/6/16.
 */
public class TenantIdResolver implements CurrentTenantIdentifierResolver {

    private static Logger.ALogger log = Logger.of(TenantIdResolver.class );

    @Override
    public String resolveCurrentTenantIdentifier() {
        Map<String, Object> args = Http.Context.current().args;
        log.debug("Selected tenant "+(String)args.get("tenantId"));
        return (String)args.get("tenantId");
        //return "uchealth";
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
