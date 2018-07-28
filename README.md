![image](https://github.com/FortuneDream/PocketMusic/blob/master/readme_logo.png)

# 口袋乐谱


### 客官看过来~     

当漫天飞雨，当心绪使然，当练习音乐……也许你突然想拿起身边的乐器，或琴，或筝，或笛，或古风，或西洋。你苦于乐谱难寻，音乐难叙，此时此刻，此景此情，唯有口袋乐谱在你身边。奇妙的口袋里，有你想要的一切乐谱，你可以随时、随地、随心玩你想要的音乐。产品特色：及时提供各种乐器的乐谱，上传你想与他人分享的乐曲，经典的，流行的，应有尽有，支持古筝，口琴，钢琴，吉他等十余种乐器，更有志同道合的乐友与你同在。我随缘为你寻谱，你随缘为我演奏。音乐不止，乐谱不尽，口袋乐谱在音乐的旅途中与你同行。

 
**搜索乐谱**：可以搜索到任意最新，最全的曲谱。

**支持多种曲谱类型**：支持十五种乐器，包括吉他谱，钢琴谱，口琴谱等等。

**本地曲谱**：喜欢的乐谱可以通过下载或从相册导入到本App，在没有网络不好的时候可以看哦。

**每日推荐**：每天自动更新最时尚、最潮流的乐谱。

**用户系统**：可以看到自己的收藏，分享，求谱，关注

**谱友圈子**：在谱友圈子你可以看到谱友的动态，谱友分享的曲谱，以及需要帮忙的求谱贴。

**其他功能**：帮助系统，用户反馈，分享小口袋，硬币系统（可持续发展），支持开发者。


### 示例：

#### Splash
![image](https://github.com/FortuneDream/PocketMusic/blob/master/readme_0.png)
* 把音乐装进口袋

#### 网络曲谱
* 提供各种乐器类型的铺子
* 左上角为提交意见和建议，右上角为帮助
* 顶部的Banner可以弹出加QQ群的提示框

![image](https://github.com/FortuneDream/PocketMusic/blob/master/readme_1.png)

#### 本地乐谱
* 曲谱来源：本地导入，网络下载
* 演奏模式：可以录音的哦~

![image](https://github.com/FortuneDream/PocketMusic/blob/master/readme_2.png)

#### 搜索曲谱
* 直接搜索,基本都能搜到
* 本站搜索（暂时停用）

![image](https://github.com/FortuneDream/PocketMusic/blob/master/readme_3.png)

#### 个人页面
* 我的关注
* 我的求谱帖
* 我的收藏
* 我的分享
* 硬币与捐赠系统系统
* 应用商城好评
* 分享App

![image](https://github.com/FortuneDream/PocketMusic/blob/master/readme_4.png)

#### 曲谱界面
* 手势操作可放大和缩小
* 点击右上角可以隐藏标题栏和底部录音栏
* 本地曲谱底部界面显示为录音，网路曲谱底部界面显示为下载，收藏，分享。

![image](https://github.com/FortuneDream/PocketMusic/blob/master/readme_5.png)

### 实现
* **T-MVP** 架构
* 加载图片 [Glide](https://github.com/bumptech/glide)
* 展示图片 [PhotoView](https://github.com/chrisbanes/PhotoView)
* 网络请求 [OkHttp](https://github.com/square/okhttp)
* 数据库 [OrmLite](https://github.com/j256/ormlite-android)
* 爬虫 [Jsoup](https://github.com/jhy/jsoup)
* Bmob云服务器 [Bmob](http://www.bmob.cn/)
* Design包的各种UI轮子,以及自定义View

### 技术尝试
* 设计模式：
    * Builder,
    * Listener,
    * Single
    * Proxy-Stub
    * Strategy
    * State
    * Factory
* Recycler通过viewType实现多种ViewHolder
* Activity的异常生命周期处理和启动模式
* 数据库设计(一对多，多对多)
* T-MVP与弱引用
* 利用线程池的阻塞队列实现顺序下载(也可以采用IntentService)
* 压缩apk包（VectorDrawable，Webp）
* 版本适配（包括权限，通知栏，应用内apk安装等），目前适配到Android 8.0 Oreo
* ActivityLifecycle和LoadingDialog联动

### 下载安装

* 目前已登陆小米应用商店,应用宝

* [官方网站](http://http://pocketmusic.bmob.site/)

* Andriod兼容版本：**4.1.1** 及以上

### 注意事项

* 权限说明
    * 手机信息（上传手机型号，Android版本，Crash分析）   
    * SD卡权限（本地存储乐谱） 
    * 录制音频需要开启录音权限（运行时权限）

#### By 鹏君
项目大小：Java：3万行，历时两年，独立开发