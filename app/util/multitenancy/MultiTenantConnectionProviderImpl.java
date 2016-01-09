package util.multitenancy;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import play.Configuration;
import play.Logger;
import play.Play;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provides the Connections to Hibernate with appropriate tenant datasource.
 * It uses C3P0 connection pooling
 * Created by RP on 1/6/16.
 */
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {

    private static Logger.ALogger log = Logger.of(MultiTenantConnectionProviderImpl.class );

    private Map<String, ComboPooledDataSource> dataSourceMap = new ConcurrentHashMap<>();

    /**
     *
     * Constructor. Initializes the ComboPooledDataSource based on the config.properties.
     */
    public MultiTenantConnectionProviderImpl() {
        Configuration configuration = Play.application().configuration();
        ComboPooledDataSource cpds = getComboPooledDataSource(configuration);
        String prefix = configuration.getString("jdbc.urlprefix")+configuration.getString("jdbc.host")+"/";
        cpds.setJdbcUrl(prefix+configuration.getString("jdbc.commondb"));
        dataSourceMap.put("common", cpds);
        log.info("Connection Pool initialised!");
    }

    private ComboPooledDataSource getComboPooledDataSource(Configuration configuration) {
        ComboPooledDataSource cpds = new ComboPooledDataSource("Example");
        try {
            cpds.setDriverClass(configuration.getString("jdbc.driver"));
            cpds.setUser(configuration.getString("jdbc.username"));
            cpds.setPassword(configuration.getString("jdbc.password"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        return cpds;
    }


    @Override
    public Connection getAnyConnection() throws SQLException {
        ComboPooledDataSource cpds = dataSourceMap.get("common");
        log.debug("Get Default Connection:::Number of connections (max: busy - idle): {} : {} - {}",new int[]{cpds.getMaxPoolSize(),cpds.getNumBusyConnectionsAllUsers(),cpds.getNumIdleConnectionsAllUsers()});
        if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize()){
            log.warn("Maximum number of connections opened");
        }
        if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize() && cpds.getNumIdleConnectionsAllUsers()==0){
            log.error("Connection pool empty!");
        }
        return cpds.getConnection();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        ComboPooledDataSource cpds = dataSourceMap.get(tenantIdentifier);
        if(cpds == null) {
            Configuration conf = Play.application().configuration();
            cpds = getComboPooledDataSource(conf);
            String prefix = conf.getString("jdbc.urlprefix")+conf.getString("jdbc.host")+"/";
            String jdbcUrl = prefix + conf.getString("jdbc.applicationdbprefix")+tenantIdentifier;
            cpds.setJdbcUrl(jdbcUrl);
            dataSourceMap.put(tenantIdentifier, cpds);
        }
        log.debug("Get {} Connection:::Number of connections (max: busy - idle): {} : {} - {}",new Object[]{tenantIdentifier, cpds.getMaxPoolSize(),cpds.getNumBusyConnectionsAllUsers(),cpds.getNumIdleConnectionsAllUsers()});
        if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize()){
            log.warn("Maximum number of connections opened");
        }
        if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize() && cpds.getNumIdleConnectionsAllUsers()==0){
            log.error("Connection pool empty!");
        }
        return cpds.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection){
        try {
            this.releaseAnyConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return ConnectionProvider.class.equals( unwrapType ) || MultiTenantConnectionProvider.class.equals( unwrapType ) || MultiTenantConnectionProviderImpl.class.isAssignableFrom( unwrapType );
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        if ( isUnwrappableAs( unwrapType ) ) {
            return (T) this;
        }
        else {
            throw new UnknownUnwrapTypeException( unwrapType );
        }
    }
}
