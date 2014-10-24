[category]: work
[keywords]: sdk
[source]: 

![01_定位SDK大小对比及优化分析](../assets/01_定位SDK大小对比及优化分析.png)

TencentLocationSDK.jar 包大小为 171KB，目前仍然为最小。但考虑到 libtencentloc.so 大小为 24KB，实际上已经比高德的定位SDK要大一些了。

TencentLocationSDK 包大小主要由以下几个部分构成：
1. jar = 网络包(80KB) + 定位功能(70KB) + Geofence功能(20KB)
2. so, native 代码

可优化的点：
1. 使用旧的网络包(仅20KB) 左右
2. 将 Geofence功能 分拆，打包成 core.jar + geofence.jar
3. 精简 log, log 中包含大量字符串
4. 优化代码及proguard配置(可能没有明显效果)
