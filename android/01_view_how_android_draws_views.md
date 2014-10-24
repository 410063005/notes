[category]: android
[keywords]: android,view
[source]: https://developer.android.com/guide/topics/ui/how-android-draws.html
[date]: 2014-10-22

# How Android Draws Views
Activity 得到焦点后将请求绘制其布局. Activity 只用提供布局结构的根节点, 而 Android framework 负责整个绘制流程.

从布局中的根节点开始绘制. 布局树(layout tree)首先被测量(measure), 然后绘制. 绘制过程是这样的:　遍历整个布局树, 分别渲染(rendering)每个跟无效区域(invalid region)相交的 View. 而每个 ViewGroup 负责要求其子 View 绘制(即调用子 View 的 draw()方法), 而子 View 则负责完成自身的绘制. 以中遍优先的方式遍历布局树, 所以父节点会先于子节点被绘制, 兄弟节点则按布局树中的先后顺序被绘制. 

> framework 不会绘制 invalid region 以外的 View, 且负责绘制 View 的背景.
> 可以调用 invalidate() 方法强制 View 重绘

绘制布局分两个过程: 一个 measure 过程, 和一个 layout 过程. 前者是在 measure(int, int) 中实现的, 对布局树采用深度优先遍历的过程. 每个 View 会在这个递归过程中往下传递维度要求(dimension specifications). measure 过程完成后, 每个 View 会保存其测量结果(measurements). 后者由 layout(int, int, int, int), 也是深度优先遍历, 这个过程中每个父节点负责使用 measure 过程计算出的结构来对子节点进行定位.

当 View 对象的 measure() 方法返回后, 它的 getMeasuredWidth() 和 getMeasuredHeight() 值必须已设置, 当然其所有子节点的 getMeasuredWidth() 和 getMeasuredHeight() 值也必须已设置. View 对象的 measured width 和 measure height 值必须奠定其父节点给定的限制. 这样才能保证 measure 过程结束后, 所有的父节点会接受子节点的测量结果. **父节点可能调用子节点的 measure() 方法不止一次.** 比如, 父节点可能先使用 unspecified dimension 来对每个子节点进行测量以确定子节点大小, 如果所有子节点的大小和太大或太小则再使用实际值(??原始文档并没有说明这个值具体来源)调用子节点的 measure() (也即, 如果子节点间不能就大小达成一致, 父节点将会干预 measure 过程, 并在第二次调用 measure() 时设置限制). 

> 可以调用 requestLayout() 方法发起 layout 过程. 这个方法通常用由 View 认为当前边界无法容纳时调用.

measure 过程使用两个类来通信以确定大小. ViewGroup.LayoutParams 类用于 View 对象告知其父节点当前节点所需的大小和位置. 最基本的 ViewGroup.LayoutParams  
仅描述宽度和高度. 对于宽度和高度, 可以使用如下值指定:

+ 准确值(an exact number)
+ MATCH_PARENT, 即当前 View 期望跟父节点一样大(要减去 padding)
+ WRAP_CONTENT, 即当前 View 期望足够大可容纳其内容即可(要减去 padding)

ViewGroup 不同的子类有各自继承自 ViewGroup.LayoutParams 的 LayoutParams. 比如, RelativeLayout 有自己的 ViewGroup.LayoutParams 子类, 它支持子节点水平居中或垂直居中.

MeasureSpec 对象用于父节点向子节点告知大小要求. MeasureSpec 可以是以下三种模式中的一种:

+ UNSPECIFIED: 用于父节点确定子节点期望的大小. 比如, LinearLayout 可能以高度 UNSPECIFIED, 宽度240 pixels 来调用子节点的 measure(), 以确定子节点宽度为240 pixels时的期望高度.
+ EXACTLY: 用于父节点指定子节点的准确大小. 子节点必须使用这个大小, 且保证其所有子节点都遵守这个大小.
+ AT MOST: 用于父节点指定子节点的最大允许大小. 子节点必须保证其自身及所有子节点都遵守这个大小.
