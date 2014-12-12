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
5. `scanPackageLI()`
6. `createDataDirsLI()`
7. TODO
8. 处理 `POST_INSTALL` 消息

第5步的代码如下

	if (res.returnCode == PackageManager.INSTALL_SUCCEEDED) {
	    args.doPreInstall(res.returnCode);
	    synchronized (mInstallLock) {
	        installPackageLI(args, true, res);
	    }
	    args.doPostInstall(res.returnCode, res.uid);
	}
	
这里的 args 是 InstallArgs 的实例

第6步的代码如下

	private void scanPackageLI() {
		...
		//invoke installer to do the actual installation
		int ret = createDataDirsLI(pkgName, pkg.applicationInfo.uid,
		                           pkg.applicationInfo.seinfo);
		...
	}

    private int createDataDirsLI(String packageName, int uid, String seinfo) {
        int[] users = sUserManager.getUserIds();
        int res = mInstaller.install(packageName, uid, uid, seinfo);
        if (res < 0) {
            return res;
        }
        for (int user : users) {
            if (user != 0) {
                res = mInstaller.createUserData(packageName,
                        UserHandle.getUid(user, uid), user);
                if (res < 0) {
                    return res;
                }
            }
        }
        return res;
    }

---

# installd分析
整理流程

1. 创建 socket (<src>\system\core\include\cutils\sockets.h: `android_get_control_socket`)
2. 监听 socket
3. 无限循环中 accept 等待连接
4. 处理连接
  1. 读 size
  2. 根据 size 读 buf
  3. 执行buf中的命令
5. 关闭连接



# 知识点
## 知识点一
<TODO 本地socket>

## 知识点二
installd 执行安装命令的实现方式比较巧妙：首先定义如下简单的结构体, 字段分别为名字、参数个数、指针函数；然后定义结构体数组。根据名字找到这个结构体，然后调用相应的方法(感觉比Java的接口强大、简单多了！)。

	struct cmdinfo {
	    const char *name;
	    unsigned numargs;
	    int (*func)(char **arg, char reply[REPLY_MAX]);
	};
	
	struct cmdinfo cmds[] = {
	    { "ping",                 0, do_ping },
	    { "install",              4, do_install },
	    { "dexopt",               3, do_dexopt },
	    { "movedex",              2, do_move_dex },
	    { "rmdex",                1, do_rm_dex },
	    { "remove",               2, do_remove },
	    { "rename",               3, do_rename },
	    { "freecache",            1, do_free_cache },
	    { "rmcache",              2, do_rm_cache },
	    { "protect",              2, do_protect },
	    { "getsize",              4, do_get_size },
	    { "rmuserdata",           2, do_rm_user_data },
	    { "movefiles",            0, do_movefiles },
	    { "linklib",              2, do_linklib },
	    { "unlinklib",            1, do_unlinklib },
	};

学习上面这种用法，写了个简单 [demo](../assets/pkg_install/struct_test.c)，demo的运行结果如下：

	PC0 /cygdrive/f/Users/kingcmchen/git/first_c/jni
	$ ./a.exe  hello 1
	do_hello arg0=hello arg1=1
	
	PC0 /cygdrive/f/Users/kingcmchen/git/first_c/jni
	$ ./a.exe  haha 2
	do_haha arg0=haha arg1=2

---
# QUESTION
如何实现 adb 安装

如何实现禁用权限

什么是 `FileObserver`

什么是 `ParcelFileDescriptor`

assets 目录中的文件被保存到哪里