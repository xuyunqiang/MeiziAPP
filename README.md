###MeiziAPP

![icon](/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png "")

每日提供技术干货的App

###干货数据源

应用中的所有干货数据均来自[干货集中营](http://gank.io/)。

### App设计

整个项目借鉴自 @drakeet 的[妹纸](https://github.com/drakeet/Meizhi)。但是在原项目基础上，
自己在代码层面和UI层面上做了自己的修改。

##效果图
![Meizi](http://7xq7wz.com1.z0.glb.clouddn.com/Meizi.gif)

####代码设计

此项目紧作为Demo级别，采用了MVP分层架构对原有项目进行整合，想更了解MVP设计可参考末尾链接；
此项目通过Retrofit进行普通数据的网络请求，Picasso进行图片加载，Dagger通过注解的方式进行依赖注入
整个项目结构很清晰，不过还有很多方面有待完善，后面将写一遍关于android架构方面的文章，尽请期待。

PS（通过对Retrofit beta2.0 及Okhttp2.5源码分析，得出要通过HttpClient.cancel(TAG)取消请求的方式是无效的，
具体原因将在后期博客会写到，同时也会讲解采用Retrofit-beta3.0版本就能及时取消网络请求的关键源码）


###依赖库   

* [RxJava](https://github.com/ReactiveX/RxJava) 
* [Dagger](https://github.com/square/dagger)
* [OkHttp](https://github.com/square/okhttp)
* [Picasso](https://github.com/square/picasso)
* [Retrofit](https://github.com/square/retrofit)
* [Butterknife](https://github.com/JakeWharton/butterknife)

###参考资料

* [drakeet/Meizhi](https://github.com/drakeet/Meizhi)
* [浅谈 MVP in Android](http://blog.csdn.net/lmj623565791/article/details/46596109)
* [Effective Android UI](https://github.com/pedrovgs/EffectiveAndroidUI)


### License

    /*
     *       
     * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
     * Copyright (C) 2015 Xuyunqiang <1539140295@qq.com>
     *
     * Meizhi is free software: you can redistribute it and/or modify
     * it under the terms of the GNU General Public License as published by
     * the Free Software Foundation, either version 3 of the License, or
     * (at your option) any later version.
     *
     * Meizhi is distributed in the hope that it will be useful,
     * but WITHOUT ANY WARRANTY; without even the implied warranty of
     * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     * GNU General Public License for more details.
     *
     * You should have received a copy of the GNU General Public License
     * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
     */
