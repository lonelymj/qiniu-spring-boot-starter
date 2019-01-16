package com.lgmn.qiniu.starter.autoConfigure;

import com.lgmn.qiniu.starter.properties.QiNiu_UpLoad_Img_StarterPropetie;
import com.lgmn.qiniu.starter.service.QiNiu_UpLoad_Img_StarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(QiNiu_UpLoad_Img_StarterPropetie.class)
@ConditionalOnClass(QiNiu_UpLoad_Img_StarterService.class)
@ConditionalOnProperty(prefix = "qiniu.service", value = "enable", matchIfMissing = true)
public class QiNiu_UpLoad_Img_StarterAutoConfigure {

    @Autowired
    private QiNiu_UpLoad_Img_StarterPropetie qiNiu_upLoad_img_starterPropetie;

    @Bean
    @ConditionalOnMissingBean(QiNiu_UpLoad_Img_StarterService.class)
    QiNiu_UpLoad_Img_StarterService qiNiu_upLoad_img_starterService () {
        return new QiNiu_UpLoad_Img_StarterService(qiNiu_upLoad_img_starterPropetie.getAccessKey(), qiNiu_upLoad_img_starterPropetie.getSecretKey(), qiNiu_upLoad_img_starterPropetie.getBucket());
    }


}
