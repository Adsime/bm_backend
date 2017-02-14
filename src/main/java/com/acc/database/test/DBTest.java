package com.acc.database.test;
import com.acc.database.pojo.User;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.assertion.*;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import java.io.FileInputStream;


/**
 * Created by nguyen.duy.j.khac on 10.02.2017.
 */
public class DBTest extends DBTestCase{

    public DBTest(String name)
    {
        /*super( name );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:5050/bm_database" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "admin" );
        // System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "" );*/
    }

    public static void main(){
    }

    protected IDataSet getDataSet() throws Exception
    {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("dataset.xml"));
    }
    /*
    @Test
    public void findsAndReadsExistingPersonByFirstName() throws Exception {
        assert
        PersonRepository repository = new PersonRepository(dataSource());
        Person charlie = repository.findPersonByFirstName("Charlie");
        assertThat(charlie.getFirstName(), is("Charlie"));
        assertThat(charlie.getLastName(), is("Brown"));
        assertThat(charlie.getAge(), is(42));
    }*/
}
