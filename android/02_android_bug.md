[category]: android
[keywords]: socket
[source]: http://stackoverflow.com/questions/21353474/android-exception-using-closeable-interface-with-socket
[date]: 2014-11-07

# Socket
今天发现 Android 平台上一个奇怪的异常

	FATAL EXCEPTION: Thread-18
	java.lang.IncompatibleClassChangeError: interface not implemented
		at com.tencent.map.geolocation.util.Streams.closeQuietly(Streams.java:21)
		at com.tencent.map.geolocation.wap.LocalHttpServer$ClientHandler.run(LocalHttpServer.java:556)
		at java.lang.Thread.run(Thread.java:1019)

问题原因是调用了 `Streams.closeQuietly(socket)`，百 closeQuietly 实现如下：

	/**
	 * 关闭流
	 * 
	 * @param stream
	 */
	public static void closeQuietly(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

Stackoverflow 上有人遇到[同样问题]android-exception-using-closeable-interface-with-socket]

来自 [Android issue tracker][android-issue-tracker]解释如下：

> Socket implements Closeable only since API level 19 (kitkat) but that information isn't in the generated documentation.

原来如此！

[android-exception-using-closeable-interface-with-socket]: http://stackoverflow.com/questions/21353474/android-exception-using-closeable-interface-with-socket
[android-issue-tracker]: https://code.google.com/p/android/issues/detail?id=62909

---
