package lab.application.api.web;

import lab.application.api.web.rest.PublicController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApiApplicationTest {

    private int port = 8080;
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";


    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    PublicController publicController;

    //@Autowired
    private RestTemplate restTemplate;

    private MockMvc mockMvc;

    //@BeforeAll
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Bean
    RestTemplate restTemplate () {
        return new RestTemplate();
    }

    @Test
    public void contexLoads() throws Exception {
        assertThat(publicController).isNotNull();
    }

    @Test
    public void toTestPublicUrlShouldReturn200() throws Exception {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();

        this.mockMvc.perform(get("/api/public/test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.message").isString())
        .andExpect(jsonPath("$.success").value(true));
    }




}