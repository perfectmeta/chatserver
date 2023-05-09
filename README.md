# chatserver

## Key管理

各个外部API调用使用的Token需要使用一个配置文件管理。

配置文件的模板放在了 chatserver/key.properties中。但是，在开发阶段，你不应该使用它。

配置文件遵从以下加载顺序：

1. 检查环境变量 `CHAT_KEY_FILE`, 如果该环境变量存在，且指向了一个有效的配置文件地址， 那么使用该配置文件。
2. 如果第一步检查失败，则在项目目录下读取 key.properties文件。

综上，key.properties文件可用，但是为了保护key不被提交到公共仓库，在开发阶段，你应该在开发机上通过环境变量`CHAT_KEY_FILE`来指定key文件位置，并保护好它，不要提交到不受控制的范围内。

在代码中，如果要使用key，只需要导入security.KeyManager类即可，该类会在加载时去读取配置，导入配置数据。

---

## 语音识别

语音识别我们使用了讯飞语音的webapi， 讯飞提供了javasdk，但是其文档明确提出javasdk是为了客户端涉及，sdk不支持多路并发，
所以我们没有采用sdk方案，但是也做了集成。

讯飞语音一般的账号 webapi 并行可以到50路，超过50路以后就会出错。前期我们作demo的时候这个并发量
感觉应该是够了，后面记得要升级账号。

语音识别(TTS也是)使用时，仅需 var inputStream = XFYasr.makeSession(outputStream)即可，然后
后续直接从inputStream中读取内容。在没有动态修正的业务场景下这么做比较合适。增加动态修正后，
暂时考虑增加流式延迟，仍然保留这个工作模式，在封装类中缓存一定长度的文本，确保动态修正不会去
修正它了之后再注入inputStream。