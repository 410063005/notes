[category]: mat
[keywords]: mat,java,memory
[source]: http://www.blogjava.net/rosen/archive/2010/05/21/321575.html
[date]: 2014-11-01

为什么使用 MAT --> OOM

为什么有 OOM --> Java 内存管理，对象引用

什么是 [GC Roots][GC Roots]

什么是 Shallo Size 和 Retined Size

什么是 Heap Dump

[source2]: http://www.blogjava.net/rosen/archive/2010/06/13/323522.html

ClassLoader 的本质

sample:

[OOMHeapTest](assets/OOMHeapTest.java)

这个代码向Map和数组中存放1000000个 Pilot 对象，同时限制最大堆内存为32MB，运行结果如下图所示。

![OOM](assets/oom.png)

日志保存到 java_pid632.hprof 中

[MAT Wiki]: http://wiki.eclipse.org/index.php/MemoryAnalyzer 
[Interned Strings]: http://mindprod.com/jgloss/interned.html
[Strong,Soft,Weak,Phantom Reference]: http://mindprod.com/jgloss/weak.html
[Tuning Garbage Collection with the 5.0 Java[tm] Virtual Machine]: http://java.sun.com/docs/hotspot/gc5.0/gc_tuning_5.html
[Permanent Generation]: http://mindprod.com/jgloss/permgen.html
[Understanding Weak References译文]: http://blog.csdn.net/xtyyumi301/archive/2008/10/04/3015493.aspx
[Java HotSpot VM Options]: http://java.sun.com/javase/technologies/hotspot/vmoptions.jsp
[Shallow and retained sizes]: http://www.yourkit.com/docs/90/help/sizes.jsp
[JVM Memory Structure]: http://www.yourkit.com/docs/kb/sizes.jsp
[GC Roots]: http://www.yourkit.com/docs/java/help/gc_roots.jsp
---
[memoryanalyzer Blog]: http://dev.eclipse.org/blogs/memoryanalyzer/
[java类加载器体系结构]: http://kenwublog.com/structure-of-java-class-loader
[ClassLoader]: http://mindprod.com/jgloss/classloader.html
