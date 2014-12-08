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
# QUESTION
如何实现 adb 安装

如何实现禁用权限

