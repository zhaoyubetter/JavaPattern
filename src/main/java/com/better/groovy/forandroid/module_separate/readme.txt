**从主App，进行模块的资源的抽取；
**** 操作步骤：
1. 新建工程后，从主App相应包中，copy 其对应的所有Java代码；
2. layout 资源：
    通过 `LayoutFinder` Groovy 类，进行layout布局文件的提取，并将提取的layout，copy 到新建的工程中；
3. drawable 资源：
