# HaruRPC
基于java aio 的RPC 远程调用框架

# 组件介绍

## Serializer

序列化和反序列的工具类，项目的实现为基于Gson的序列化工具

## IOHandler

从Channel中读取数据并交由Serializer处理的类，本身是异步读取数据 在读取数据时 提供读取完成回调

## RPCClient

发送RPC请求的客户端 将调用目标的接口名，方法名，参数列表发送给服务器端。

设计上是线程安全的，每个RPC请求会放入队列中 依次调用

## RPCClientProxy

客户端RPC调用的代理类 针对AIO的非阻塞式特性 在发送请求时使用了同步锁调用了wait() 在RPC返回是 调用 norifyAll() 来实现阻塞式调用

## RPCServer
通过哈希表映射 根据类名找到调用的具体对象，目前是的静态设置（可以考虑使用注解）
```java
    private static HashMap<String, Object> mapper = new HashMap<>();

    static {
        mapper.put(UserService.class.getName(), new UserServiceImpl());
    }

```

example

```java

public class ServerTest {
    public static void main(String[] args) throws Exception {
        new Thread(ServerTest::run){}.start();
        Thread.sleep(999999);
    }

    public static void run(){
        RPCServer server = new RPCServer(3839) ;
        try {
            server.startListen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




public class ClientTest {
    public static void main(String[] args) throws InterruptedException {
        new Thread(ClientTest::run) {
        }.start();
        Thread.sleep(999999);
    }

    public static void run() {
        UserService userService = RPCProxyBuilder.createProxy(UserService.class);
        User user = new User("admin", "admin");

        System.out.println(userService.login(user));

        user = userService.login("111", "admin");
        System.out.println(user);
    }
}

```
