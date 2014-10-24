[category]: android
[keywords]: android,view
[source]: android doc
[date]: 2014-10-22

# head 1
> A ViewGroup is a special view that can contain other views (called children.) The view group is the base class for layouts and views containers. 

# 自定义 Layout

实现自定义 Layout 的关键是: 

1. 实现 `onMeasure()`: Ask all children to measure themselves and compute the measurement of this layout based on the children.  
2. `onMeasure()`: 最后调用 `setMeasuredDimension()`, Report our final dimensions.
3. 实现 `onLayout()`: Position all children within this layout.

其他非关键的步骤还包括:

1. 重写 `xxxLayoutParams()`: 重写 layout params 相关的方法
2. 定义 `xxxLayoutParams` 类: 一般继承 `MarginLayout` 即可

# LayoutParams
1. LayoutParams: LayoutParams are used by views to tell their parents how they want to be laid out. 
2. MarginLayoutParams: 跟 LayoutParams 很像但支持 margins 

