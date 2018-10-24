/*
 * TOP SECRET Copyright 2006-2015 Transsion.com All right reserved. This software is the confidential and proprietary
 * information of Transsion.com ("Confidential Information"). You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you entered into with Transsion.com.
 */
package com.codegen.test.controller;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codegen.test.BaseTestCase;
import com.codegen.vo.Page;
import com.codegen.vo.Result;
import com.gencode.generate.persistence.model.Sites;
import com.gencode.generate.web.controller.SitesController;

/**
 * ClassName:SitesMapperTest <br/>
 * Date: 2018年10月23日 下午5:36:38 <br/>
 * 
 * @author fenglibin1982@163.com
 * @Blog http://blog.csdn.net/fenglibing
 * @version
 * @see
 */
public class SitesControllerTest extends BaseTestCase {

    @Autowired
    private SitesController sitesController;

    @Test
    public void testSelectByPage() {
        Page<Sites> page = new Page<Sites>();
        page.setOffset(0);
        page.setPageSize(10);
        Sites sites = new Sites();
        // sites.setUrl("http://andorralavella.ad/");
        sites.setCountryCode("USXX");
        page.setObj(sites);
        Result<Page<List<Sites>>> result = sitesController.selectByPage(page);
        if (result.getData().getObj() != null) {
            result.getData().getObj().forEach((s) -> System.out.println(s.getUrl()));
        }
    }
}
