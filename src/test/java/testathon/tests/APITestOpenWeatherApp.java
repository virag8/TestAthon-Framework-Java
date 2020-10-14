package test.java.testathon.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class APITestOpenWeatherApp {

    @Test
    public void OpenWeatherCheckStatus() {

        RestAssured.get(
                "https://samples.openweathermap.org/data/2.5/forecast?id=524901&appid=b1b15e88fa797225412429c1c50c122a1")
                .then().statusCode(200);

        RestAssured.baseURI = "http://echo.jsontest.com";
        RequestSpecification rs = RestAssured.given();
        Response res = rs.get("Key1/value/Key2/two");
        System.out.println(res.getBody().asString().toString());
        System.out.println(res.getBody().jsonPath().getString("Key1").toString());

    }

    @Test
    public void JSONTest(){
        RestAssured.baseURI = "http://ip.jsontest.com";

        RequestSpecification rs = RestAssured.given();

        Response res = rs.get();
        System.out.println(res.getBody().asString());
        Machine machine = res.getBody().as(Machine.class);
        System.out.println(machine.getIp());
        System.out.println(res.getBody().jsonPath().getString("ip").toString());
    }
}

final class Machine {

    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
