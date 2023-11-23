package bg.petarh.vending.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;
import bg.petarh.vending.rest.responses.serialization.PurchaseOrderHandlingResponseSerializer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("POST", "HEAD", "PUT", "DELETE", "GET")
                .allowedOriginPatterns("localhost");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(PurchaseOrderHandlingResponse.class, new PurchaseOrderHandlingResponseSerializer());

        objectMapper.registerModule(module);
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
    }
}
