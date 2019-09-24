package com.lgmn.qiniu.starter.service;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    public QiNiu_UpLoad_Img_StarterService() {
    }

    public QiNiu_UpLoad_Img_StarterService(String accessKey, String secretKey, String bucket) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucket = bucket;
    }

    /**
     * 获取上传凭证
     *
     * @return
     */
    public String getUpLoadToken() {
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucket);
    }

    /**
     * 上传文件到七牛云
     *
     * @param inputStream 文件流
     * @param key         自定义文件名
     * @return 自定义文件名
     * @throws QiniuException
     */
    public String upLoadImg(InputStream inputStream, String key) throws QiniuException {
        String upLoadToken = getUpLoadToken();
        Response response = getUploadManager().put(inputStream, key, upLoadToken, null, null);
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        return putRet.key;
    }

    /**
     * 上传图片到七牛云
     *
     * @param multipartFile 上传的文件
     * @param path          路径_可不传
     * @return
     * @throws QiniuException
     */
    public String upLoadImg(MultipartFile multipartFile, List<String> path) throws IOException {
        UploadManager uploadManager = getUploadManager();
        String key = getkey(multipartFile, path);
        Response response = uploadManager.put(multipartFile.getInputStream(), key, getUpLoadToken(), null, null);
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        return putRet.key;
    }

    /**
     * 生成文件名
     *
     * @param multipartFile 文件
     * @param path          路径
     * @return
     */
    private String getkey(MultipartFile multipartFile, List<String> path) {
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.indexOf("."));
        String fileName = System.currentTimeMillis() + suffix;
        String pathKey = "";
        if (path == null || path.size() <= 0) {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            pathKey = df.format(new Date()) + "/" + fileName;
        } else {
            pathKey = StringUtils.join(path, "/") + "/" + fileName;
        }
        return pathKey;
    }

    /**
     * 获取 UploadManager
     *
     * @return
     */
    private UploadManager getUploadManager() {
        Configuration configuration = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(configuration);
        return uploadManager;
    }
}
