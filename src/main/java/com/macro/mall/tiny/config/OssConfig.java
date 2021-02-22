package com.macro.mall.tiny.config;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Endpoint: 访问域名，通过该域名可以访问OSS服务的API，进行文件上传、下载等操作
 * Bucket: 存储空间，是存储对象的容器，所有存储对象都必须隶属于某个存储空间
 * Object: 对象，对象是OSS存储数据的基本单元，也被称为OSS的文件
 * AccessKey: 访问密钥，指的是访问身份验证中用到的AccessKeyId 和 AccessKeySecret.
 *
 * 流程介绍：
 * 1. Web前端请求应用服务器，获取上传所需参数(如OSS的accessKeyId, policy, callback等)
 * 2. 应用服务器返回相关参数
 * 3. Web前端直接向OSS服务发起上传文件请求
 * 4. 等上传完成后OSS服务会回调应用服务器的回调接口
 * 5. 应用服务器返回响应给OSS服务
 * 6. OSS服务将应用服务器回调接口的内容返回给Web前端
 */
@Configuration
public class OssConfig {
    @Value("${aliyun.oss.endpoint}")
    private String ALIYUN_OSS_ENDPOINT;
    @Value("${aliyun.oss.accessKeyId}")
    private String ALIYUN_OSS_ACCESSKEYID;
    @Value("${aliyun.oss.accessKeySecret}")
    private String ALIYUN_OSS_ACCESSKEYSECRET;
    @Bean
    public OSSClient ossClient(){
        return new OSSClient(ALIYUN_OSS_ENDPOINT,ALIYUN_OSS_ACCESSKEYID,ALIYUN_OSS_ACCESSKEYSECRET);
    }
}
