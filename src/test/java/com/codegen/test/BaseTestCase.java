/*
 * Copyright 2006-2015 Transsion.com All right reserved. This software is the confidential and proprietary information
 * of Transsion.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Transsion.com.
 */
package com.codegen.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * ClassName:BaseTestCase <br/>
 * Date: 2015年7月16日 下午6:09:29 <br/>
 * 
 * @author fenglibin
 * @version
 * @see
 */
@ContextConfiguration(locations = { "classpath*:spring/spring-application-context-test.xml" })
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseTestCase {
    @Autowired
    protected WebApplicationContext wac;
    protected MockMvc mockMvc;        //SpringMVC提供的Controller Mock测试类
    protected MockHttpServletRequest request = new MockHttpServletRequest();
    protected MockHttpServletResponse response = new MockHttpServletResponse();
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        //StandaloneMockMvcBuilder不会加载Spring MVC配置文件，不能够用于通过模拟WEB URL请求测试
        //mockMvc = MockMvcBuilders.standaloneSetup(wac).build();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
    }
}
