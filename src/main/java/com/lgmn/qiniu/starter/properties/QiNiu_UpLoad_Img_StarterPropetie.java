package com.lgmn.qiniu.starter.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("qiniu.service")
public class QiNiu_UpLoad_Img_StarterPropetie {
    private String accessKey;
    private String secretKey;
    private String bucket;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

}
