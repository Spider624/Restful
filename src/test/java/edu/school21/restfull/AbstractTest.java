package edu.school21.restfull;

import edu.school21.restfull.app.RestfullApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Profile("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RestfullApplication.class)
public class AbstractTest {

	protected MockMvc mockMvc;

	@Autowired
	public void setContext(WebApplicationContext context) {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

}
