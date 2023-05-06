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

