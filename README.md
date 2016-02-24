

# 1. 用户邮件订阅插件：newsletter

官方网址：[newsletter](http://www.thenewsletterplugin.com/)

具体配置：

## 1.1 配置SMTP

Newsletter -- SETTINGS -- SMTP，本地使用163的host进行配置。

## 1.2 配置发送邮件

填写发送邮件地址，用于给订阅用户发送eMail，Newsletter -- Settings and More -- Basic Settings -- Sender email address，填写上一步中的Authentication 邮件地址。

## 1.3 配置form

在页面中显示邮件订阅form，此步中有两种方式，

- 第一种是使用widget方式，Appearance -- Widgets，将Newsletter加入到Sidebar中，然后在footer中添加<?php get_sidebar(); ?>来使用，但样式会有问题，为了使样式正确，只能将html源码截取出来然后修改，再把修改后的代码替换掉<?php get_sidebar(); ?>，才能显示正确。不是很方便。

- 第二种是使用shortcode的方式，即自己定义form。Newsletter -- LIST BUILDING -- SUBSCRIPTION FORM FIELDS -- Form code,拷贝Standard form code中的代码，黏贴到Newsletter -- LIST BUILDING -- CUSTOM FORMS -- Form1中，然后修改一下式样。在page中使用的话使用[newsletter_form form="1"]，在footer中使用的话使用<?php echo do_shortcode("[newsletter_form form='1']"); ?>

## 1.4 其他配置

- 相关订阅信息及邮件内容信息设置，Newsletter -- List building中，Confirmation设置订阅后的确认信息，Welcome设置确认后的欢迎信息。

- Newsletter -- Unsubscription设置退订后的信息。

# 2. Contact Form 7 插件

Contact Form 7官方网址：[Contact Form 7](http://contactform7.com/)

Contact Form 7z：

## 2.1 配置form

新建一个form。

## 2.2 使用shortcode

建立后将shorcode [contact-form-7 id="138" title="CONTACT US_PARTNER"]拷贝到page中使用

## 2.3 其他配置

- Mail中设置从哪里发送到哪里，如上图，即用户填写信息后，这些信息将由support@educationaxis.com
发送给seaalan@163.com 。有时候wordpress无法发送邮件，可以使用插件配置SMTP。
- Messages设置各种信息。

# 3. Contact Form DB 插件

Contact Form DB官方网址：[newsletter](http://cfdbplugin.com/)

Contact Form DB使用：

Contact Form DB需要配合Contact Form 7一起使用,否则会显示No form submissions in the database。

## 3.1 显示数据

上面配置好Contact Form 7后，要成功发送一次contact us邮件，然后Contact Form DB中才会有数据库
信息。

## 3.2 数据字段与form中输入框name相对应

Contact Form 7中form设置了几个字段，在Contact Form DB中就会有几个相应的字段。

## 3.3 数据库表与form的数量相对应

Contact Form 7中form设置了几个form，在Contact Form DB中就会有几个数据库表。

# 4. wordpress邮件配置插件：WP-Mail-SMTP

相关配置：Settings -- Email

# 5. wordpress 头尾部插件：Header and Footer

相关配置：

Settings -- Header and Footer -- Page head and footer

- -- Code to be added on HEAD section of every page 中写入头部代码，页面中使用<?php wp_head(); ?>引入。
- -- Code to be added before the end of the page 中写入尾部代码，页面中使用<?php wp_footer(); ?>引入。


