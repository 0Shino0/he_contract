## RuoYi-Vue-Plus平台简介
RuoYi-Vue-Plus 是基于 RuoYi-Vue 针对 `分布式集群` 场景升级(不兼容原框架)

| 功能介绍 | 使用技术 | 文档地址 | 特性注意事项 |
|---|---|---|---|
| 当前框架 | RuoYi-Vue-Plus | [RuoYi-Vue-Plus文档](https://gitee.com/JavaLionLi/RuoYi-Vue-Plus/wikis/pages) | 重写RuoYi-Vue全方位升级(不兼容原框架) |
| satoken分支 | RuoYi-Vue-Plus-satoken | [satoken分支地址](https://gitee.com/JavaLionLi/RuoYi-Vue-Plus/tree/satoken/) | 使用satoken重构权限鉴权(仅供学习不推荐上生产) |
| 单体分支 | RuoYi-Vue-Plus-fast | [fast分支地址](https://gitee.com/JavaLionLi/RuoYi-Vue-Plus/tree/fast/) | 单体应用结构 |
| 原框架 | RuoYi-Vue | [RuoYi-Vue官网](http://ruoyi.vip/) | 定期同步需要的功能 |
| 前端开发框架 | Vue、Element UI | [Element UI官网](https://element.eleme.cn/#/zh-CN) | |
| 后端开发框架 | SpringBoot | [SpringBoot官网](https://spring.io/projects/spring-boot/#learn) | |
| 容器框架 | Undertow | [Undertow官网](https://undertow.io/) | 基于 Netty 的高性能容器 |
| 权限认证框架 | Spring Security、Jwt | [SpringSecurity官网](https://spring.io/projects/spring-security#learn) | 支持多终端认证系统 |
| 关系数据库 | MySQL | [MySQL官网](https://dev.mysql.com/) | 适配 8.X 最低 5.7 |
| 缓存数据库 | Redis | [Redis官网](https://redis.io/) | 适配 6.X 最低 4.X |
| 数据库框架 | Mybatis-Plus | [Mybatis-Plus文档](https://baomidou.com/guide/) | 快速 CRUD 增加开发效率 |
| 数据库框架 | p6spy | [p6spy官网](https://p6spy.readthedocs.io/) | 更强劲的 SQL 分析 |
| 多数据源框架 | dynamic-datasource | [dynamic-ds文档](https://www.kancloud.cn/tracy5546/dynamic-datasource/content) | 支持主从与多种类数据库异构 |
| 序列化框架 | Jackson | [Jackson官网](https://github.com/FasterXML/jackson) | 统一使用 jackson 高效可靠 |
| 网络框架 | Feign、OkHttp3 | [Feign官网](https://github.com/OpenFeign/feign) | 接口化管理 HTTP 请求 |
| Redis客户端 | Redisson | [Redisson文档](https://github.com/redisson/redisson/wiki/%E7%9B%AE%E5%BD%95) | 支持单机、集群配置 |
| 分布式限流 | Redisson | [Redisson文档](https://github.com/redisson/redisson/wiki/%E7%9B%AE%E5%BD%95) | 全局、请求IP、集群ID 多种限流 |
| 分布式锁 | Lock4j | [Lock4j官网](https://gitee.com/baomidou/lock4j) | 注解锁、工具锁 多种多样 |
| 分布式幂等 | Lock4j | [Lock4j文档](https://gitee.com/baomidou/lock4j) | 基于分布式锁实现 |
| 文件存储 | Minio | [Minio文档](https://docs.min.io/) | 本地存储 |
| 文件存储 | 七牛、阿里、腾讯 | [OSS使用文档](https://gitee.com/JavaLionLi/RuoYi-Vue-Plus/wikis/pages?sort_id=4359146&doc_id=1469725) | 云存储 |
| 监控框架 | SpringBoot-Admin | [SpringBoot-Admin文档](https://codecentric.github.io/spring-boot-admin/current/) | 全方位服务监控 |
| 校验框架 | Validation | [Validation文档](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/) | 增强接口安全性、严谨性 |
| Excel框架 | Alibaba EasyExcel | [EasyExcel文档](https://www.yuque.com/easyexcel/doc/easyexcel) | 性能优异 扩展性强 |
| 文档框架 | Knife4j | [Knife4j文档](https://doc.xiaominfo.com/knife4j/documentation/) | 美化接口文档 |
| 工具类框架 | Hutool、Lombok | [Hutool文档](https://www.hutool.cn/docs/) | 减少代码冗余 增加安全性 |
| 代码生成器 | 适配MP、Knife4j规范化代码 | [Hutool文档](https://www.hutool.cn/docs/) | 一键生成前后端代码 |
| 部署方式 | Docker | [Docker文档](https://docs.docker.com/) | 容器编排 一键部署业务集群 |
| 国际化 | SpringMessage | [SpringMVC文档](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc) | Spring标准国际化方案 |

## 本系统演示视频
> https://www.bilibili.com/video/BV1s44y1E7sm

## **基于区块链技术的电子合同管理系统**

> 本系统后台使用框架为ruoyi-vue-plus：https://github.com/JavaLionLi/RuoYi-Vue-Plus
>
> 该框架为前后端分离框架，后端使用SpringBoot，前端使用Vue
>
> 小程序UI框架为Lin-UI：https://github.com/TaleLin/lin-ui
>
> 区块链为Hyperledger Fabric，部署框架为minifabric：https://github.com/hyperledger-labs/minifabric
>
> 阿里OSS做对象存储：https://help.aliyun.com/product/31815.html
>
> IPFS做入链对象存储：https://ipfs.io/
>
> itext实现pdf盖章：https://www.tutorialspoint.com/itext/itext_setting_position_of_image.htm
>
> Hyperledger Fabric 官方手册：https://hyperledger-fabric.readthedocs.io/zh_CN/release-2.2/network/network.html

### 一些重要配置以及代码逻辑

#### 如何基于itext实现pdf盖章

> 详见PDFUtils声明与调用

```java
public class PDFUtils {
public static byte[] signPdf(String pdfUrl, String sealUrl) throws DocumentException, IOException {
        Document document = null;
        PdfStamper stamper = null;
        PdfReader reader = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = null;
        try {
            // pdf文件路径
            URL url = new URL(pdfUrl);
            // 读取pdf文件
            reader = new PdfReader(url);
            // 获取页数
            int pageCount = reader.getNumberOfPages();
            // 生成副本
            stamper = new PdfStamper(reader, outputStream);
            // 生成document
            document = new Document(reader.getPageSize(1));
            // 获取页面宽度
            float width = document.getPageSize().getWidth();
            // 获取页面高度
            float height = document.getPageSize().getHeight();
            System.out.println("width = " + width + ", height = " + height);
            // 读取seal
            Image sealImage = Image.getInstance(sealUrl);
            // 根据域的大小缩放印章
            sealImage.scaleToFit(150, 150);
            // 设置印章位置
            sealImage.setAbsolutePosition(width - 150 - 50, 50);
            // 在最后一页盖章
            PdfContentByte sign = stamper.getOverContent(pageCount);
            sign.addImage(sealImage);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
            if (stamper != null) {
                stamper.close();
            }
            if (reader != null) {
                data = outputStream.toByteArray();
                reader.close();
            }
            return data;
        }
    }

}
```



#### 如何使用SpringBoot连接线上区块链，调用链码

1. 首先搭建minifab在云服务器上，然后将minifab生成的“**peerOrganizations**"和”**ordererOrganizations**“文件复制到本地（**文件路径：vars/keyfiles**），创建一个名叫crypto-config的父目录保存（此处可自定义，需修改application.yml文件对照自己保存的路径）
2. 然后将minifab生成的网络配置文件也保存到本地（**文件路径：vars/profiles**），里面有各种语言的连接配置文件，按需所求

> 连接Fabric相关代码

##### application.yml

```yaml
--- # Fabric 超级账本gateway连接配置项
fabric:
  # 网络配置文件路径
  networkConfigPath: mychannel_connection_for_javasdk.yaml
  # 用户证书路径
  certificatePath: crypto-config/peerOrganizations/bc1.henriport.com/users/Admin@bc1.henriport.com/msp/signcerts/Admin@bc1.henriport.com-cert.pem
  # 用户私钥路径
  privateKeyPath: crypto-config/peerOrganizations/bc1.henriport.com/users/Admin@bc1.henriport.com/msp/keystore/priv_sk
  # 链码名字
  contractName: contract
  # 通道名字
  channelName: mychannel
  # mspId
  mspId: bc1-henriport-com
  # 用户
  user: admin
```



##### FabricProperties

```java
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

```

##### FabricUtils

```java
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
```



> 调用链码相关代码

##### Controller

```java
/**
     * 修改审核状态
     *
     * @param id
     * @param type
     * @return
     */
    @ApiOperation("修改审核状态")
    @PreAuthorize("@ss.hasPermi('system:contract:edit')")
    @Log(title = "电子合同管理", businessType = BusinessType.UPDATE)
    @PutMapping("/state/{id}/{type}")
    public AjaxResult<Void> changeState(@NotNull(message = "主键不能为空")
                                        @PathVariable Long id,
                                        @NotNull(message = "type不能为空")
                                        @PathVariable Boolean type,
                                        String pdfUrl, String sealUrl) {
        return toAjax(iHeContractService.setStateById(id, type, pdfUrl, sealUrl) ? 1 : 0);
    }
```

##### ServiceImpl

```java
   @Override
    public Boolean setStateById(Long id, Boolean type, String pdfUrl, String sealUrl) {
        String state = type ? "2" : "3";
        UpdateWrapper<HeContract> updateWrapper = new UpdateWrapper<>();
        // 如果审核通过
        try {
            if (type && pdfUrl != null && sealUrl != null) {
                // TODO 调用修改pdf方法,OSS文件重写
                byte[] data = PDFUtils.signPdf(pdfUrl, sealUrl);
                iSysOssService.updateFile(data, pdfUrl);
                // TODO 调用文件入ipfs方法
                String ipfsHash = IPFSUtils.upload(data);
                // 修改id对应的contract数据
                updateWrapper.eq("id", id)
                    .set("state", state)
                    .set("ipfs_hash", ipfsHash)
                    .set("is_link", 1);
                // TODO 调用数据入链方法，整体数据存入Fabric
                // 初始化
                Network network = fabricUtils.getNetwork();
                Contract contract = fabricUtils.getContract();
                // 获取contract
                HeContractVo heContractVo = queryById(id);
                byte[] addContractResult = contract.createTransaction("addContract")
                    .setEndorsingPeers(network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
                    .submit(
                        heContractVo.getId().toString(),
                        heContractVo.getTitle(),
                        heContractVo.getDescription(),
                        heContractVo.getType(),
                        ipfsHash,
                        heContractVo.getBelong().toString(),
                        heContractVo.getBelong().toString());
                log.info("addContract："+new String(addContractResult, StandardCharsets.UTF_8));
            }
        } catch (DocumentException documentException) {
            documentException.printStackTrace();
            return false;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return false;
        } catch (ContractException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return update(updateWrapper);
    }
```



#### 如何使用SpringBoot连接本地IPFS，上传文件

##### IPFSUtils

```java
public class IPFSUtils {
    /**
     * ipfs的服务器地址和端口
     */
    private static IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");

    /**
     * 将文件url转换为InputStream
     * @param url
     * @return
     */
    public static InputStream getFileInputSteam(String url){
          try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            return inStream;
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }

    /**
     * 将文件url转换为byte[]
     * @param url
     * @return
     */
    public static byte[] getFileSteam(String url){
          try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据
            byte[] btImg = readInputStream(inStream);
            return btImg;
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }

     /**
   * 从输入流中获取数据
   * @param inStream 输入流
   * @return
   * @throws Exception
   */
   public static byte[] readInputStream(InputStream inStream) throws Exception{
     ByteArrayOutputStream outStream = new ByteArrayOutputStream();
     byte[] buffer = new byte[1024];
     int len = 0;
     while( (len=inStream.read(buffer)) != -1 ){
         outStream.write(buffer, 0, len);
     }
     inStream.close();
     return outStream.toByteArray();
   }

    /**
     * 整合，根据URL地址上传文件
     * @param url
     * @return
     * @throws IOException
     */
   public static String uploadByUrl(String url) throws IOException {
       InputStream fileInputSteam = getFileInputSteam(url);
       return upload(fileInputSteam);
   }

    /**
     * 根据文件路径上传文件
     * src/image/hello.jpg
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String upload(String fileName) throws IOException {
        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File(fileName));
        MerkleNode addResult = ipfs.add(file).get(0);
        return addResult.hash.toString();
    }

    /**
     * 根据inputstream上传文件
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String upload(InputStream inputStream) throws IOException {
        NamedStreamable.InputStreamWrapper file = new NamedStreamable.InputStreamWrapper(inputStream);
        MerkleNode addResult = ipfs.add(file).get(0);
        return addResult.hash.toString();
    }

    /**
     * 根据文件二进制内容上传文件
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static String upload(byte[] data) throws IOException {
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(data);
        MerkleNode addResult = ipfs.add(file).get(0);
        return addResult.hash.toString();
    }

    /**
     * 根据hash值下载文件
     *
     * @param hash
     * @return
     */
    public static byte[] download(String hash) {
        byte[] data = null;
        try {
            data = ipfs.cat(Multihash.fromBase58(hash));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 替换文件？
     *
     * @param hash
     * @param destFile
     */
    public static void download(String hash, String destFile) {
        byte[] data = null;
        try {
            data = ipfs.cat(Multihash.fromBase58(hash));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (data != null && data.length > 0) {
            File file = new File(destFile);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(data);
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}

```






## Minifabric使用文档

### 设置spec.yaml配置文件

```shell
cd /mnt/d/minifabric-main/
vim spec.yaml

fabric:
  cas:
  - "ca.bc1.henriport.com"
  - "ca.bc2.henriport.com"
  peers:
  - "p1.bc1.henriport.com"
  - "p1.bc2.henriport.com"
  orderers:
  - "orderer1.henriport.com"
  settings:
    ca:
      FABRIC_LOGGING_SPEC: DEBUG
    peer:
      FABRIC_LOGGING_SPEC: DEBUG
    orderer:
      FABRIC_LOGGING_SPEC: DEBUG
  ### use go proxy when default go proxy is restricted in some of the regions.
  ### the default goproxy
  # goproxy: "https://proxy.golang.org,direct"
  ### the goproxy in China area
  goproxy: "https://goproxy.cn,direct"
  ### set the endpoint address to override the automatically detected IP address
  # endpoint_address: 104.196.45.144
  ### set the docker network name to override the automatically generated name.
  netname: "mysite"
  ### set the extra optins for docker run command
  # container_options: "--restart=always --log-opt max-size=10m --log-opt max-file=3"
```

### 启动simple链码例子

```shell
./minifab up -n simple -l go -v 1.0 -r true -o bc1.henriport.com
```

### 重启fabric区块链

```shell
./minifab restart
```

### 启动区块链浏览器

```shell
./minifab explorerup
```

### 关闭区块链浏览器

```shell
./minifab explorerdown
```

### 关闭fabric区块链

```shell
./minifab down
```

### 删除fabric区块链

```shell
./minifab cleanup
```

更多命令可通过如下命令查看

```shell
./minifab -h
```
### 编写链码

> 请看链码文件->main.go，仅供参考
### 安装链码

1. 根据例子自定义自己的链码

2. 将go.mod、go.sum、main.go文件打包新建名为"go"的文件夹

3. 在\minifabric-main\vars\chaincode\目录下创建自己自定义链码的文件夹，比如"contract"

4. 将"go"文件夹复制到"contract"文件夹下

5. 回到\minifabric-main\中启动区块链后输入如下代码

   ```shell
   ./minifab install -n contract -l go
   ./minifab approve
   ./minifab commit
   ./minifab initialize -p ''
   ```

   如果有go mod问题，回到"go"文件夹下输入以下命令

   ```shell
   go mod tidy
   go mod vendor
   ```

   高版本可用如下代码

   ```shell
   minifab ccup -n <chaincodename> -l <lang> -v 1.0
   ```

   详见：

   https://github.com/hyperledger-labs/minifabric/blob/main/docs/README.md#install-your-own-chaincode

### 测试用例

```shell
# 添加用户
./minifab invoke -p '"addUser","1", "张三", "1208471280957102", "120847124"'
./minifab invoke -p '"addUser","2", "李四", "128754812074", "197264192"'
./minifab invoke -p '"addUser","3", "王五", "128712354812074", "197264141292"'
# 通过用户id查询用户
./minifab query -p '"getUserById","1"'
# 修改用户
./minifab invoke -p '"updateUserById","3", "赵六", "128712354812074", "197264141292"'
# 添加合同
./minifab invoke -p '"addContract","1","租房", "租房合同", "个人", "jqoiwdnqowhjgoqwj", "1", "2"'
# 通过合同id查询合同
./minifab query -p '"getContractById","1"'
# 修改合同
./minifab invoke -p '"updateContractById","1", "租房2", "租房合同2", "非个人", "jqoiwdnqowhjgoqwj", "1", "3"'
# 删除合同
./minifab invoke -p '"deleteContractById","1"'

```





### 问题汇总

1. 代理被拒绝

==Error: chaincode install failed with status: 500 - failed to invoke backing implementation of 'InstallChaincode': could not build chaincode: docker build failed: docker image build failed: docker build failed: Error returned from build: 1 "go: github.com/golang/protobuf@v1.3.2: Get "https://proxy.golang.org/github.com/golang/protobuf/@v/v1.3.2.mod": dial tcp 172.217.24.17:443: connect: connection refused==
————————————————
版权声明：本文为CSDN博主「Jayo_o_0」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/weixin_42918620/article/details/120268692

```shell
// 到链码所在的目录下提前下载依赖包
cd fabric-samples/asset-transfer-basic/chaincode-go
//更改GO代理
go env -w GOPROXY=https://goproxy.io,direct
go env -w GO111MODULE=on
go mod vendor
```

之后重启网络部署 重新使用`restart`指令即可


## 更多参考资料
Hyperledger Fabric官方手册
https://hyperledger-fabric.readthedocs.io/en/latest/index.html

awesome-blockchain
https://github.com/chaozh/awesome-blockchain-cn

快速带你上手Hyperledger Fabric环境搭建＋开发测试
https://cloud.tencent.com/developer/article/1678190

登链社区
https://learnblockchain.cn/

minifabric
https://github.com/hyperledger-labs/minifabric

HyperLedger Fabric ChainCode开发——shim.ChaincodeStubInterface用法 
https://www.cnblogs.com/studyzy/p/7360733.html

Hyper ledger Fabric 2.3 部署链码中遇到的问题
https://blog.csdn.net/weixin_42918620/article/details/120268692

Fabric-sdk-java调试跑通
https://www.jianshu.com/p/1c7707ae3ffd

fabric-sdk-java
https://github.com/hyperledger/fabric-sdk-java

Spring Fabric Gateway
https://ecsoya.github.io/fabric/pages/gateway.html

fabric-sdk-java 简单示例
https://www.cnblogs.com/luyilan/p/15016703.html

Fabric2.0Java SDK实践-合约交易
https://blog.csdn.net/qq_28540443/article/details/104451259

Hyperledger Fabric架构及Java SDK实现
https://www.jianshu.com/p/d2c796849b42

【fabric】java sdk制作 Hyperledger Fabric Gateway SDK for Java
https://blog.csdn.net/qq_38904077/article/details/108038061

常见Fabric错误场景（持续更新）
https://www.jianshu.com/p/248530bf31b8

IPFS使用教程 | java对接IPFS的SDK
https://blog.csdn.net/weixin_42793960/article/details/117441564

多节点IPFS搭建以及采用Java SDK通信 
https://www.cnblogs.com/lyon-liu/p/14848958.html

ipfs - 命令行简介
http://cw.hubwiz.com/card/c/ipfs/1/1/1/

docker部署IPFS私有链
https://blog.csdn.net/cljdsc/article/details/110849702