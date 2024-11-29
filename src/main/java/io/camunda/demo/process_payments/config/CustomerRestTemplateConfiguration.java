package io.camunda.demo.process_payments.config;

import org.springframework.core.io.Resource;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Configuration
public class CustomerRestTemplateConfiguration {

    @Value("${spring.trust.store}")
    private Resource trustStore;

    @Value("${spring.trust.store-password}")
    private String trustStorePassword;

    @Bean
    public RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, MalformedURLException, IOException {

        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray()).build();

        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);

        HttpClientConnectionManager httpClientConnectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build();

        CloseableHttpClient closeableHttpClient = HttpClients.custom().setConnectionManager(httpClientConnectionManager).build();

        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(closeableHttpClient);

        return new RestTemplate(requestFactory);
    }
}