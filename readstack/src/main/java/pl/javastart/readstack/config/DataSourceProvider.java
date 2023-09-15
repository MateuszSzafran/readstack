package pl.javastart.readstack.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceProvider {
    static DataSource dataSource;

    private DataSourceProvider() {
    }

    public static DataSource getDataSource() throws NamingException {
        if (dataSource == null) {
            Context initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/readstack");
            //Context envContext = (Context) initialContext.lookup("java:comp/env/");
            //dataSource = (DataSorurce) envContext.lookup("jdbc/readstack");
        }
        return dataSource;
    }
}
