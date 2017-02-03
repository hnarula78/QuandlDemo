package com.quandl.test;

import org.json.JSONArray;
import groovy.json.JsonException;
import com.jayway.restassured.response.Response;
import static com.jayway.restassured.RestAssured.get;

import org.testng.Assert;
import org.testng.annotations.Test;



/**
 * Created by himan on 2017-02-03.
 */
public class APITest {
    String org_dataset = "NASDAQ_FB";
    String api_key="TsSCPQE1aUh5Dax1cEXU";
    String dataset_return_type = "json";
    String ticker = "FB";


    //Test 1 : To return the list of DBs for which data is available
    @Test(priority = 0)
    public void getDBList() throws JsonException {
        Response resp = get("https://www.quandl.com/api/v3/databases.json");
        String api_response = resp.asString();
        api_response = api_response.substring(api_response.indexOf(":")+1);

        JSONArray jsonResponse = new JSONArray(api_response);
        String databasename = jsonResponse.getJSONObject(0).getString("name");
        String databasecode = jsonResponse.getJSONObject(0).getString("database_code");
        Assert.assertTrue(jsonResponse.length()>0,"No databases listed.");
        System.out.println("Test 1 : First Database Listed is " +databasecode+"<-->"+databasename);
    }
    //Second Test to get dataset for requested Key
    @Test(priority = 1)
    public void getSpecificDataSetForRequestedKey()
    {
        Response resp = get("https://www.quandl.com/api/v3/datasets/GOOG/"+org_dataset+"."+dataset_return_type+"?api_key="+api_key);
        String api_response = resp.asString();
        api_response = api_response.substring(api_response.indexOf(":")+1);
        api_response = api_response.substring(0,api_response.lastIndexOf('}'));
        api_response = "[" + api_response + "]";
        JSONArray jsonResponse = new JSONArray(api_response);
        String datasetcode = jsonResponse.getJSONObject(0).getString("dataset_code");
        String databasecode = jsonResponse.getJSONObject(0).getString("database_code");
        Assert.assertTrue(jsonResponse.length() > 0 ," Data for requested Company is not found");
        System.out.println("Test 2 : Dataset Code returned  is " +datasetcode + "from database " + databasecode);
    }

    //Test 3 : To test whether we get data from datatable for WIKI with  a valid API Key
    @Test(priority = 2)
    public void getDataFromDataTableForWiki() {
        Response response = get("https://www.quandl.com/api/v3/datatables/WIKI/PRICES.json?ticker="+ticker+"&qopts.columns=date,open&api_key="+api_key);
        String api_response = response.asString();
        api_response = api_response.substring(api_response.indexOf(":")+1);
        api_response = api_response.substring(0,api_response.lastIndexOf(',')-1);
        api_response = "[" + api_response + "}]";
        JSONArray jsonResponse = new JSONArray(api_response);
        Assert.assertTrue(jsonResponse.length()>0,"No datatable listed.");
        System.out.println("Test 3 : Data returned is "+ api_response);
    }
}
