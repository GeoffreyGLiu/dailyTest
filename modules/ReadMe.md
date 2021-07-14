
Test Project 各module内容一览:

AIDLTest:关于Client和Service间IPC操作.
    Service端提供书籍的增加,单本查询,书目列表查询的服务.
    Client端由AIDL远程调用服务,并在控件展示.
    
Algorithm:关于各种算法的
    例如查询回文字符串,合并两个有序数组,找出第K大的数字,找出不重复的区间,投飞镖最小次数等.
    目前这些,后续待补充.
    
app:
    目前有greenDao(待迁移),view绘制钟盘的(待完善)

BaseModule:project内部module依赖的基础module
    集成了常用的第三方库ConstraintLayout,Glide,RecyclerView等,后续待添加.
    可以用于Module间通信跳转,待做.
    
ContentProviderTest:关于ContentProvider和ContentResolver
    以表格的形式保存数据,,对数据执行CRUD
    读取手机联系人的操作
    
Dagger2Retrofit:关于Dagger2,Retrofit封装网络请求,还有Dagger2+Retrofit实现MVP框架的.

GradleTest:暂时未使用,预留

HybridTest:关于JS和Android互相交互
    JS调用Android:
        1.addJavaScriptInterface,添加映射到JS的android方法,调用
        2.WebView的setWebViewClient,shouldOverrideUrlLoading()拦截Uri的
        3.WebView的setWebChromeClient,onJsAlert(),onJsPrompt(),onJsConfirm().
    android调用JS:
        1.WebView.loadUrl()
        2.WebView.evaluateJavascript(),这个好处是能接受到JS方法的返回值.
        
IOStream:关于字节流,字符流,增强流,转换流
    将内容输出到文件,持久化保存
    
RecyclerMove:关于RecyclerView的操作,功能模块以Fragment封装,动态加载Fragment.
    滑动到指定item位置
    ItemDecoration的
    粘性头部StickyHead
    ViewFlipper(不该放这里的,先扔这里)
    
StorageDirectory:关于存储路径,文件保存数据和读取数据的