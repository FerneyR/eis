package com.statusreview.statusreview.controllers;

import com.statusreview.statusreview.models.UrlMethodInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import io.minio.MinioClient;
import io.minio.errors.MinioException;

@CrossOrigin
@RestController
public class StatusReviewController {
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${MINIO_ENDPOINT}")
    private String minioEndpoint;

    @Value("${MINIO_ACCESS_KEY}")
    private String minioAccessKey;

    @Value("${MINIO_SECRET_KEY}")
    private String minioSecretKey;

    @GetMapping("/statuspage")
    public ResponseEntity<List<Map<String, Object>>> getHealthStatus() {
        List<Map<String, Object>> healthStatusList = new ArrayList<>();
        System.out.println("Consola de Spring Boot statusreview/statuspage: ");

        try {
            healthStatusList.add(createHealthStatus("Base de datos - " + dbUrl, testDatabaseConnection()));
            System.out.println("aqui" + healthStatusList.get(0).get("status"));
        } catch (Exception e) {
            e.printStackTrace();
            healthStatusList.add(createHealthStatus("Base de datos - " + dbUrl, false));
        }

        List<UrlMethodInfo> urlsToCheckDB = Arrays.asList(
                new UrlMethodInfo("http://register:8080/customers", HttpMethod.GET, "{}"),
                new UrlMethodInfo("http://register:8080/employees", HttpMethod.GET, "{}"),
                //new UrlMethodInfo("http://attendance:8080/attendance/save", HttpMethod.POST, "{\"idCustomer\": 1, \"idEmployee\": 1, \"photo\": \"photo-1-1\"}"),
                new UrlMethodInfo("http://attendance:8080/attendance", HttpMethod.GET, "{}")
        );

        if (healthStatusList.get(0).get("status").equals("Healthy")) {
            for (UrlMethodInfo pair : urlsToCheckDB) {
                String url = pair.getUrl();
                HttpMethod httpMethod = pair.getHttpMethod();
                String bodyjson = pair.getBodyjson();
                healthStatusList.add(createHealthStatus("Endpoint - " + url, checkEndpointHealth(url, httpMethod, bodyjson)));
            }
        } else {
            for (UrlMethodInfo pair : urlsToCheckDB) {
                String url = pair.getUrl();
                HttpMethod httpMethod = pair.getHttpMethod();
                String bodyjson = pair.getBodyjson();
                healthStatusList.add(createHealthStatus("Endpoint - " + url, false));
            }
        }

        List<UrlMethodInfo> urlsNO = Arrays.asList(
                new UrlMethodInfo("https://pokeapi.co/api/v2/pokemon/ditto", HttpMethod.GET, "{}"),
                new UrlMethodInfo("http://faceapi.logixpress.co:8000/", HttpMethod.GET, "{}")
        );

        for (UrlMethodInfo pair : urlsNO) {
            String url = pair.getUrl();
            HttpMethod httpMethod = pair.getHttpMethod();
            String bodyjson = pair.getBodyjson();
            healthStatusList.add(createHealthStatus("Endpoint - " + url, checkEndpointHealth(url, httpMethod, bodyjson)));
        }

        System.out.println(minioEndpoint+" "+checkMinioStatus());
        healthStatusList.add(createHealthStatus("Minio - " + minioEndpoint, checkMinioStatus()));

        return ResponseEntity.ok(healthStatusList);
    }

    private Map<String, Object> createHealthStatus(String name, boolean isHealthy) {
        Map<String, Object> healthStatus = new HashMap<>();
        healthStatus.put("name", name);
        healthStatus.put("status", isHealthy ? "Healthy" : "Unhealthy");
        healthStatus.put("code", isHealthy ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value());
        return healthStatus;
    }

    public boolean checkMinioStatus() {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioEndpoint)
                    .credentials(minioAccessKey, minioSecretKey)
                    .build();

            minioClient.listBuckets();
            return true;
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean testDatabaseConnection() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Connection> connectionFuture = executor.submit(() -> {
            try {
                return dataSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });

        try {
            Connection connection = connectionFuture.get(3, TimeUnit.SECONDS);
            if (connection != null) {
                connection.close();
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException | ExecutionException | TimeoutException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            executor.shutdown();
        }
    }

    private boolean checkEndpointHealth(String endpointUrl, HttpMethod httpMethod, String jsonInputString) {
        try {

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(jsonInputString, headers);

            CompletableFuture<ResponseEntity<String>> future = CompletableFuture.supplyAsync(() ->
                            restTemplate.exchange(endpointUrl, httpMethod, requestEntity, String.class),
                    Executors.newFixedThreadPool(1)
            );

            int timeoutInMillis = (int) (1.6 * 1000);

            ResponseEntity<String> response = future.get(timeoutInMillis, TimeUnit.MILLISECONDS);

            HttpStatusCode response2 = response.getStatusCode();

            boolean pr = response2.is2xxSuccessful();
            try {
                if (httpMethod.toString().equals("POST")) {
                    SingleConnectionDataSource dataSource = new SingleConnectionDataSource(dbUrl, dbUsername, dbPassword, true);
                    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                    int employeeIdToDelete = 1;
                    String sql = "DELETE FROM attendance WHERE employee_id = ?";
                    jdbcTemplate.update(sql, employeeIdToDelete);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(response2 + " - " + httpMethod.toString());
            return response2.is2xxSuccessful();
        } catch (RestClientException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

