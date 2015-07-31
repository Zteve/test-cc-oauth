package io.pivotal.xd.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
final class Sample {

    @Value("${api.endpoint}")
    private static String ENDPOINT;

    private final RestOperations restOperations;

    @Autowired
    Sample(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    public void getSomething() {
        String result = this.restOperations.getForObject(String.format("%s/v2/apps", ENDPOINT), String.class);
        System.out.println(String.format("\nResult is:\n%s\n", result));
    }

}
