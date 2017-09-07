![image](https://github.com/FortuneDream/PocketMusic/blob/master/readme_logo.png)

# 口袋乐谱


### 客官看过来~     

当漫天飞雨，当心绪使然，当练习音乐……也许你突然想拿起身边的乐器，或琴，或筝，或笛，或古风，或西洋。你苦于乐谱难寻，音乐难叙，此时此刻，此景此情，唯有口袋乐谱在你身边。奇妙的口袋里，有你想要的一切乐谱，你可以随时、随地、随心玩你想要的音乐。产品特色：及时提供各种乐器的乐谱，上传你想与他人分享的乐曲，经典的，流行的，应有尽有，支持古筝，口琴，钢琴，吉他等十余种乐器，更有志同道合的乐友与你同在。我随缘为你寻谱，你随缘为我演奏。音乐不止，乐谱不尽，口袋乐谱在音乐的旅途中与你同行。

 
**搜索乐谱**：可以搜索到任意最新，最全的曲谱，包括网络搜索，本站搜索。

**支持多种曲谱类型**：支持十五种乐器，包括吉他谱，钢琴谱，简谱等等。

**一键下载**：喜欢的乐谱一键下载，不让您错过每一次感动。

**每日推荐**：每天自动更新最时尚、最潮流的乐谱。

**演奏模式**：喜欢的曲子一定要自己演奏出来才有意思啊~

**分享乐谱**：好的曲子分享给大家也不失为一种快乐。

**求谱系统**：搜索不到怎么办？发帖子向谱友求助吧！花费少量的硬币可以提升求谱指数哦~

**用户系统**：我的收藏，我的分享，硬币系统，每日签到，支持开发者~~（圈钱大法好）~~

**其他功能**：帮助系统，版本介绍，电子钢琴（尝鲜版）,分享小口袋


### 示例：

#### Splash
![image](https://github.com/FortuneDream/PocketMusic/blob/master/readme_0.png)
* ~~嘿嘿嘿，好像有什么奇怪的东西加进来了~~

#### 乐谱界面
* 本地曲谱可以录音
* 网络曲谱可以下载，收藏，分享

![image](https://github.com/FortuneDream/PocketMusic/blob/master/readme_1.png)

#### 本地乐谱
* 曲谱来源：本地导入，网络下载
* 演奏模式：可以录音的哦~

![image](https://github.com/FortuneDream/PocketMusic/blob/master/readme_2.png)

#### 网络乐谱
* 直接搜索
* 推荐曲谱
* 乐器类型
* 信息反馈

![image](https://github.com/FortuneDream/PocketMusic/blob/master/readme_3.png)

#### 求谱
* 谱友来荐
* 求谱帖子
* 点击右下角的小飞机发布求谱帖~

![image](https://github.com/FortuneDream/PocketMusic/blob/master/readme_4.png)

#### 个人界面
* 应用评分
* 我的硬币（通过每日签到或者分享曲谱可以获得）
* 我的求谱
* 我的收藏
* 我的分享
* 帮助文档
* 设置中心

![image](https://github.com/FortuneDream/PocketMusic/blob/master/readme_5.png)


#### 直接搜索
* 网络搜索
* 本站搜索

![image](https://github.com/FortuneDream/PocketMusic/blob/master/readme_6.png)

### 实现
* **T-MVP** 架构
* iOS 与 Android的混合UI风格
* 加载图片 [Glide](https://github.com/bumptech/glide)
* 展示图片 [PhotoView](https://github.com/chrisbanes/PhotoView)
* 网络请求 [OkHttp](https://github.com/square/okhttp)
* 数据库 [OrmLite](https://github.com/j256/ormlite-android)
* 爬虫 [Jsoup](https://github.com/jhy/jsoup)
* Bmob云服务器 [Bmob](http://www.bmob.cn/)
* 各种UI轮子

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
* 数据库
    * 一对多
    * 多对多
* T-MVP与弱引用
* 利用线程池的阻塞队列实现顺序下载
* 压缩apk包以及屏幕适配（VectorDrawable，Webp,权限系统）

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
项目大小：Java：3万行，历时一年，独立开发