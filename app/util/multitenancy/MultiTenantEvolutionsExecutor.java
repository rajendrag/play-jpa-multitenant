package util.multitenancy;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.Configuration;
import play.Environment;
import play.Play;
import play.api.db.evolutions.ResourceEvolutionsReader;
import play.db.Database;
import play.db.Databases;
import play.db.evolutions.Evolutions;
import scala.Option;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Executes the scripts on to all the databases.
 * Created by RP on 1/8/16.
 */
@Singleton
public class MultiTenantEvolutionsExecutor {

    private Database currentDatabase;
    private Connection currentConnection;


    private String driver;
    private String host;
    private int port;
    private String commonDB;
    private String appDBPrefix;
    private String username;
    private String password;
    private String urlPrefix;

    //jdbc.driver=com.mysql.jdbc.Driver
    //jdbc.host=localhost
    //jdbc.commondb=iqueue
    //jdbc.applicationdbprefix=iqueue_
    //jdbc.username=iqueue_u
    //jdbc.password="iqueue_p@$sw0rD"
    //jdbc.urlprefix="jdbc:mysql://"

    Configuration conf;
    Environment environment;

    @Inject
    public MultiTenantEvolutionsExecutor(Configuration configuration, Environment environment) {
        conf = configuration;
        this.environment = environment;
        this.driver = conf.getString("jdbc.driver");
        this.host = conf.getString("jdbc.host");
        this.port = conf.getInt("jdbc.port", 3306);
        this.commonDB = conf.getString("jdbc.commondb");
        this.appDBPrefix = conf.getString("jdbc.applicationdbprefix");
        this.username = conf.getString("jdbc.username");
        this.password = conf.getString("jdbc.password");
        this.urlPrefix = conf.getString("jdbc.urlprefix");
        List<String> tenants = queryForAllTenants();
        applyEvolutions(tenants);
    }

    private void applyEvolutions(List<String> tenants) {
        for(String tenant : tenants) {
            createConnection(appDBPrefix+tenant);
            Evolutions.applyEvolutions(currentDatabase, new ApplicationEvolutionsReader(environment.underlying()));
            safeClose();
        }
    }


    private List<String> queryForAllTenants() {
        createConnection(commonDB);
        List<String> tenants = new ArrayList<>();
        try {
            ResultSet rs = currentConnection.prepareStatement("select * from com_tenant").executeQuery();
            while (rs.next()) {
                tenants.add(rs.getString("tenant_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tenants;
    }

    private void createConnection(String dbName) {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("username", this.username);
        configMap.put("password", this.password);
        //String name, String driver, String url
        try {
            currentDatabase = Databases.createFrom(dbName, driver, urlPrefix+host+":"+port+"/"+dbName, configMap);
            currentConnection = currentDatabase.getConnection();
        } catch (Exception e) {

        }
    }

    private void safeClose() {
        currentDatabase.shutdown();
    }

}
