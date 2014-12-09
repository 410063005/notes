[category]: android
[keywords]: packagemanager
[source]: http://blog.jobbole.com/67286/
[date]: 2014-12-08

`PackageManagerService.installPackageWithVerificationAndEncryption()` 分析

1. 检查进程是否有INSTALL权限
2. 检查用户是否有INSTALL权限
3. 构造 UserHandle
4. 构造 InstallParams

---

> 下面的过程执行在Package Manger服务中。

> + 等待；
> + 添加一个包文件到安装进程的队列中；
> + 确定合适的地方来安装包文件；
> + 复制apk文件到一个给定的目录下；
> + 确定应用的UID；
> + 请求installd守护程序进程；
> + 创建应用目录和设置权限；
> + 提取dex代码到缓存目录中；
> + 解析packages.list、system、data和packages.xml的最新状态；
> 向系统发送广播消息，消息带有安装完成效果的名字Intent.ACTION_PACKAGE_ADDED：如果是更新，会带有新的（Intent.ACTION_PACKAGE_REPLACED）。

分别对应

1. 空闲状态
2. `HandlerParams.startCopy` (MCS_BOUND消息时且mPendingInstalls不为空)
3. `createInstallArgs` (InstallParams.handleStartCopy() 方法中)
4. `InstallArgs.copyApk()`

---
# QUESTION
如何实现 adb 安装

如何实现禁用权限

什么是 `FileObserver`

什么是 `ParcelFileDescriptor`