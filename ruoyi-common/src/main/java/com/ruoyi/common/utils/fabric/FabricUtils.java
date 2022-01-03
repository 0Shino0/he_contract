package com.ruoyi.common.utils.fabric;

import com.ruoyi.common.properties.FabricProperties;
import org.hyperledger.fabric.gateway.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Instant;

/**
 * Fabric 工具类
 *
 * @author Henriport
 * @since 2021/11/16
 */
@Component
public class FabricUtils {
    @Resource
    private FabricProperties fabricProperties;

    private Network network;

    public Network getNetwork() {
        return this.network;
    }

    private Contract contract;

    public Contract getContract() {
        return this.contract;
    }

    @PostConstruct
    private void FabricUtils() {
        try {
            // 获取当前文件类路径
            String resourcePath = this.getClass().getResource("/").toURI().toString();
            System.out.println(resourcePath);
            X509Certificate certificate = readX509Certificate(Paths.get(new URI(resourcePath+fabricProperties.getCertificatePath())));
            PrivateKey privateKey = getPrivateKey(Paths.get(new URI(resourcePath+fabricProperties.getPrivateKeyPath())));
            Wallet wallet = Wallets.newInMemoryWallet();
            wallet.put(fabricProperties.getUser(), Identities.newX509Identity(fabricProperties.getMspId(), certificate, privateKey));
            //根据配置文件 获取Fabric网络连接对象
            Gateway.Builder builder = Gateway.createBuilder()
                .identity(wallet, "admin")
                .networkConfig(Paths.get(new URI(resourcePath+fabricProperties.getNetworkConfigPath())));
            //连接网关
            Gateway gateway = builder.connect();
            //获取通道
            network = gateway.getNetwork(fabricProperties.getChannelName());
            //获取合约对象
            contract = network.getContract(fabricProperties.getContractName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static X509Certificate readX509Certificate(final Path certificatePath) throws IOException, CertificateException {
        try (Reader certificateReader = Files.newBufferedReader(certificatePath, StandardCharsets.UTF_8)) {
            return Identities.readX509Certificate(certificateReader);
        }
    }

    private static PrivateKey getPrivateKey(final Path privateKeyPath) throws IOException, InvalidKeyException {
        try (Reader privateKeyReader = Files.newBufferedReader(privateKeyPath, StandardCharsets.UTF_8)) {
            return Identities.readPrivateKey(privateKeyReader);
        }
    }
}
