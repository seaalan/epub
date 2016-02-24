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

# 6. 代码

## 6.1 page

### 6.1.1 landing

``` html
<div class="main-content">
<div class="landing">
<div class="common-header landing-header">
<div class="container">
<h2>WE WANT EVERYONE ON EARTH
TO GET THE EDUCATION THEY NEED</h2>
Introducing a fully customizable way for schools to find students
</div>
</div>
<div class="common-body container">
<h2 class="line-side">HOW TO GET INVOLVED</h2>
INTRO COPY NEEDED HERE
<div class="common-item clearfix">
<div class="col-md-6">
<div class="landing-item">
<img src="http://localhost/wp/wp-content/themes/EDUCATION/images/smart_protrait.png" alt="" />
<h3>GET MORE STUDENTS</h3>
Find out how EducationAxis can send you qualified student leads
</div>
<button class="btn btn-common landing-item-btn btn-lg">LEARN MORE</button>
</div>
<div class="col-md-6">
<div class="landing-item">
<img src="http://localhost/wp/wp-content/themes/EDUCATION/images/smart_doctoria.png" alt="" />
<h3>RECOMMEND SCHOOLS</h3>
We'll pay you to refer students to schools from your website
</div>
<button class="btn btn-common landing-item-btn btn-lg">LEARN MORE</button>
</div>
</div>
</div>
</div>
</div>
```
### 6.1.2 partner

``` html
<div class="main-content">
<div class="partner">
<div class="common-header partner-header">
<div class="container">
<h2>WE WANT EVERYONE ON EARTH
TO GET THE EDUCATION THEY NEED</h2>
<h3>(We'll even pay you to join us)</h3>
EducationAxis has created a tool that lets you recommend amazing schools to prospective students.
People find the right schools, schools find the right people, and you get a commission for making it happen.
</div>
</div>
<div class="common-body container">
<h2 class="line-side">GET PAID TO RECOMMEND SCHOOLS</h2>
<p class="partner-contact">WE WILL INVITE PREFERRED PARTNERS TO TRY THE TOOL SOON: SIGN UP NOW TO BE THE FIRST TO USE IT</p>
<div class="common-item partner-item">
<div class="form-inline common-form">
<h2>JOIN THE WAITING LIST</h2>
[contact-form-7 id="138" title="CONTACT US_PARTNER"] 
</div>
</div>
</div>
</div>
</div>
```
### 6.1.3 school

``` html
<div class="main-content">
<div class="school">
<div class="common-header school-header">
<div class="container">
<h2>WE CONNECT STUDENTS WITH
THE BEST SCHOOLS</h2>
EducationAxis is on a mission to help the world's prospective students find their ideal schools.
Show your school be on the list?
</div>
</div>
<div class="common-body container">
<div class="school-send">
<h2 class="line-side">WE SEND YOU TARGETED STUDENT INQUIRES</h2>
<p class="school-contact">POTENTIAL STUDENTS ARE BOMBARDED WITH GENERIC COLLEGE ADS ALL T HE TIME. THAT RESULTS IN TWO LARGE
PROBLEMS FOR THE EDUCATION WORLD:</p>
<div class="send-item-list clearfix">
<div class="col-md-6 send-item clearfix">
<img class="col-md-2" src="http://localhost/wp/wp-content/themes/EDUCATION/images/1.png" alt="" />
<p class="col-md-10">PROSPECTS GET MORE SPECIALIZED SCHOOL SUGGESTIONS</p>
</div>
<div class="col-md-6 send-item clearfix">
<img class="col-md-2" src="http://localhost/wp/wp-content/themes/EDUCATION/images/2.png" alt="" />
<p class="col-md-10">THE BEST SCHOOLS ARE PUT IN FRONT OF MORE PROSPECTS</p>
</div>
<div class="col-md-6 send-item clearfix">
<img class="col-md-2" src="http://localhost/wp/wp-content/themes/EDUCATION/images/3.png" alt="" />
<p class="col-md-10">ISSUE NUMBER THREE</p>
</div>
<div class="col-md-6 send-item clearfix">
<img class="col-md-2" src="http://localhost/wp/wp-content/themes/EDUCATION/images/4.png" alt="" />
<p class="col-md-10">ISSUE NUMBER FOUR</p>
</div>
</div>
</div>
<div class="school-personInfo">
<h2>CONTACT US TO GET MORE LEADS</h2>
<p class="school-contact">IF YOU ARE INTERESTED IN LEARNING HOW EDUCATIONAXIS CAN GIVE YOU MORE TARGETED STUDENT INQUIRIES,
CONTACT US: WE'LL GET BACK TO YOU.</p>
<div class="common-item school-item">
<div class="form-inline common-form">
<h2>GET YOUR SCHOOL ON THE SHORT LIST</h2>
[contact-form-7 id="156" title="CONTACT US_SCHOOL"]
</div>
</div>
</div>
</div>
</div>
</div>
```
## 6.2 Header and Footer

### 6.2.1 Header

``` html
<header class="main-header">
  <div class="container clearfix">
    <img class="logo pull-left" src="http://localhost/wp/wp-content/themes/EDUCATION/images/EA_logo-644x144.png" alt="Education Axis" />
  </div>
</header>
```

### 6.2.2 Footer

``` html
<footer class="main-footer">
    <div class="contract">
        <div class="container clearfix">
            <div class="col-md-9">

                <?php echo do_shortcode("[newsletter_form form='1']"); ?>

            </div>
            <div class="col-md-3">
                <h2>ADDRESS</h2>
                Lineage Media &amp; Solutions 788 11oth Ave. NE, N-2602 Bellevue, WA 98004 000-000-0000
            </div>
        </div>
    </div>
    <div class="copyright">EducationAxis: Freedom Turns On Education</div>
</footer>
```
