package io.pivotal.xd.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.List;
import java.util.Map;

@Component
final class Sample {

    private final String endpoint;

    private final RestOperations restOperations;

    @Autowired
    Sample(@Value("${api.endpoint}") String endpoint, RestOperations restOperations) {
        this.endpoint = endpoint;
        this.restOperations = restOperations;
    }

    @SuppressWarnings("unchecked")
    public void getSomething() {
        Map<String, Object> result = this.restOperations.getForObject(getAppsEndpoint(), Map.class);

        getResources(result).stream()
                .map(this::getName)
                .sorted()
                .forEach(System.out::println);
    }

    private String getAppsEndpoint() {
        return String.format("%s/v2/apps", this.endpoint);
    }

    @SuppressWarnings("unchecked")
    private String getName(Map<String, Object> resource) {
        Map<String, Object> entity = (Map<String, Object>) resource.get("entity");
        return (String) entity.get("name");
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getResources(Map<String, Object> result) {
        return (List<Map<String, Object>>) result.get("resources");
    }

}
