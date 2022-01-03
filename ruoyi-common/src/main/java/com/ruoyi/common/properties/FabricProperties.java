package com.ruoyi.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Fabric 配置属性
 *
 * @author Henriport
 * @since 2021/11/15
 */
@Data
@Component
@ConfigurationProperties(prefix = "fabric")
public class FabricProperties {

    /**
     * 网络配置文件路径
     */
    private String networkConfigPath;

    /**
     * 用户证书路径
     */
    private String certificatePath;

    /**
     * 用户私钥路径
     */
    private String privateKeyPath;

    /**
     * 链码名字
     */
    private String contractName;

    /**
     * 通道名字
     */
    private String channelName;

    /**
     * mspId
     */
    private String mspId;

    /**
     * 用户
     */
    private String user;

}
