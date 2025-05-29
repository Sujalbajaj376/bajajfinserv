package com.healthrx.hiring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class HiringApplication {

    private static final Logger log = LoggerFactory.getLogger(HiringApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HiringApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) {
        return args -> {
            try {
                // Step 1: Generate webhook
                String generateWebhookUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
                
                WebhookRequest request = new WebhookRequest();
                request.setName("SUJAL BAJAJ");
                request.setRegNo("REG12348");
                request.setEmail("22ucc106lnmiit.com");

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);
                ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(
                    generateWebhookUrl, entity, WebhookResponse.class);

                WebhookResponse webhookResponse = response.getBody();
                log.info("Received webhook URL: {}", webhookResponse.getWebhook());
                log.info("Received access token: {}", webhookResponse.getAccessToken());

                // Step 2: Solve SQL problem (Question 2 since regNo ends with 8 - even)
                String sqlQuery = "SELECT d.doctor_id, d.doctor_name, " +
                                "COUNT(DISTINCT p.patient_id) as total_patients, " +
                                "COUNT(DISTINCT pr.prescription_id) as total_prescriptions " +
                                "FROM doctors d " +
                                "LEFT JOIN prescriptions pr ON d.doctor_id = pr.doctor_id " +
                                "LEFT JOIN patients p ON pr.patient_id = p.patient_id " +
                                "GROUP BY d.doctor_id, d.doctor_name " +
                                "HAVING COUNT(DISTINCT p.patient_id) > 1 " +
                                "ORDER BY total_prescriptions DESC";

                // Step 3: Submit solution
                String submitUrl = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";
                
                HttpHeaders submitHeaders = new HttpHeaders();
                submitHeaders.setContentType(MediaType.APPLICATION_JSON);
                submitHeaders.set("Authorization", webhookResponse.getAccessToken());

                SolutionRequest solutionRequest = new SolutionRequest();
                solutionRequest.setFinalQuery(sqlQuery);

                HttpEntity<SolutionRequest> submitEntity = new HttpEntity<>(solutionRequest, submitHeaders);
                ResponseEntity<String> submitResponse = restTemplate.postForEntity(
                    submitUrl, submitEntity, String.class);

                log.info("Solution submitted successfully: {}", submitResponse.getBody());

            } catch (Exception e) {
                log.error("Error occurred: ", e);
            }
        };
    }
}

class WebhookRequest {
    private String name;
    private String regNo;
    private String email;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

class WebhookResponse {
    private String webhook;
    private String accessToken;
    public String getWebhook() { return webhook; }
    public void setWebhook(String webhook) { this.webhook = webhook; }
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
}

class SolutionRequest {
    private String finalQuery;
    public String getFinalQuery() { return finalQuery; }
    public void setFinalQuery(String finalQuery) { this.finalQuery = finalQuery; }
} 