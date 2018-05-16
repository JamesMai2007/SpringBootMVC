package com.example.sbmvc;

import com.example.sbmvc.config.Myconfig;
import com.example.sbmvc.controller.IndexController;
import com.example.sbmvc.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(IndexController.class) //可以指定多个class , @WebMvcTest使用起来像@SpringBootTest，同样提供一些注入bean
public class SbmvcApplicationTests {

	@Autowired
	MockMvc mvc;

	@MockBean
	UserService userService;

	@MockBean
	Myconfig myconfig;



	@Test
	public void contextLoads() throws Exception {
		BDDMockito.given(this.userService.getName()).willReturn("James"); //模拟service返回值

		mvc.perform(MockMvcRequestBuilders.get("/getUser").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()) //输出James
				;

	}

}
