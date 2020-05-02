# KeywordBlock
A Bukkit Plugin to Block Keyword
  
[English](#English)  
[中文](#中文)  
  
## English
### Usage
Download latest releases to your server and reboot or plugman load

### Features
 - Add/Remove keyword event broadcast message to administrator and console
 - Player try to send block word broadcast message to administrator and console
 - Filter all special characters (Only support English|Chinese, others not all)
 - Reminder message customization
 - Auto mute (Need to install other mute plugin)
 - Detect keyword in commands

### Permission
 - KeywordBlock.admin  
   - KeywordBlock's Admin

### Command
 - /keywordblock
 - /keywordblock help
 - /keywordblock reload
 - /keywordblock list
 - /keywordblock add <keyword>
 - /keywordblock del <keyword>

### Config
```yaml
# Main function enable or disable
# detect | detect_other need reload plugin
# reload plugin can be by rebooting or plugman reload
function:
  # detect chat message
  detect: true
  # message broadcast
  admin-broadcast: true
  # auto mute
  mute: enable
  # detect other command
  detect_other: true
  # admin bypass
  bypass: true

# Message (not include mute)
message:
  broadcast:
    admin:
      - "&2[&dKeywordBlock&2] &9%player_name% &btry to send blocked word &c%player_message%"
  warn:
    player:
      - "&2[&dKeywordBlock&2] &c%player_message% include blocked word"
      - "&2[&dKeywordBlock&2] &cDo not try to send blocked word"

# Mute
mute:
  # After how many times, mute
  times: 3
  # mute commnd
  command: "mute %player% 30min Please mind your words"
  # mute message
  message:
    - "&2[&dKeywordBlock&2] &cYou were muted. Please mind your words."

# detect other command
detect_other:
  # command list
  command:
    - "/tell"
    - "/msg"
    - "/t"
    - "/r"

# keywordblock commands language
command_lang:
  # plugin name
  keywordblock_name: "&2[&dKeywordBlock&2]"
  help:
    - "&rUse &7/keywordblock help &rto get help"
    - "&rUse &7/keywordblock reload &rto reload"
    - "&rUse &7/keywordblock list &rto get list"
    - "&rUse &7/keywordblock add <keyword> &rto add"
    - "&rUse &7/keywordblock del <keyword> &rto del"
  reload:
    - "&rReload Config"
  list:
    prefix:
      - "&rKeyword List:"
    empty:
      - "&3Null"
  add:
    without_key:
      - "&rPlease add with keyword"
    exists:
      - "&rThis keyword already exists"
    success:
      - "&radd a keyword"
  del:
    without_key:
      - "&rPlease del with keyword"
    not_exist:
      - "&rThis keyword is not exist"
    success:
      - "&rdel a keyword"
  unknown:
    - "&cUnknown Command"
  no_perm:
    - "&r&cYou do not have permission to use this command"

# Keyword list
words: []
```
### License
MIT License

## 中文
### 使用方法
下载最新的版本然后重启服务器或者使用plugman加载

### 特点
 - 添加/删除关键词 广播提示给管理
 - 玩家试图发送关键词 广播提示给管理
 - 过滤特殊符号 (仅支持中文|英语, 其他语言暂时无法过滤全部符号)
 - 支持消息自定义
 - 自动禁言 (需要安装其他mute插件)
 - 检测指令里的关键词

### 权限组
 - KeywordBlock.admin  
   - KeywordBlock 管理员

### 指令
 - /keywordblock
 - /keywordblock help
 - /keywordblock reload
 - /keywordblock list
 - /keywordblock add <keyword>
 - /keywordblock del <keyword>

### 配置
```yaml
# 主要功能的禁用与启用
# 更改 detect | detect_other 配置需要重载插件而不是重载配置
# 可以通过重启服务器或者plugman reload实现
function:
  # 公屏聊天检测
  detect: true
  # 消息广播
  admin-broadcast: true
  # 自动禁言
  mute: enable
  # 检测其他指令
  detect_other: true
  # 管理不被检测
  bypass: false

# 消息 (不包含自动禁言)
message:
  broadcast:
    admin:
    - '&2[&d关键词屏蔽&2] &9%player_name% &b试图发送违禁词语 &c%player_message%'

  warn:
    player:
    - '&2[&d关键词屏蔽&2] &c%player_message% 包含违禁词语!'
    - '&2[&d关键词屏蔽&2] &c请不要尝试发送违禁词语!'

# 自动禁言
mute:
  # 多少次之后禁言
  times: 3
  # 执行指令
  command: "mute %player% 30min 注意文明用语"
  message:
    - "&2[&d关键词屏蔽&2] &c你被禁言了 请注意文明用语"

# 检测其他指令
detect_other:
  command:
  - "/tell"
  - "/msg"
  - "/t"
  - "/r"

# 指令返回的消息
command_lang:
  # 插件名称
  keywordblock_name: '&2[&d关键词屏蔽&2]'
  help:
  - '&r使用 &7/keywordblock help &r获取帮助'
  - '&r使用 &7/keywordblock reload &r重载配置'
  - '&r使用 &7/keywordblock list &r获取关键词列表'
  - '&r使用 &7/keywordblock add <keyword> &r添加关键词'
  - '&r使用 &7/keywordblock del <keyword> &r删除关键词'
  reload:
  - '&r重载配置完成'
  list:
    prefix:
    - '&r关键词列表:'
    empty:
    - '&3无'
  add:
    without_key:
    - '&r缺少关键词'
    exists:
    - '&r这个关键词已存在'
    success:
    - '&r成功添加了关键词'
  del:
    without_key:
    - '&r缺少关键词'
    not_exist:
    - '&r这个关键词不存在'
    success:
    - '&r成功删除了关键词'
  unknown:
  - '&c未知指令'
  no_perm:
  - '&r&c你没有权限使用该指令!'

# 关键词列表
words: []
```
### 开源协议
MIT License