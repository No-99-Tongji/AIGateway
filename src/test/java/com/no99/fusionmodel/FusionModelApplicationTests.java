package com.no99.fusionmodel;

import com.no99.fusionmodel.models.DeepseekModel;
import com.no99.fusionmodel.models.DoubaoModel;
import com.no99.fusionmodel.models.QwenModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FusionModelApplicationTests {

    @Autowired
    private DeepseekModel deepseekModel;
    @Autowired
    private DoubaoModel doubaoModel;
    @Autowired
    private QwenModel qwenModel;

    @Test
    void contextLoads() {
        System.out.println(deepseekModel.requestData("你好"));
        System.out.println(doubaoModel.requestData("你好"));
        System.out.println(qwenModel.requestData("你好"));
    }

}
