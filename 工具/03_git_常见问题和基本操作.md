# 控制台下的中文乱码问题

## git log 无法显示中文名

![无法显示中文名](../assets/03_git_常见问题和基本操作-无法显示中文名.png)

进行如下设置

	git config core.quotepath false

![无法显示中文名](../assets/03_git_常见问题和基本操作-正常显示中文名.png)

## git log 乱码
![gitlog乱码](../assets/03_git_常见问题和基本操作-gitlog乱码.png)

进行如下设置

	git config i18n.logoutputencoding utf-8

![gitlog正常显示中文](../assets/03_git_常见问题和基本操作-gitlog正常显示中文.png)

## git diff 时出现乱码
一般保证文件采用 utf-8 编码 就能解决乱码问题
	
