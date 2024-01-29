## 一、背景

在分布式事务场景中，有一类是单个应用中连接多个数据源的场景，也就是单应用——>多个数据库。分布式事务框架**Atomikos** 就很适合解决这种单应用——多数据源下的分布式事务问题。

Atomikos 的底层原理是基于 JTA 规范，通过事务管理器协调和管理分布式事务，使用两阶段提交协议保证事务的一致性，提供日志和恢复机制用于持久化和恢复事务状态，以及使用资源适配器与各个资源进行交互。这些组件和机制共同提供了可靠的分布式事务管理功能。

## 二、集成准备

由于 Atomikos 是XA协议下的一种具体实现，所以数据库必须要支持XA协议才行，MySql的InnoDB引擎默认是支持XA协议的，我们可以在数据库中运行 **SHOW ENGINES** 进行查看。

应用框架以及对应的版本信息如下：
- Mysql：5.7.31
- JDK：1.8
- Spring Boot：2.6.3
- Mybatis Plus：3.3.0
- Druid：1.1.20

Atomikos 的依赖我们使用 **spring-boot-starter-jta-atomikos** ，这是 Spring Boot 提供了一个用于整合 Atomikos 的 starter。

业务场景模拟用户下单，创建订单——扣减商品库存的场景，订单数据存储在订单库，商品库存数据存储在库存库。所以应用中需要连接这两个数据源，保证下单业务下这两个数据源数据的一致性。

## 三、运行 yimo-atomikos-samples

### 1. 下载代码

```
git clone https://github.com/htwdjqr/yimo-atomikos-samples.git
```

### 2. 数据库

运行工程中sql文件夹下的 **atomikos.sql** 文件，完成数据库创建与数据初始化。

### 3. 运行应用

启动 **Application**。

### 4. 验证

请求 [http://localhost:8100/business/buy](http://localhost:8100/business/buy) 接口地址，body：

```json
{
    "userId":"1",
    "commodityCode":"C201901140001",
    "name":"fan",
    "count":2,
    "amount":"100"
}
```

分布式事务回滚可以注释 BusinessServiceImpl 类中的代码模拟异常

```java
@Transactional(rollbackFor = Throwable.class)
public String handleBusiness(BusinessDTO businessDTO) {
    // 创建订单
    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setUserId(businessDTO.getUserId());
    orderDTO.setCommodityCode(businessDTO.getCommodityCode());
    orderDTO.setOrderCount(businessDTO.getCount());
    orderDTO.setOrderAmount(businessDTO.getAmount());
    orderService.createOrder(orderDTO);

    // 模拟抛出异常
    // int i = 1 / 0;

    // 扣库存
    CommodityDTO commodityDTO = new CommodityDTO();
    commodityDTO.setCommodityCode(businessDTO.getCommodityCode());
    commodityDTO.setCount(businessDTO.getCount());
    stockService.decreaseStock(commodityDTO);

    // 模拟两个操作都执行完再抛出异常
    int i = 1 / 0;
    return "success";
}
```

通过测试我们可以发现，上面任意一个地方抛出异常，两个库的数据都能被回滚，证明分布式事务生效。

## 四、总结

在 Spring Boot 中使用 atomikos 的关键是配置 **xaDataSource**，也就是项目代码中 config 目录下的两个数据源配置类，在使用上也是通过 **@Transactional** 注解来管理事务。

由于 Atomikos 是XA规范下的两阶段提交协议，其优点包括：

- 能够实现数据的有效隔离，满足数据的全局一致性。
- 代码无侵入。
- 数据库的广泛支持，大部分关系型数据库都支持XA规范。

但是缺点包括：性能差、同步阻塞的时间长（prepare 后，分支事务进入阻塞阶段，收到 XA commit 或 XA rollback 前必须阻塞等待）。



