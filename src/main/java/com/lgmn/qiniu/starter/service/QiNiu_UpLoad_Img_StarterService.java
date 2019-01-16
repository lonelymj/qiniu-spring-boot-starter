package com.lgmn.qiniu.starter.service;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.File;
import java.io.InputStream;

public class QiNiu_UpLoad_Img_StarterService {

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

    public QiNiu_UpLoad_Img_StarterService () {}

    public QiNiu_UpLoad_Img_StarterService (String accessKey, String secretKey, String bucket) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucket = bucket;
    }

    /**
     * 获取上传凭证
     * @return
     */
    private String getUpLoadToken () {
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucket);
    }

    /**
     * 上传文件到七牛云
     * @param inputStream 文件流
     * @param key 自定义文件名
     * @return 自定义文件名
     * @throws QiniuException
     */
    public String upLoadImg(InputStream inputStream, String key) throws QiniuException {
        Configuration configuration = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(configuration);
        String upLoadToken = getUpLoadToken();
        Response response = uploadManager.put(inputStream, key, upLoadToken, null, null);
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        return putRet.key;
    }
}
