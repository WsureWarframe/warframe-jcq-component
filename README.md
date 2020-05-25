# warframe-jcq-component
一个java语言编写的酷Q机器人插件，借助了JCQ插件做中转，调用酷Q的原生API

#### 此项目将重已重构，请移步 [【warframe-bot】](https://github.com/WsureDev/waframe-bot) 
原因： 在经过调研HTTP-Api和FF14的 獭.net 机器人之后，我不得不承认它的设计在某些方面很棒，但是也面临一些尴尬的窘境
    
    http-api + 后台服务 架构的优缺点：
    优点： 用户侧配置简单，安装插件只需要http-api插件，本地不处理任何业务，只上报
    缺点： 服务端使用ws长连接或者http请求与用户侧通信，对服务器侧的性能要求十分高，并且长连接会占用连接池
     ，这就是为什么ff14的獭机器人用户面临官方窝满了被挤掉和ws假死需要重启服务。解决方案只有再搭新窝
      
 所以经过权衡，最终决定使用本地处理无需联网业务+服务端提供需要联网数据借口的结构。这样虽然用户侧安装的东西可能会多一点，但是保证了性能和可靠性。
 更新可以使用release版本的懒人包轻松部署（小版本可选择不更新）
 
 统一远端服务项目请移步[【warframe-info-api】](https://github.com/WsureDev/waframe-info-api) 

## 如何使用
1.在使用之前，您需要了解什么是[jcq](https://cqp.cc/t/37318)，[以及如何入门](https://github.com/Sobte/JCQ-CoolQ)

2.在您配置好了jcq环境之后，在 酷Q目录\data\app\org.meowy.cqp.jcq\app\ 内拷贝`com.example.demo.json`、`com.example.demo.jar`，并且新建`com.example.demo`文件夹

3.在`com.example.demo`文件夹内拷贝如下文件：`Dict.json`、`keys.json`、`rewards.json`、`riven.json`、`rm.json`、`setting.json`、`lib文件夹`、`wiki文件夹`

4.运行酷Q，启用jcq插件，在jcq的设置里设置jre目录

5.在jcq应用里启用com.example.demo，然后重启酷Q

6. enjoy it

## 快速使用[【视频教程】](https://www.bilibili.com/video/av53646289)
1.下载32位jdk，并且安装

2.将Release版本包解压丢进酷Q的`CQA.exe`或`CQP.exe`所在目录

3.运行酷Q，启用jcq插件，在jcq的设置里设置jre目录

4.在jcq应用里启用com.example.demo，然后重启酷Q

## 个性配置

请自行修改json文件，注意json文件需要保持GBK编码（否则会产生乱码）

## 致谢

本项目使用的API接口来自[WFCD的https://api.warframestat.us/pc](https://github.com/WFCD/warframe-worldstate-parser)

词库来自[云之幻(Richasy)](https://github.com/Richasy)

wiki查询数据来源来自[灰机wiki](https://warframe.huijiwiki.com/)

WM查询来自[Warframe.Market](https://warframe.market/)

RM查询来自[Riven.Market](https://riven.market/)
