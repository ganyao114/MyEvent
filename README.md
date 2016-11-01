# MyEvent
EventPoster 事件推送者 管理一切事件 包括View事件，ActivityLife事件，自定义事件等

	EventPoster


后续更新移步到 https://github.com/ganyao114/feizhai 准备一边维护一个 App 一边改进该框架。

一.目的
二.概述以及优势
1.模块化，易扩展
2.缓存管理
3.预加载
4.对于注册的实例的管理，防止 Leak
5.各模块 Handler 的管理
三.用法
1.与 MVP 结合使用
2.接口
3.扩展模块


________________


一.目的
        纯粹的想做一个轮子，已经有 EventBus 了，为什么还要去做？应为个人觉得·EvntBus还缺点东西。事件本身多种多样，绝不仅仅只是自定义的一些事件。在Android平台上，各种各样的事件驱动着代码的逻辑。View 事件，ActivityLife 事件，Receiver 事件，以及类似 EventBus 的自定义事件。简单来说 EvntPoster 就是一个综合的事件中心，它模块化，可扩展，接口统一，性能优秀。
二.概述以及优势
        1.模块化，易扩展
        EventPoster 是模块化的，简单的说由核心框架，以及各个模块组成，目前完成的有四个模块：View 事件，ActivityLife 事件，Receiver 事件，以及类似 EventBus 的自定义事件。如果需要扩展模块，仅仅需要实现事件相应的 Handler，Annotation ，以及缓存实体模型。核心框架会帮各个模块管理缓存，以及预加载等。
2.缓存管理
        为了减少开销，框架自然会避免重复的反射操作。核心框架会管理以 Annotation 对象为 Key 的缓存实体，实体内会存储反射结果。
        3.预加载
        为了避免在·regist 的时候给予主线程过多的压力，核心框架提供了异步预加载的支持，实现 Handler 接口的 parse 方法返回包含反射结果的实体对象。这是线程安全的，在Application 内可以启用多个线程对需要注册的类进行预加载。这样通过缓存以及预加载，在事件 regist 以及发生的时候，反射操作有且只有 invoke，性能接近接口通讯。
4.对于注册的实例的管理，防止 Leak
        默认仅有核心框架持有注册的 Object 对象，各个模块可以使用 api getInsts(Class clazz) 从核心框架获取相应类的实例。原则上不建议各模块私自持有 Object 实例。
        5.各模块 Handler 的管理
        用户通过框架主类 EventPoster 可以通过工厂方法获得各模块的 Handler 实例。原则上各个模块的 Handler 都是单例的。


三.用法
        1.与 MVP 结合使用
        框架内提供了一个简单的 MVP Presenter 的微框架。为了让 Activity/Fragment 纯粹成为一个View 的容器，只保留 set/get 方法，同时 Presenter 又需要获得绝对的控制力。所以 Presenter 需要监听到各个事件。View 的点击触摸，Receiver 的发生，Activity 生命周期，以及一些自定义事件等。
        2.接口
* regist 各种事件统一调用 EventPoster.Regist(object) 即可，如果想解析父类中的事件即调用 EventPoster.RegistDeep. UnRegist 同理。
* 获取某个模块的 Handler EventPost.With(Class handlerType);然后调用各自模块的接口
* 预加载，Application 中调用 EventPoster.PreLoad(Class[] classes); 递归加载包括父类的 EventPoster.PreLoadDeep(Class[] classes);


        3.扩展模块
* 自定义 Annotation 加上@AnnotationBase(你的 Handler.class)
* 自定义 Handler 实现 init destory parse load unload 以及特殊需求时穿透的 regist remove方法。
* 实现 EventEntity 缓存实体。
