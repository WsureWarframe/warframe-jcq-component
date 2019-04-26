# warframe-jcq-component
一个java语言编写的酷Q机器人插件，借助了JCQ插件做中转，调用酷Q的原生API

#### 这是一个`废弃的项目`，本人将不再提供后续维护，新项目请移步[warframe-info-api](https://github.com/WsureDev/waframe-info-api).但是这并不妨碍您的使用和学习

## 如何使用
1.在使用之前，您需要了解什么是[jcq](https://cqp.cc/t/37318)，[以及如何入门](https://github.com/Sobte/JCQ-CoolQ)

2.在您配置好了jcq环境之后，在 酷Q目录\app\com.sobte.cqp.jcq\app\ 内拷贝`com.example.demo.json`、`com.example.demo.jar`，并且新建`com.example.demo`文件夹

3.在`com.example.demo`文件夹内拷贝如下文件：`Dict.json`、`keys.json`、`rewards.json`、`riven.json`、`rm.json`、`setting.json`、`lib文件夹`、`wiki文件夹`

4.运行酷Q，启用jcq插件，在jcq的设置里设置jre目录

5.在jcq应用里启用com.example.demo，然后重启酷Q

6. enjoy it

## 快速使用
1.下载32位jdk，并且安装

2.将Release版本包解压丢进酷Q的`CQA.exe`或`CQP.exe`所在目录

3.运行酷Q，启用jcq插件，在jcq的设置里设置jre目录

4.在jcq应用里启用com.example.demo，然后重启酷Q

## 个性配置

请自行修改json文件，注意json文件需要保持GBK编码（否则会产生乱码）

## 致谢

本项目使用的API接口来自[WFCD的https://api.warframestat.us/pc](https://github.com/WFCD/warframe-worldstate-parser)
词库来自[云之幻(Richasy)](https://github.com/Richasy)，wiki查询数据来源来自[灰机wiki](https://warframe.huijiwiki.com/)
WM查询来自[Warframe.Market](https://warframe.market/),RM查询来自[Riven.Market](https://riven.market/)
