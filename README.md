# incubatorUI
incubatorTest
完成基本通信，解析服务器的数据显示；
添加了Glide图片框架查看图片，保存图片。
添加自定义控件，添加缩略图事件。
完成图片放大缩小功能


裁剪图片之后能按比例放大填充

获取图片流
方法：将获取图片流的线程放到
ApplicationUtil类里面

完成页面切换，视频正常更新

重写finish，将系统回退键利用（系统回退键默认执行finish（）；）



添加指示灯；实时显示连接状态

优化主界面返回逻辑

处理断开连接，初始化Socket

已修复：图片裁剪时牙签的头花花没了。裁剪功能呢完成
添加了高级涂鸦

添加日期选择器，选择对应日期获取对应日期的图片

12.12  全屏页面的imageview控件改成LinearLayout控件，在控件中添加部分按钮

12.12  增加tcp发送消息的方法

12.13 合并图片编辑框架

12.13 添加控制按键监听与数据的发送

12.13 优化图片编辑框架

12.13 下位机数据反馈解析算法完成

12.13 大图模式增加编辑功能

12.13 优化编辑图片框架

12.14，添加位置选择页面，SiteSelection,通过全屏控制的视野转跳

12.14  ApplicationUtil添加数据响应反馈方算法

12.14 主页面定时请求传感器数据线程

12.15 绑定各个培养皿自定义事件，触发点击发送计算好的指令，且等待指令响应

12.15 制作原点按键布局，坐标按键布局（绝对位置），相对位置的还没做

12.16 添加相对位置布局，添加调焦布局

12.16 归零所有按键后端指令绑定完成

12.16 定点相对位置指令绑定完成

12.16 定点绝对位置指令绑定完成

12.17 调焦绝对位置，相对位置指令绑定完成，修复单选bug

12.17  添加rgb灯控制界面，从全屏控制的灯光按钮进去，

12.17 RGB灯光指令绑定完成

12.17 位置选择界面 发送指令显示等待的提示框，显示响应成功或者失败

12.17 主页面UI更新

12.18  在灯光控制里面加了紫外灯  UV lmap

12.19 紫外灯后端指令绑定完成

12.19 主页面业务完成（所有数据的请求）

12.19 所有指令使用完成

12.20 添加区域灯光控制

12.21 修改图片bug

12.22-12.25 修改步数设置

03.18  github pull test 4567

















