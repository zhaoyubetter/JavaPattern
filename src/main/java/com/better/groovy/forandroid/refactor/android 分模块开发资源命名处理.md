# 1. Android 分模块开发资源重名问题

因为Android中资源名称不能重复，一般在另一个module开始时，我们会在此module对应的

gradle文件中，添加一个资源前缀，但这个并不能帮我们为资源名称，自动添加前缀，他实现的只是一个

新建资源时，提示你名称必须添加指定的前缀；



如下：

```
icon_app_drawable.png
添加前缀后：
moduleName_icon_app_drawable.png
```



> 通过模块名，并以此模块名为前缀，为该模块内的所有资源统一添加前缀，并替换所有该资源出现的位置，来实现资源名称唯一；



但我们在module开发中，新增资源时，经常忘记添加资源名的前缀，直到集成到主app时，才会意识到

冲突问题，这个时候，手动去改，麻烦且容易出错；

## 1.1 如何解决此问题

通过分析Android工程，我们得出Android App 工程的资源分为2大类：

- `文件夹形式资源`，即每个资源在改特定的`文件夹`下，该类型文件夹大约如下：
  - layout
  - drawable
  - anim
  - color
  - menu
  - raw
  - xml
  - mipmap

- `values 类型资源`，即在`values`文件夹下，`除了id可重用`，其他目前都不允许重用，这些大约有如下：
  - string
  - arrays
  - color
  - dimens
  - bool
  - integer
  - id 
  - attr
  - style



## 1.2 发现资源规律

以上资源内容，会出现在2个地方，一是java源代码中，二是资源间的引用，整理表格如下：


| 资源类型 | 源代码中 | xml引用中 |备注|
| :--- | ------ | :---- |:--|
| layout | R.layout.XXX | @layout/XXX |布局文件|
| drawable | R.drawable.XXX | @drawable/XXX ||
| anim | R.anim.XXX | @anim/XXX ||
| color | R.color.XXX | @color/XXX ||
| mipmap | R.mipmap.XXX | @mipmap.XXX ||
| menu | R.menu.XXX |  |Xml 暂无引用|
| raw | R.raw.XXX |  |Xml 暂无引用|
| xml | R.xml.XXX |  |Xml 暂无引用|
|  |  |  ||
| string | R.string.XXX | @string/XXX ||
| color | R.color.XXX | @color/XXX ||
| dimens | R.dimen.XXX | @dimen/XXX ||
| arrays | R.array.XXX | @array/XXX ||
| style | R.style.XXX | @style/XXX |需要考虑到parent，比较复杂|
| attr | R.attr.XXX |  |需要特殊处理，主要在自定义属性上|



> 从上表格看，大部分资源都是有规律的，也就是我们可以读取源代码文件，与xml文件，来进行整体资源的重命名与整体替换；



## 1.3 资源重名替换的风险性

1. 如何保证替换原子性，即：要么文件全部替换成功，要么全部不替换；

   > 目前没有找到合适方案，希望大家多指教；

2. 如何保证不替换公共库资源，比如：当前模块中a，引用了公共库的资源 `common.png`，如何保证

   `common.png` 不被重命名成 `a_common.png`

   > 解决：只替换本模块中声明的资源，也就是只读取本模块中res/下定义的资源；

3. attrs 与 style 资源的特殊性

   这2块资源，可能用的不是很多，暂时可以先手动修改一下；麻烦在以下原因：

   因为style可能会是继承下来的，所以这块替换容易出问题，**后期再考虑**；

   attrs同样如此，主要用来自定义控件属性这块；**后期再考虑**；

## 1.4 方案可行性

**如果暂不考虑批量文件替换，重命名事务性问题**，通过上面的分析，我们得知，如果要进行资源重命名，我们需要通过程序检索出某资源出现在该模块位置，然后替换之：

因为源代码与xml文件都是文本文件内容，所以我们可以通过`正则`全工程检索，然后将用到资源按顺序分别重命名；然后替换到现有文件；从而达到目的；

比如：

1. 资源名（资源声明）：

```xml
<string name="default_loading">正在加载…</string>
```

2. 源代码中：

```java
String s = getResources().getString(R.string.default_loading);
```

3. xml中引用

```xml
<TextView
    ...
    android:text="@string/default_loading"/>
```



假设前缀为：`common_ui_`, 那么资源`default_loading` 出现的所有位置，应该替换成：

```java
// 1.资源名处
<string name="common_ui_default_loading">正在加载…</string>

// 2.源代码处
String s = getResources().getString(R.string.common_ui_default_loading);

// 3.xml引用处
<TextView
    ...
    android:text="@string/common_ui_default_loading"/>
```

这样就完成了一个string类型资源的替换，类似的，可用这个方式替换其他资源；



# 2.具体实现

​	











