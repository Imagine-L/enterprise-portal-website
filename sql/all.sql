-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: ms_db
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `ms_db`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `ms_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `ms_db`;

--
-- Table structure for table `ms_article`
--

DROP TABLE IF EXISTS `ms_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ms_article` (
  `aid` bigint NOT NULL COMMENT '文章编号',
  `title` varchar(20) DEFAULT NULL COMMENT '文章标题',
  `summary` varchar(300) DEFAULT NULL COMMENT '文章摘要',
  `html_content` text COMMENT '文章html格式正文',
  `nav_id` bigint NOT NULL COMMENT '文章发布的栏目',
  `order_seed` tinyint NOT NULL DEFAULT '0' COMMENT '排序种子',
  `released` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否发布',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `disabled` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否禁用',
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ms_article`
--

LOCK TABLES `ms_article` WRITE;
/*!40000 ALTER TABLE `ms_article` DISABLE KEYS */;
INSERT INTO `ms_article` VALUES (1592815210829254658,'法沙发沙发是','安分守法梵蒂冈双方的共同的山峰根据回复大家赶快回家开发规划尽快寄给客户格格不入的施工','<p>安分守法梵蒂冈双方的共同的山峰根据回复大家赶快回家开发规划尽快寄给客户格格不入的施工</p>',1,0,_binary '','2022-11-16 17:41:54',1,'2022-11-16 17:41:54',1,_binary '\0');
/*!40000 ALTER TABLE `ms_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ms_home_chart`
--

DROP TABLE IF EXISTS `ms_home_chart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ms_home_chart` (
  `hid` bigint NOT NULL COMMENT '轮播图编号',
  `image` varchar(200) NOT NULL COMMENT '轮播图片',
  `link` varchar(300) DEFAULT NULL COMMENT '轮播图链接',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`hid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='栏目表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ms_home_chart`
--

LOCK TABLES `ms_home_chart` WRITE;
/*!40000 ALTER TABLE `ms_home_chart` DISABLE KEYS */;
INSERT INTO `ms_home_chart` VALUES (1591712178829893634,'http://localhost:5050/static/2022-11-13/c792c7bf7d014497867ee38e8bc17d58.jpg','https://www.bilibili.com/',1,'2022-11-13 16:38:51',1,'2022-11-13 16:38:50');
/*!40000 ALTER TABLE `ms_home_chart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ms_log`
--

DROP TABLE IF EXISTS `ms_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ms_log` (
  `lid` bigint NOT NULL COMMENT '日志编号',
  `module` varchar(20) NOT NULL COMMENT '操作的模块',
  `request_mode` varchar(6) DEFAULT NULL COMMENT '请求方式',
  `request_addr` varchar(100) DEFAULT NULL COMMENT '请求地址',
  `request_json` text COMMENT '请求json参数',
  `request_user` bigint DEFAULT NULL COMMENT '请求用户编号',
  `handler_method` varchar(100) DEFAULT NULL COMMENT '处理的方法',
  `success` bit(1) DEFAULT b'1' COMMENT '请求是否成功',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `runtime` int DEFAULT '-1' COMMENT '程序执行时间(ms)',
  `error_class` varchar(100) DEFAULT NULL COMMENT '异常类',
  `error_message` varchar(200) DEFAULT NULL COMMENT '异常信息',
  `oper_type` varchar(20) DEFAULT NULL COMMENT '操作类型',
  PRIMARY KEY (`lid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ms_log`
--

LOCK TABLES `ms_log` WRITE;
/*!40000 ALTER TABLE `ms_log` DISABLE KEYS */;
INSERT INTO `ms_log` VALUES (1591708433027014657,'用户管理','PUT','/user/pwd','{\"uid\":1,\"oldPwd\":\"e10adc3949ba59abbe56e057f20f883e\",\"newPwd\":\"5bd5e23d71e03167f0cd71e5dc853ec2\"}',1,'top.liubaiblog.masterstudio.api.controller.UserController.updatePwd()',_binary '','2022-11-13 16:23:58',247,NULL,NULL,'修改密码'),(1591708666611998722,'用户管理','PUT','/user','{\"uid\":1,\"nickname\":\"工作室管理员\",\"gender\":\"0\",\"age\":20,\"email\":\"1332674941@qq.com\",\"phone\":\"19912345678\",\"qqNumber\":\"12345678\",\"description\":\"这是系统管理员的账号。\",\"professionId\":1,\"locked\":false}',1,'top.liubaiblog.masterstudio.api.controller.UserController.update()',_binary '','2022-11-13 16:24:53',12,NULL,NULL,'修改用户'),(1591709165864198146,'用户管理','POST','/user','{\"username\":\"nankaiwen\",\"nickname\":\"南凯文\",\"password\":\"7bbfeeb1460791826199b5ee07185daf\",\"gender\":\"0\",\"age\":20,\"email\":\"1726989969@qq.com\",\"phone\":\"18337503791\",\"qqNumber\":\"1726989969\",\"description\":\"\",\"professionId\":1,\"locked\":false}',1,'top.liubaiblog.masterstudio.api.controller.UserController.save()',_binary '','2022-11-13 16:26:52',132,NULL,NULL,'保存用户'),(1591709790513504258,'岗位管理','POST','/profession','{\"name\":\"宣传成员\",\"description\":\"工作室宣传成员\"}',1,'top.liubaiblog.masterstudio.api.controller.ProfessionController.save()',_binary '','2022-11-13 16:29:21',31,NULL,NULL,'保存岗位'),(1591709898281951234,'栏目管理','PUT','/nav','{\"nid\":1,\"navName\":\"通知公告\",\"level\":1,\"parentId\":null,\"image\":null,\"description\":\"我们会在这里发布工作室的日常通知，欢迎大家关注这个频道，工作室的通知消息会第一时间在这个频道中发布。\",\"disabled\":false,\"showed\":false,\"navType\":2,\"path\":\"notice\",\"orderSeed\":10}',1,'top.liubaiblog.masterstudio.api.controller.NavigationController.update()',_binary '','2022-11-13 16:29:47',23,NULL,NULL,'修改栏目'),(1591711701119639554,'栏目管理','DELETE','/nav/1591711614050082817','1591711614050082817',1,'top.liubaiblog.masterstudio.api.controller.NavigationController.delete()',_binary '','2022-11-13 16:36:57',17,NULL,NULL,'删除栏目'),(1591712047057444865,'轮播图管理','DELETE','/chart/2','2',1,'top.liubaiblog.masterstudio.api.controller.HomeChartController.delete()',_binary '','2022-11-13 16:38:19',22,NULL,NULL,'删除轮播图'),(1591712054766575617,'轮播图管理','DELETE','/chart/3','3',1,'top.liubaiblog.masterstudio.api.controller.HomeChartController.delete()',_binary '','2022-11-13 16:38:21',14,NULL,NULL,'删除轮播图'),(1591712061213220865,'轮播图管理','DELETE','/chart/4','4',1,'top.liubaiblog.masterstudio.api.controller.HomeChartController.delete()',_binary '','2022-11-13 16:38:23',14,NULL,NULL,'删除轮播图'),(1591712067487899649,'轮播图管理','DELETE','/chart/5','5',1,'top.liubaiblog.masterstudio.api.controller.HomeChartController.delete()',_binary '','2022-11-13 16:38:24',15,NULL,NULL,'删除轮播图'),(1591712074446249986,'轮播图管理','DELETE','/chart/1586367017061044226','1586367017061044226',1,'top.liubaiblog.masterstudio.api.controller.HomeChartController.delete()',_binary '','2022-11-13 16:38:26',15,NULL,NULL,'删除轮播图'),(1591712080775454722,'轮播图管理','DELETE','/chart/1589961115462369282','1589961115462369282',1,'top.liubaiblog.masterstudio.api.controller.HomeChartController.delete()',_binary '','2022-11-13 16:38:27',5,NULL,NULL,'删除轮播图'),(1592139792715452417,'文章发布','POST','/article','{\"title\":\"石家庄地铁公交不再查验核酸有效期\",\"summary\":\"今日（11月14日）晚间，第一财经记者致电石家庄12345，确认石家庄自今日起取消常态化核酸点。与\",\"htmlContent\":\"<p><br></p><p>今日（11月14日）晚间，第一财经记者致电石家庄12345，确认石家庄自今日起取消常态化核酸点。与取消常态化核酸点配套的措施是，地铁和公交方面自15日起不再查验核酸有效期，除重点场所外不再查验72小时核酸证明。</p><p><br></p><p>另据第一财经记者今日在石家庄的实地体验，风险区划分已经精确到楼栋单元，记者所在的低风险小区进入小区时，不再被要求查验核酸有效期，小区外路边的常态化检测点已经陆续关闭，路人如果有做核酸检测的需求，可以前往医院进行检测。目前，记者咨询的多家石家庄医院的核酸检测价格为单人单检16元，混检3.4元。【相关阅读：石家庄某医院取消免费核酸检测？是真的！而且只提供单人单采】【相关阅读：“20条”出台后，多地取消区域全员核酸检测】</p><p><br></p><p>11月14日上午，市委书记张超超赴河北师范大学检查疫情防控工作，强调优化和调整疫情防控举措，决不是“躺平”、放任不管，也决不是所谓的“全面放开”，是为了进一步提升防控工作的科学性、精准性；要全面对标对表中央进一步优化防控工作的二十条措施，凡是不一致的全面进行整改，不折不扣地落实到位。</p><p><img src=\\\"https://pics3.baidu.com/feed/472309f7905298224ef158b6bbb939c00b46d407.jpeg@f_auto?token=5963a5b9738ede28d88d5b98951d413e\\\" alt=\\\"\\\" data-href=\\\"\\\" style=\\\"width: 656px;\\\"/></p><p><br></p><p>取消常态化核酸检测</p><p><br></p><p>今日，第一财经记者发现，自己所在小区附近的常态化核酸亭都关闭了。在致电12345热线咨询相关情况时，工作人员表示，根据国务院联防联控机制《关于进一步优化新冠肺炎疫情防控措施，科学精准做好防控工作的通知》的精神，没有发生疫情的地区严格按照第九版防控方案确定的范围对风险岗位、重点人员开展核酸检测，不得扩大核酸检测范围。一般不按行政区域开展全员核酸检测，只在感染来源和传播链条不清、社区传播时间较长等疫情底数不清时开展。因此，石家庄市对于没有问题的地方不再进行大范围检测。</p><p><br></p><p>根据第一财经记者在石家庄街头的实地体验，路边的常态化检测点已陆续关闭，路人如果有做核酸检测的需求，可以前往医院进行检测。目前，记者咨询的石家庄多家医院的核酸检测价格为单人单检16元，混检3.4元。</p><p><br></p><p>记者在石家庄中医院看到，排队做核酸的队伍很长，采集点明确标示着单采和混采的价格。而河北省人民医院一位工作人员在中午1点多时接受第一财经采访时也表示，目前医院可以做核酸检测，排队的人很多。</p><p><img src=\\\"https://pics1.baidu.com/feed/f31fbe096b63f62433ee32a2e837a9f31b4ca35a.jpeg@f_auto?token=6a778aaaa1e81b99cdbdf8249afa8f0f\\\" alt=\\\"\\\" data-href=\\\"\\\" style=\\\"width: 656px;\\\"/></p><p><br></p><p>除重点场所外不再查验72小时核酸证明</p><p><br></p><p>12345的工作人员表示，今日从疫情防控办得到答复，除了对进入重点场所（如宾馆、酒店、旅游景区等）的人员要加强核酸阴性证明的查验，需要查验72小时核酸阴性证明和健康码绿码，其他地点没有相关通知。</p><p><br></p><p>多名读者向第一财经记者反映，目前小区的风险管控已经精确到了楼单元，没有阳性病例的单元居民可自由出入小区。长安区热线工作人员也表示，目前居民进出小区不再需要查验核酸有效期。</p><p><br></p><p>根据第一财经记者今日在石家庄的实地体验，位于石家庄市中心的勒泰商城，进入不需要查看72小时核酸，只需要健康码绿码便可进入。位于市中心的大型超市，进入时同样不需要查看72小时核酸，需要测温并持有绿码。</p><p><br></p><p>在市中心的北国商城地铁站，今日下午仍需查验72小时核酸码方可通行，地铁站广播同时播放，乘客进入车厢请扫场所码。不过记者在乘坐出租车时，已不再查验核酸，只看绿码。而就在昨天，乘坐出租车时还是需要查验核酸有效期。</p><p><img src=\\\"https://pics2.baidu.com/feed/241f95cad1c8a786abeeca3a067a8b3671cf50b8.jpeg@f_auto?token=46c6468a75b0299d5b8a10decd50ef53\\\" alt=\\\"\\\" data-href=\\\"\\\" style=\\\"width: 656px;\\\"/></p><p><br></p><p>今天的石家庄街头，市中心街边大多数商铺已经开门，人流、车流量也比昨天多了不少，而就在前两天，大多数餐饮商店还不接受堂食，只能外卖或者外带。公交车上人流量也明显增多，前几日封控时，市区穿梭的公交很多都空车前行。</p><p><img src=\\\"https://pics4.baidu.com/feed/86d6277f9e2f0708bfb2c4578d57fa92a801f2b5.jpeg@f_auto?token=b9f7386d44febe8a6b7a108d11aea1c0\\\" alt=\\\"\\\" data-href=\\\"\\\" style=\\\"width: 656px;\\\"/></p><p><br></p><p>强调科学精准防控</p><p><br></p><p>11月12日，石家庄市委市政府公开发表《致全体市民的一封信》表示坚决落实“四早”要求，把防控责任压实到最小单元，对划定的高风险区，严格按照国家有关规定管理；除此之外，实行常态化疫情防控，全力维护正常生产生活秩序。始终用心用情用力加强人文关怀，加强老人、儿童、有基础性疾病患者等脆弱人群防护，充分满足市民看病、就医、取暖、上课等基本需求；畅通表达诉求渠道，建立直通热线，主动回应关切，及时释疑解惑，做到策略更精准、措施更有效、群众更满意、社会可承受，全力以赴确保人民群众生命安全和身体健康。信中还公开了各区的防疫热线，欢迎市民进行反映和监督。</p><p><br></p><p>11月13日，石家庄疫情防控总指挥部办公室再次发布通告，宣布根据对高校疫情形势发展变化的研判，为确保师生身体健康安全，对河北师范大学师生采取临时疏散措施，目前已全部完成。正在对学校场所进行全方位消毒消杀，确保校园整体干净卫生安全，从明天开始，将组织学校无风险人员陆续返回学校。通告还表示，“当前，我市疫情防控形势严峻复杂，局部存在散发点，但总体可控。”“请广大市民保持冷静、坚定信心，理性面对疫情，不恐慌、不信谣、不传谣，主动配合疫情防控工作。”</p><p><br></p><p>11月14日上午，市委书记张超超赴河北师范大学检查疫情防控工作，他强调要全面对标对表中央进一步优化防控工作的二十条措施，凡是不一致的全面进行整改，不折不扣地落实到位。要教育引导广大市民群众充分认识到，市委、市政府进一步优化和调整疫情防控举措，是贯彻落实中央进一步优化防控工作的二十条措施的具体行动，决不是“躺平”、放任不管，也决不是所谓的“全面放开”，是为了进一步提升防控工作的科学性、精准性；同时，要教育引导广大市民群众理性面对疫情，不恐慌、不信谣、不传谣，坚定信心，共克时艰，一起打赢这场疫情防控歼灭战。</p><p><br></p><p>据河北省卫健委网站消息，2022年11月13日0—24时，石家庄新增新冠确诊病例3例，新增无症状感染者541例。</p>\",\"navId\":1592139528570769409,\"orderSeed\":0,\"released\":true,\"disabled\":false}',1,'top.liubaiblog.masterstudio.api.controller.ArticleController.save()',_binary '','2022-11-14 20:58:02',17,NULL,NULL,'保存文章'),(1592139963532677121,'文章发布','POST','/article','{\"title\":\"Linux根目录下各文件夹的含义和用途\",\"summary\":\"1./bin\\n\\n主要存放系统的二进制命令（cd、ls、vi、kill），普通用户和 root 都可以\",\"htmlContent\":\"<p>1./bin</p><p><br></p><p>主要存放系统的二进制命令（cd、ls、vi、kill），普通用户和 root 都可以执行。</p><p><br></p><p>放在 /bin 下的命令在单用户模式下也可以执行。</p><p><br></p><p>2./boot</p><p><br></p><p>Linux内核及引导系统程序所需的目录。</p><p><br></p><p>3./dev</p><p><br></p><p>所有设备文件的目录（如声卡、磁盘、光驱）</p><p><br></p><p>4./etc</p><p><br></p><p>目录中包含所有系统管理和维护方面的配置文件，如host.conf、logrotate.conf、mke2fs.conf、resolv.conf、sysctl.conf、syslog.conf等；</p><p><br></p><p>在系统内所有采用”rpm”、”yum”安装的服务，配置文件默认也保存在”/etc”目录下。例如采用”yum”的方式安装了”php”，在”/etc”目录下，可以找到”php.ini”；用”yum”的方式安装了”mysql”，可以在”/etc”下找到”my.cnf”。</p><p><br></p><p>5./lib</p><p><br></p><p>目录下存放必要的运行库，主要是编程语言的库。典型的 Linux 操作系统中包含了C、C++和 Fortran 的库文件。用这些语言开发的应用程序可以使用这些编程语言库文件。这使软件开发者能够利用那些预先写好并通过测试的函数。库文件包含了标准的C库/lib/libc.so.*，数学库libm.so.*，共享的动态链接库/lib/ld/so以及目录/bin和/sbin下用到的其他共享库。/lib/modules目录存放系统的核心模块，某些可被模块化的部分并不需要在编译系统核心时放入核心本体，避免本体过于庞大而导致效率降低。</p><p><br></p><p>6./lost+found</p><p><br></p><p>该目录存放所有和其它目录都没有关联的文件。当系统意外崩溃或意外关机时，会产生一些碎片文件在这个目录下面，供系统进行修复。</p><p><br></p><p>7./media</p><p><br></p><p>挂载目录。系统建议用来挂载媒体设备，如软盘和光盘。</p><p><br></p><p>8./mnt</p><p><br></p><p>挂载目录。早期 Linux 中只有这一个挂载目录，并没有细分。系统建议这个目录用来挂载额外的设备，如 U 盘、移动硬盘和其他操作系统的分区。</p><p><br></p><p>9./opt</p><p><br></p><p>第三方安装的软件保存位置。这个目录是放置和安装其他软件的位置，手工安装的源码包软件都可以安装到这个目录中。</p><p><br></p><p>10./proc</p><p><br></p><p>进程文件系统proc的根目录，其中的部分文件分别对应正在运行的进程，可用于访问当前进程的地址空间。</p><p><br></p><p>它是一个非常特殊的虚拟文件系统，其中并不包含“实际的”文件，而是可用以引用当前运行系统的系统信息，如CPU、内存、运行时间、软件配置以及硬件配置的信息，这些信息是在内存中由系统自己产生的。</p><p><br></p><p>10.1 /proc/net：其中的文件分别表示各种网络协议（如TCP、UDP以及ARP等）的状态与统计信息。</p><p><br></p><p>10.2 /proc/sys：这个目录不仅存有各种系统信息，而且也包含系统内核与TCP/IP网络的可调参数。其中的kernel子目录含有共享内存和消息队列的可调参数，net子目录中含有TCP/IP的各种可调参数。</p><p><br></p><p>11./root</p><p><br></p><p>root 的主目录。普通用户主目录在 /home/ 下，root 主目录直接在“/”（根目录）下。</p><p><br></p><p>12./sbin</p><p><br></p><p>保存与系统环境设置相关的命令，只有 root 可以使用这些命令进行系统环境设置，但也有些命令可以允许普通用户查看。（大部分供系统管理员使用命令存放的目录）</p><p><br></p><p>13./srv</p><p><br></p><p>服务数据目录。一些系统服务启动之后，可以在这个目录中保存所需要的数据。</p><p><br></p><p>14./sys</p><p><br></p><p>虚拟文件系统。和 /proc/ 目录相似，该目录中的数据都保存在内存中，主要保存与内核相关的信息。</p><p><br></p><p>15./tmp</p><p><br></p><p>临时目录。系统存放临时文件的目录，在该目录下，所有用户都可以访问和写入。建议此目录中不能保存重要数据，最好每次开机都把该目录清空。</p><p><br></p><p>16./usr</p><p><br></p><p>在linux系统中，所有系统默认的软件都</p><p>————————————————</p><p>版权声明：本文为CSDN博主「像树一样活着」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。</p><p>原文链接：https://blog.csdn.net/qiuhuanghe/article/details/114497248</p>\",\"navId\":1592139528570769409,\"orderSeed\":0,\"released\":true,\"disabled\":false}',1,'top.liubaiblog.masterstudio.api.controller.ArticleController.save()',_binary '','2022-11-14 20:58:42',4,NULL,NULL,'保存文章'),(1592139999482056705,'文章发布','PUT','/article','{\"aid\":1592139963503316993,\"title\":\"Linux根目录下各文件夹的含义和用途\",\"summary\":\"1./bin\\n\\n主要存放系统的二进制命令（cd、ls、vi、kill），普通用户和 root 都可以\",\"htmlContent\":\"<p>1./bin</p><p><br></p><p>主要存放系统的二进制命令（cd、ls、vi、kill），普通用户和 root 都可以执行。</p><p><br></p><p>放在 /bin 下的命令在单用户模式下也可以执行。</p><p><br></p><p>2./boot</p><p><br></p><p>Linux内核及引导系统程序所需的目录。</p><p><br></p><p>3./dev</p><p><br></p><p>所有设备文件的目录（如声卡、磁盘、光驱）</p><p><br></p><p>4./etc</p><p><br></p><p>目录中包含所有系统管理和维护方面的配置文件，如host.conf、logrotate.conf、mke2fs.conf、resolv.conf、sysctl.conf、syslog.conf等；</p><p><br></p><p>在系统内所有采用”rpm”、”yum”安装的服务，配置文件默认也保存在”/etc”目录下。例如采用”yum”的方式安装了”php”，在”/etc”目录下，可以找到”php.ini”；用”yum”的方式安装了”mysql”，可以在”/etc”下找到”my.cnf”。</p><p><br></p><p>5./lib</p><p><br></p><p>目录下存放必要的运行库，主要是编程语言的库。典型的 Linux 操作系统中包含了C、C++和 Fortran 的库文件。用这些语言开发的应用程序可以使用这些编程语言库文件。这使软件开发者能够利用那些预先写好并通过测试的函数。库文件包含了标准的C库/lib/libc.so.*，数学库libm.so.*，共享的动态链接库/lib/ld/so以及目录/bin和/sbin下用到的其他共享库。/lib/modules目录存放系统的核心模块，某些可被模块化的部分并不需要在编译系统核心时放入核心本体，避免本体过于庞大而导致效率降低。</p><p><br></p><p>6./lost+found</p><p><br></p><p>该目录存放所有和其它目录都没有关联的文件。当系统意外崩溃或意外关机时，会产生一些碎片文件在这个目录下面，供系统进行修复。</p><p><br></p><p>7./media</p><p><br></p><p>挂载目录。系统建议用来挂载媒体设备，如软盘和光盘。</p><p><br></p><p>8./mnt</p><p><br></p><p>挂载目录。早期 Linux 中只有这一个挂载目录，并没有细分。系统建议这个目录用来挂载额外的设备，如 U 盘、移动硬盘和其他操作系统的分区。</p><p><br></p><p>9./opt</p><p><br></p><p>第三方安装的软件保存位置。这个目录是放置和安装其他软件的位置，手工安装的源码包软件都可以安装到这个目录中。</p><p><br></p><p>10./proc</p><p><br></p><p>进程文件系统proc的根目录，其中的部分文件分别对应正在运行的进程，可用于访问当前进程的地址空间。</p><p><br></p><p>它是一个非常特殊的虚拟文件系统，其中并不包含“实际的”文件，而是可用以引用当前运行系统的系统信息，如CPU、内存、运行时间、软件配置以及硬件配置的信息，这些信息是在内存中由系统自己产生的。</p><p><br></p><p>10.1 /proc/net：其中的文件分别表示各种网络协议（如TCP、UDP以及ARP等）的状态与统计信息。</p><p><br></p><p>10.2 /proc/sys：这个目录不仅存有各种系统信息，而且也包含系统内核与TCP/IP网络的可调参数。其中的kernel子目录含有共享内存和消息队列的可调参数，net子目录中含有TCP/IP的各种可调参数。</p><p><br></p><p>11./root</p><p><br></p><p>root 的主目录。普通用户主目录在 /home/ 下，root 主目录直接在“/”（根目录）下。</p><p><br></p><p>12./sbin</p><p><br></p><p>保存与系统环境设置相关的命令，只有 root 可以使用这些命令进行系统环境设置，但也有些命令可以允许普通用户查看。（大部分供系统管理员使用命令存放的目录）</p><p><br></p><p>13./srv</p><p><br></p><p>服务数据目录。一些系统服务启动之后，可以在这个目录中保存所需要的数据。</p><p><br></p><p>14./sys</p><p><br></p><p>虚拟文件系统。和 /proc/ 目录相似，该目录中的数据都保存在内存中，主要保存与内核相关的信息。</p><p><br></p><p>15./tmp</p><p><br></p><p>临时目录。系统存放临时文件的目录，在该目录下，所有用户都可以访问和写入。建议此目录中不能保存重要数据，最好每次开机都把该目录清空。</p><p><br></p><p>16./usr</p><p><br></p><p>在linux系统中，所有系统默认的软件都</p><p>————————————————</p><p>版权声明：本文为CSDN博主「像树一样活着」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。</p><p>原文链接：https://blog.csdn.net/qiuhuanghe/article/details/114497248</p>\",\"navId\":1592139629787713537,\"orderSeed\":0,\"released\":true,\"disabled\":false}',1,'top.liubaiblog.masterstudio.api.controller.ArticleController.update()',_binary '','2022-11-14 20:58:51',17,NULL,NULL,'修改文章'),(1592140034634518530,'栏目管理','PUT','/nav','{\"nid\":1592139385993793538,\"navName\":\"工作室概况\",\"level\":1,\"parentId\":null,\"image\":null,\"description\":\"\",\"disabled\":false,\"showed\":true,\"navType\":0,\"path\":\"outline\",\"orderSeed\":1}',1,'top.liubaiblog.masterstudio.api.controller.NavigationController.update()',_binary '','2022-11-14 20:58:59',17,NULL,NULL,'修改栏目'),(1592803162670034945,'文章发布','PUT','/article','{\"aid\":1592139963503316993,\"title\":\"Linux根目录下各文件夹的含义和用途\",\"summary\":\"1./bin\\n\\n主要存放系统的二进制命令（cd、ls、vi、kill），普通用户和 root 都可以\",\"htmlContent\":\"<p>1./bin</p><p><br></p><p>主要存放系统的二进制命令（cd、ls、vi、kill），普通用户和 root 都可以执行。</p><p><br></p><p>放在 /bin 下的命令在单用户模式下也可以执行。</p><p><br></p><p>2./boot</p><p><br></p><p>Linux内核及引导系统程序所需的目录。</p><p><br></p><p>3./dev</p><p><br></p><p>所有设备文件的目录（如声卡、磁盘、光驱）</p><p><br></p><p>4./etc</p><p><br></p><p>目录中包含所有系统管理和维护方面的配置文件，如host.conf、logrotate.conf、mke2fs.conf、resolv.conf、sysctl.conf、syslog.conf等；</p><p><br></p><p>在系统内所有采用”rpm”、”yum”安装的服务，配置文件默认也保存在”/etc”目录下。例如采用”yum”的方式安装了”php”，在”/etc”目录下，可以找到”php.ini”；用”yum”的方式安装了”mysql”，可以在”/etc”下找到”my.cnf”。</p><p><br></p><p>5./lib</p><p><br></p><p>目录下存放必要的运行库，主要是编程语言的库。典型的 Linux 操作系统中包含了C、C++和 Fortran 的库文件。用这些语言开发的应用程序可以使用这些编程语言库文件。这使软件开发者能够利用那些预先写好并通过测试的函数。库文件包含了标准的C库/lib/libc.so.*，数学库libm.so.*，共享的动态链接库/lib/ld/so以及目录/bin和/sbin下用到的其他共享库。/lib/modules目录存放系统的核心模块，某些可被模块化的部分并不需要在编译系统核心时放入核心本体，避免本体过于庞大而导致效率降低。</p><p><br></p><p>6./lost+found</p><p><br></p><p>该目录存放所有和其它目录都没有关联的文件。当系统意外崩溃或意外关机时，会产生一些碎片文件在这个目录下面，供系统进行修复。</p><p><br></p><p>7./media</p><p><br></p><p>挂载目录。系统建议用来挂载媒体设备，如软盘和光盘。</p><p><br></p><p>8./mnt</p><p><br></p><p>挂载目录。早期 Linux 中只有这一个挂载目录，并没有细分。系统建议这个目录用来挂载额外的设备，如 U 盘、移动硬盘和其他操作系统的分区。</p><p><br></p><p>9./opt</p><p><br></p><p>第三方安装的软件保存位置。这个目录是放置和安装其他软件的位置，手工安装的源码包软件都可以安装到这个目录中。</p><p><br></p><p>10./proc</p><p><br></p><p>进程文件系统proc的根目录，其中的部分文件分别对应正在运行的进程，可用于访问当前进程的地址空间。</p><p><br></p><p>它是一个非常特殊的虚拟文件系统，其中并不包含“实际的”文件，而是可用以引用当前运行系统的系统信息，如CPU、内存、运行时间、软件配置以及硬件配置的信息，这些信息是在内存中由系统自己产生的。</p><p><br></p><p>10.1 /proc/net：其中的文件分别表示各种网络协议（如TCP、UDP以及ARP等）的状态与统计信息。</p><p><br></p><p>10.2 /proc/sys：这个目录不仅存有各种系统信息，而且也包含系统内核与TCP/IP网络的可调参数。其中的kernel子目录含有共享内存和消息队列的可调参数，net子目录中含有TCP/IP的各种可调参数。</p><p><br></p><p>11./root</p><p><br></p><p>root 的主目录。普通用户主目录在 /home/ 下，root 主目录直接在“/”（根目录）下。</p><p><br></p><p>12./sbin</p><p><br></p><p>保存与系统环境设置相关的命令，只有 root 可以使用这些命令进行系统环境设置，但也有些命令可以允许普通用户查看。（大部分供系统管理员使用命令存放的目录）</p><p><br></p><p>13./srv</p><p><br></p><p>服务数据目录。一些系统服务启动之后，可以在这个目录中保存所需要的数据。</p><p><br></p><p>14./sys</p><p><br></p><p>虚拟文件系统。和 /proc/ 目录相似，该目录中的数据都保存在内存中，主要保存与内核相关的信息。</p><p><br></p><p>15./tmp</p><p><br></p><p>临时目录。系统存放临时文件的目录，在该目录下，所有用户都可以访问和写入。建议此目录中不能保存重要数据，最好每次开机都把该目录清空。</p><p><br></p><p>16./usr</p><p><br></p><p>在linux系统中，所有系统默认的软件都</p><p>————————————————</p><p>版权声明：本文为CSDN博主「像树一样活着」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。</p><p>原文链接：https://blog.csdn.net/qiuhuanghe/article/details/114497248</p>\",\"navId\":1592139629787713537,\"orderSeed\":0,\"released\":false,\"disabled\":false}',1,'top.liubaiblog.masterstudio.api.controller.ArticleController.update()',_binary '','2022-11-16 16:54:01',25,NULL,NULL,'修改文章'),(1592803189995925505,'文章发布','PUT','/article','{\"aid\":1592139792543485954,\"title\":\"石家庄地铁公交不再查验核酸有效期\",\"summary\":\"今日（11月14日）晚间，第一财经记者致电石家庄12345，确认石家庄自今日起取消常态化核酸点。与\",\"htmlContent\":\"<p><br></p><p>今日（11月14日）晚间，第一财经记者致电石家庄12345，确认石家庄自今日起取消常态化核酸点。与取消常态化核酸点配套的措施是，地铁和公交方面自15日起不再查验核酸有效期，除重点场所外不再查验72小时核酸证明。</p><p><br></p><p>另据第一财经记者今日在石家庄的实地体验，风险区划分已经精确到楼栋单元，记者所在的低风险小区进入小区时，不再被要求查验核酸有效期，小区外路边的常态化检测点已经陆续关闭，路人如果有做核酸检测的需求，可以前往医院进行检测。目前，记者咨询的多家石家庄医院的核酸检测价格为单人单检16元，混检3.4元。【相关阅读：石家庄某医院取消免费核酸检测？是真的！而且只提供单人单采】【相关阅读：“20条”出台后，多地取消区域全员核酸检测】</p><p><br></p><p>11月14日上午，市委书记张超超赴河北师范大学检查疫情防控工作，强调优化和调整疫情防控举措，决不是“躺平”、放任不管，也决不是所谓的“全面放开”，是为了进一步提升防控工作的科学性、精准性；要全面对标对表中央进一步优化防控工作的二十条措施，凡是不一致的全面进行整改，不折不扣地落实到位。</p><p><img src=\\\"https://pics3.baidu.com/feed/472309f7905298224ef158b6bbb939c00b46d407.jpeg@f_auto?token=5963a5b9738ede28d88d5b98951d413e\\\" alt=\\\"\\\" data-href=\\\"\\\" style=\\\"width: 656px;\\\"/></p><p><br></p><p>取消常态化核酸检测</p><p><br></p><p>今日，第一财经记者发现，自己所在小区附近的常态化核酸亭都关闭了。在致电12345热线咨询相关情况时，工作人员表示，根据国务院联防联控机制《关于进一步优化新冠肺炎疫情防控措施，科学精准做好防控工作的通知》的精神，没有发生疫情的地区严格按照第九版防控方案确定的范围对风险岗位、重点人员开展核酸检测，不得扩大核酸检测范围。一般不按行政区域开展全员核酸检测，只在感染来源和传播链条不清、社区传播时间较长等疫情底数不清时开展。因此，石家庄市对于没有问题的地方不再进行大范围检测。</p><p><br></p><p>根据第一财经记者在石家庄街头的实地体验，路边的常态化检测点已陆续关闭，路人如果有做核酸检测的需求，可以前往医院进行检测。目前，记者咨询的石家庄多家医院的核酸检测价格为单人单检16元，混检3.4元。</p><p><br></p><p>记者在石家庄中医院看到，排队做核酸的队伍很长，采集点明确标示着单采和混采的价格。而河北省人民医院一位工作人员在中午1点多时接受第一财经采访时也表示，目前医院可以做核酸检测，排队的人很多。</p><p><img src=\\\"https://pics1.baidu.com/feed/f31fbe096b63f62433ee32a2e837a9f31b4ca35a.jpeg@f_auto?token=6a778aaaa1e81b99cdbdf8249afa8f0f\\\" alt=\\\"\\\" data-href=\\\"\\\" style=\\\"width: 656px;\\\"/></p><p><br></p><p>除重点场所外不再查验72小时核酸证明</p><p><br></p><p>12345的工作人员表示，今日从疫情防控办得到答复，除了对进入重点场所（如宾馆、酒店、旅游景区等）的人员要加强核酸阴性证明的查验，需要查验72小时核酸阴性证明和健康码绿码，其他地点没有相关通知。</p><p><br></p><p>多名读者向第一财经记者反映，目前小区的风险管控已经精确到了楼单元，没有阳性病例的单元居民可自由出入小区。长安区热线工作人员也表示，目前居民进出小区不再需要查验核酸有效期。</p><p><br></p><p>根据第一财经记者今日在石家庄的实地体验，位于石家庄市中心的勒泰商城，进入不需要查看72小时核酸，只需要健康码绿码便可进入。位于市中心的大型超市，进入时同样不需要查看72小时核酸，需要测温并持有绿码。</p><p><br></p><p>在市中心的北国商城地铁站，今日下午仍需查验72小时核酸码方可通行，地铁站广播同时播放，乘客进入车厢请扫场所码。不过记者在乘坐出租车时，已不再查验核酸，只看绿码。而就在昨天，乘坐出租车时还是需要查验核酸有效期。</p><p><img src=\\\"https://pics2.baidu.com/feed/241f95cad1c8a786abeeca3a067a8b3671cf50b8.jpeg@f_auto?token=46c6468a75b0299d5b8a10decd50ef53\\\" alt=\\\"\\\" data-href=\\\"\\\" style=\\\"width: 656px;\\\"/></p><p><br></p><p>今天的石家庄街头，市中心街边大多数商铺已经开门，人流、车流量也比昨天多了不少，而就在前两天，大多数餐饮商店还不接受堂食，只能外卖或者外带。公交车上人流量也明显增多，前几日封控时，市区穿梭的公交很多都空车前行。</p><p><img src=\\\"https://pics4.baidu.com/feed/86d6277f9e2f0708bfb2c4578d57fa92a801f2b5.jpeg@f_auto?token=b9f7386d44febe8a6b7a108d11aea1c0\\\" alt=\\\"\\\" data-href=\\\"\\\" style=\\\"width: 656px;\\\"/></p><p><br></p><p>强调科学精准防控</p><p><br></p><p>11月12日，石家庄市委市政府公开发表《致全体市民的一封信》表示坚决落实“四早”要求，把防控责任压实到最小单元，对划定的高风险区，严格按照国家有关规定管理；除此之外，实行常态化疫情防控，全力维护正常生产生活秩序。始终用心用情用力加强人文关怀，加强老人、儿童、有基础性疾病患者等脆弱人群防护，充分满足市民看病、就医、取暖、上课等基本需求；畅通表达诉求渠道，建立直通热线，主动回应关切，及时释疑解惑，做到策略更精准、措施更有效、群众更满意、社会可承受，全力以赴确保人民群众生命安全和身体健康。信中还公开了各区的防疫热线，欢迎市民进行反映和监督。</p><p><br></p><p>11月13日，石家庄疫情防控总指挥部办公室再次发布通告，宣布根据对高校疫情形势发展变化的研判，为确保师生身体健康安全，对河北师范大学师生采取临时疏散措施，目前已全部完成。正在对学校场所进行全方位消毒消杀，确保校园整体干净卫生安全，从明天开始，将组织学校无风险人员陆续返回学校。通告还表示，“当前，我市疫情防控形势严峻复杂，局部存在散发点，但总体可控。”“请广大市民保持冷静、坚定信心，理性面对疫情，不恐慌、不信谣、不传谣，主动配合疫情防控工作。”</p><p><br></p><p>11月14日上午，市委书记张超超赴河北师范大学检查疫情防控工作，他强调要全面对标对表中央进一步优化防控工作的二十条措施，凡是不一致的全面进行整改，不折不扣地落实到位。要教育引导广大市民群众充分认识到，市委、市政府进一步优化和调整疫情防控举措，是贯彻落实中央进一步优化防控工作的二十条措施的具体行动，决不是“躺平”、放任不管，也决不是所谓的“全面放开”，是为了进一步提升防控工作的科学性、精准性；同时，要教育引导广大市民群众理性面对疫情，不恐慌、不信谣、不传谣，坚定信心，共克时艰，一起打赢这场疫情防控歼灭战。</p><p><br></p><p>据河北省卫健委网站消息，2022年11月13日0—24时，石家庄新增新冠确诊病例3例，新增无症状感染者541例。</p>\",\"navId\":1592139528570769409,\"orderSeed\":0,\"released\":false,\"disabled\":false}',1,'top.liubaiblog.masterstudio.api.controller.ArticleController.update()',_binary '','2022-11-16 16:54:08',4,NULL,NULL,'修改文章'),(1592803224263389186,'文章发布','DELETE','/article/1592139963503316993','1592139963503316993',1,'top.liubaiblog.masterstudio.api.controller.ArticleController.removeById()',_binary '','2022-11-16 16:54:16',4,NULL,NULL,'删除文章'),(1592803229946671106,'文章发布','DELETE','/article/1592139792543485954','1592139792543485954',1,'top.liubaiblog.masterstudio.api.controller.ArticleController.removeById()',_binary '','2022-11-16 16:54:18',4,NULL,NULL,'删除文章'),(1592803332866502658,'栏目管理','PUT','/nav','{\"nid\":1592139528570769409,\"navName\":\"工作室成员\",\"level\":2,\"parentId\":1592139385993793538,\"image\":null,\"description\":\"\",\"disabled\":false,\"showed\":false,\"navType\":1,\"path\":\"student\",\"orderSeed\":1}',1,'top.liubaiblog.masterstudio.api.controller.NavigationController.update()',_binary '','2022-11-16 16:54:42',9,NULL,NULL,'修改栏目'),(1592803372859191297,'栏目管理','PUT','/nav','{\"nid\":1592139629787713537,\"navName\":\"工作室领导\",\"level\":2,\"parentId\":1592139385993793538,\"image\":null,\"description\":\"\",\"disabled\":false,\"showed\":false,\"navType\":1,\"path\":\"leader\",\"orderSeed\":2}',1,'top.liubaiblog.masterstudio.api.controller.NavigationController.update()',_binary '','2022-11-16 16:54:52',7,NULL,NULL,'修改栏目'),(1592805628186730497,'栏目管理','PUT','/nav','{\"nid\":1592139528570769409,\"navName\":\"工作室成员\",\"level\":1,\"parentId\":null,\"image\":null,\"description\":\"\",\"disabled\":false,\"showed\":false,\"navType\":1,\"path\":\"student\",\"orderSeed\":1}',1,'top.liubaiblog.masterstudio.api.controller.NavigationController.update()',_binary '','2022-11-16 17:03:49',29,NULL,NULL,'修改栏目'),(1592805713100414978,'栏目管理','PUT','/nav','{\"nid\":1592139528570769409,\"navName\":\"工作室成员\",\"level\":1,\"parentId\":null,\"image\":null,\"description\":\"\",\"disabled\":false,\"showed\":false,\"navType\":2,\"path\":\"student\",\"orderSeed\":1}',1,'top.liubaiblog.masterstudio.api.controller.NavigationController.update()',_binary '\0','2022-11-16 17:04:10',6,'top.liubaiblog.masterstudio.common.exception.param.RequestParamsException','无法修改为栏目类型，因为该栏目下还绑定了其他内容','修改栏目'),(1592805789097009154,'栏目管理','PUT','/nav','{\"nid\":1592139528570769409,\"navName\":\"工作室成员\",\"level\":2,\"parentId\":1592139385993793538,\"image\":null,\"description\":\"\",\"disabled\":false,\"showed\":false,\"navType\":2,\"path\":\"student\",\"orderSeed\":1}',1,'top.liubaiblog.masterstudio.api.controller.NavigationController.update()',_binary '\0','2022-11-16 17:04:28',5,'top.liubaiblog.masterstudio.common.exception.param.RequestParamsException','无法修改为二级栏目，因为该栏目下还绑定了其他内容','修改栏目'),(1592805811347791873,'页面管理','DELETE','/plate/1592803727420485633','1592803727420485633',1,'top.liubaiblog.masterstudio.api.controller.PagePlateController.removeById()',_binary '','2022-11-16 17:04:33',5,NULL,NULL,'删除板块'),(1592805852259033090,'栏目管理','PUT','/nav','{\"nid\":1592139528570769409,\"navName\":\"工作室成员\",\"level\":2,\"parentId\":1592139385993793538,\"image\":null,\"description\":\"\",\"disabled\":false,\"showed\":false,\"navType\":2,\"path\":\"student\",\"orderSeed\":1}',1,'top.liubaiblog.masterstudio.api.controller.NavigationController.update()',_binary '','2022-11-16 17:04:43',7,NULL,NULL,'修改栏目'),(1592805874644033537,'栏目管理','PUT','/nav','{\"nid\":1592139629787713537,\"navName\":\"工作室领导\",\"level\":2,\"parentId\":1592139385993793538,\"image\":null,\"description\":\"\",\"disabled\":false,\"showed\":false,\"navType\":2,\"path\":\"leader\",\"orderSeed\":2}',1,'top.liubaiblog.masterstudio.api.controller.NavigationController.update()',_binary '','2022-11-16 17:04:48',7,NULL,NULL,'修改栏目'),(1592805923591561218,'文章发布','POST','/article','{\"title\":\"大大\",\"summary\":\"khjkhahdkahda\",\"htmlContent\":\"<p>khjkhahdkahda</p>\",\"navId\":1592139528570769409,\"orderSeed\":0,\"released\":true,\"disabled\":false}',1,'top.liubaiblog.masterstudio.api.controller.ArticleController.save()',_binary '','2022-11-16 17:05:00',13,NULL,NULL,'保存文章'),(1592805992969543681,'文章发布','POST','/article','{\"title\":\"就会反馈\",\"summary\":\"的法国公司的函数返回后广东省非根交肺结核法国和对返回说的风格设计的规范化二位他个人发表\",\"htmlContent\":\"<p>的法国公司的函数返回后广东省非根交肺结核法国和对返回说的风格设计的规范化二位他个人发表</p>\",\"navId\":1592139629787713537,\"orderSeed\":0,\"released\":true,\"disabled\":false}',1,'top.liubaiblog.masterstudio.api.controller.ArticleController.save()',_binary '','2022-11-16 17:05:16',3,NULL,NULL,'保存文章'),(1592806152181129218,'文章发布','PUT','/article','{\"aid\":1592805992944377857,\"title\":\"就会反馈\",\"summary\":\"的法国公司的函数返回后广东省非根交肺结核法国和对返回说的风格设计的规范化二位他个人发表\",\"htmlContent\":\"<p>的法国公司的函数返回后广东省非根交肺结核法国和对返回说的风格设计的规范化二位他个人发表</p>\",\"navId\":1592139629787713537,\"orderSeed\":0,\"released\":false,\"disabled\":false}',1,'top.liubaiblog.masterstudio.api.controller.ArticleController.update()',_binary '','2022-11-16 17:05:54',11,NULL,NULL,'修改文章'),(1592806166534037505,'文章发布','PUT','/article','{\"aid\":1592805923562201090,\"title\":\"大大\",\"summary\":\"khjkhahdkahda\",\"htmlContent\":\"<p>khjkhahdkahda</p>\",\"navId\":1592139528570769409,\"orderSeed\":0,\"released\":false,\"disabled\":false}',1,'top.liubaiblog.masterstudio.api.controller.ArticleController.update()',_binary '','2022-11-16 17:05:58',4,NULL,NULL,'修改文章'),(1592806261857984513,'栏目管理','PUT','/nav','{\"nid\":1592139528570769409,\"navName\":\"工作室成员\",\"level\":2,\"parentId\":1592139385993793538,\"image\":null,\"description\":\"\",\"disabled\":false,\"showed\":false,\"navType\":1,\"path\":\"student\",\"orderSeed\":1}',1,'top.liubaiblog.masterstudio.api.controller.NavigationController.update()',_binary '\0','2022-11-16 17:06:20',5,'top.liubaiblog.masterstudio.common.exception.param.RequestParamsException','无法修改为栏目类型，因为该栏目下还绑定了其他内容','修改栏目'),(1592806286105255938,'文章发布','DELETE','/article/1592805992944377857','1592805992944377857',1,'top.liubaiblog.masterstudio.api.controller.ArticleController.removeById()',_binary '','2022-11-16 17:06:26',3,NULL,NULL,'删除文章'),(1592806291985670146,'文章发布','DELETE','/article/1592805923562201090','1592805923562201090',1,'top.liubaiblog.masterstudio.api.controller.ArticleController.removeById()',_binary '','2022-11-16 17:06:28',4,NULL,NULL,'删除文章'),(1592806319353503745,'栏目管理','PUT','/nav','{\"nid\":1592139528570769409,\"navName\":\"工作室成员\",\"level\":2,\"parentId\":1592139385993793538,\"image\":null,\"description\":\"\",\"disabled\":false,\"showed\":false,\"navType\":1,\"path\":\"student\",\"orderSeed\":1}',1,'top.liubaiblog.masterstudio.api.controller.NavigationController.update()',_binary '','2022-11-16 17:06:34',5,NULL,NULL,'修改栏目'),(1592806353130233858,'栏目管理','PUT','/nav','{\"nid\":1592139629787713537,\"navName\":\"工作室领导\",\"level\":2,\"parentId\":1592139385993793538,\"image\":null,\"description\":\"\",\"disabled\":false,\"showed\":false,\"navType\":1,\"path\":\"leader\",\"orderSeed\":2}',1,'top.liubaiblog.masterstudio.api.controller.NavigationController.update()',_binary '','2022-11-16 17:06:42',6,NULL,NULL,'修改栏目'),(1592807935783731202,'栏目管理','PUT','/nav','{\"nid\":1592139528570769409,\"navName\":\"工作室成员\",\"level\":2,\"parentId\":1592139385993793538,\"image\":\"org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@36cbce69\",\"description\":\"\",\"disabled\":false,\"showed\":false,\"navType\":1,\"path\":\"student\",\"orderSeed\":1}',1,'top.liubaiblog.masterstudio.api.controller.NavigationController.update()',_binary '','2022-11-16 17:12:59',73,NULL,NULL,'修改栏目'),(1592810267632279554,'栏目管理','PUT','/nav','{\"nid\":1592139385993793538,\"navName\":\"工作室概况\",\"level\":1,\"parentId\":null,\"description\":\"\",\"disabled\":false,\"showed\":false,\"navType\":0,\"path\":\"outline\",\"orderSeed\":1}',1,'top.liubaiblog.masterstudio.api.controller.NavigationController.update()',_binary '','2022-11-16 17:22:15',39,NULL,NULL,'修改栏目'),(1592812669894733826,'栏目管理','PUT','/nav','{\"nid\":1592139385993793538,\"navName\":\"工作室概况\",\"level\":1,\"parentId\":null,\"description\":\"\",\"disabled\":false,\"showed\":false,\"navType\":0,\"path\":\"outline\",\"orderSeed\":1}',1,'top.liubaiblog.masterstudio.api.controller.NavigationController.update()',_binary '','2022-11-16 17:31:48',43,NULL,NULL,'修改栏目'),(1592815114498674690,'栏目管理','PUT','/nav','{\"nid\":1592139528570769409,\"navName\":\"工作室成员\",\"level\":2,\"parentId\":1592139385993793538,\"description\":\"\",\"disabled\":false,\"showed\":false,\"navType\":1,\"path\":\"student\",\"orderSeed\":1}',1,'top.liubaiblog.masterstudio.api.controller.NavigationController.update()',_binary '','2022-11-16 17:41:31',52,NULL,NULL,'修改栏目'),(1592815150489997313,'页面管理','PUT','/plate','{\"pid\":1592806435703496705,\"plateName\":\"大大大\",\"description\":\"gag大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大\",\"link\":\"dada\",\"plateType\":0,\"bindNav\":1592139528570769409,\"disabled\":false,\"released\":true,\"orderSeed\":1}',1,'top.liubaiblog.masterstudio.api.controller.PagePlateController.update()',_binary '','2022-11-16 17:41:40',29,NULL,NULL,'更新板块'),(1592815210858614786,'文章发布','POST','/article','{\"title\":\"法沙发沙发是\",\"summary\":\"安分守法梵蒂冈双方的共同的山峰根据回复大家赶快回家开发规划尽快寄给客户格格不入的施工\",\"htmlContent\":\"<p>安分守法梵蒂冈双方的共同的山峰根据回复大家赶快回家开发规划尽快寄给客户格格不入的施工</p>\",\"navId\":1,\"orderSeed\":0,\"released\":true,\"disabled\":false}',1,'top.liubaiblog.masterstudio.api.controller.ArticleController.save()',_binary '','2022-11-16 17:41:54',12,NULL,NULL,'保存文章'),(1594317855356194817,'忘记密码','PUT','/forget','{\"tempToken\":\"6ca535ea5ee44751975981edcaabb08b\",\"newPwd\":\"2637a5c30af69a7bad877fdb65fbd78b\"}',-1,'top.liubaiblog.masterstudio.api.controller.ForgetPasswordController.updatePwdByTempToken()',_binary '','2022-11-20 21:12:52',81,NULL,NULL,'修改密码');
/*!40000 ALTER TABLE `ms_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ms_navigation`
--

DROP TABLE IF EXISTS `ms_navigation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ms_navigation` (
  `nid` bigint NOT NULL COMMENT '栏目编号',
  `nav_name` varchar(10) NOT NULL COMMENT '栏目名',
  `level` tinyint NOT NULL DEFAULT '1' COMMENT '栏目级别',
  `parent_id` bigint DEFAULT NULL COMMENT '父栏目编号',
  `image` varchar(200) DEFAULT NULL COMMENT '栏目图片路径',
  `description` text COMMENT '栏目描述',
  `allow_del` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否允许删除',
  `nav_type` tinyint NOT NULL COMMENT '栏目类型(0父栏目, 1页面, 2文章)',
  `path` varchar(10) NOT NULL COMMENT '访问路径',
  `order_seed` tinyint NOT NULL DEFAULT '0' COMMENT '排序种子',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `used` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否可用',
  `disabled` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否禁用',
  `showed` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否首页展示',
  PRIMARY KEY (`nid`),
  UNIQUE KEY `ms_navigation_path_uindex` (`path`),
  UNIQUE KEY `ms_navigation_nav_name_uindex` (`nav_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='栏目表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ms_navigation`
--

LOCK TABLES `ms_navigation` WRITE;
/*!40000 ALTER TABLE `ms_navigation` DISABLE KEYS */;
INSERT INTO `ms_navigation` VALUES (1,'通知公告',1,NULL,'http://localhost:5050/static/2022-11-10/e6148def27a241c1a9e6ebb5d3fc9f22.jpg','我们会在这里发布工作室的日常通知，欢迎大家关注这个频道，工作室的通知消息会第一时间在这个频道中发布。',_binary '\0',2,'notice',10,NULL,'2022-10-03 15:45:54',1,'2022-11-13 16:29:47',_binary '',_binary '\0',_binary '\0'),(1592139385993793538,'工作室概况',1,NULL,'http://localhost:5050/static/2022-11-16/5361d2eb8ead447c97c43d7f1da3155d.png','',_binary '',0,'outline',1,1,'2022-11-14 20:56:25',1,'2022-11-16 17:33:02',_binary '',_binary '\0',_binary '\0'),(1592139528570769409,'工作室成员',2,1592139385993793538,'http://localhost:5050/static/2022-11-16/d9fe11fa8f584c3a83c61941854699dd.png','',_binary '',1,'student',1,1,'2022-11-14 20:56:59',1,'2022-11-16 17:41:31',_binary '',_binary '\0',_binary '\0'),(1592139629787713537,'工作室领导',2,1592139385993793538,'http://localhost:5050/static/2022-11-16/f3b55a9de5cc4362bc4e3f2687cf4a23.png','',_binary '',1,'leader',2,1,'2022-11-14 20:57:23',1,'2022-11-16 17:30:49',_binary '\0',_binary '\0',_binary '\0');
/*!40000 ALTER TABLE `ms_navigation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ms_page_plate`
--

DROP TABLE IF EXISTS `ms_page_plate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ms_page_plate` (
  `pid` bigint NOT NULL COMMENT '板块编号',
  `plate_name` varchar(20) DEFAULT NULL COMMENT '板块标题',
  `description` text COMMENT '板块描述',
  `image` varchar(200) DEFAULT NULL COMMENT '板块图片',
  `link` varchar(300) DEFAULT NULL COMMENT '板块链接',
  `plate_type` tinyint NOT NULL DEFAULT '1' COMMENT '板块类型(0左对齐, 1居中, 2右对齐)',
  `bind_nav` bigint NOT NULL COMMENT '绑定的栏目编号',
  `disabled` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否禁用',
  `released` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否发布',
  `order_seed` tinyint NOT NULL DEFAULT '0' COMMENT '排序种子',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='页面板块表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ms_page_plate`
--

LOCK TABLES `ms_page_plate` WRITE;
/*!40000 ALTER TABLE `ms_page_plate` DISABLE KEYS */;
INSERT INTO `ms_page_plate` VALUES (1592806435703496705,'大大大','gag大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大','http://localhost:5050/static/2022-11-16/ea3b1614ea9345b2a06e8744038066cb.png','dada',0,1592139528570769409,_binary '\0',_binary '',1,1,'2022-11-16 17:07:02',1,'2022-11-16 17:41:40');
/*!40000 ALTER TABLE `ms_page_plate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ms_profession`
--

DROP TABLE IF EXISTS `ms_profession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ms_profession` (
  `pid` bigint NOT NULL COMMENT '岗位编号',
  `name` varchar(20) NOT NULL COMMENT '岗位名称',
  `description` varchar(100) DEFAULT NULL COMMENT '岗位描述',
  `allow_del` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否允许删除',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`pid`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='岗位表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ms_profession`
--

LOCK TABLES `ms_profession` WRITE;
/*!40000 ALTER TABLE `ms_profession` DISABLE KEYS */;
INSERT INTO `ms_profession` VALUES (1,'工作室总负责人','负责工作室日常的管理',_binary '\0',NULL,'2022-10-18 22:12:16',NULL,'2022-10-18 22:12:16'),(2,'工作室成员','工作室成员11',_binary '',NULL,'2022-10-18 22:12:16',1,'2022-10-29 19:32:52'),(1591709790417035265,'宣传成员','工作室宣传成员',_binary '',1,'2022-11-13 16:29:21',1,'2022-11-13 16:29:21');
/*!40000 ALTER TABLE `ms_profession` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ms_upload_file`
--

DROP TABLE IF EXISTS `ms_upload_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ms_upload_file` (
  `fid` bigint NOT NULL COMMENT '文件编号',
  `filename` varchar(50) NOT NULL DEFAULT 'default' COMMENT '系统文件名',
  `origin_filename` varchar(50) DEFAULT '' COMMENT '原始文件名',
  `network_path` varchar(200) NOT NULL COMMENT '网络路径',
  `local_path` varchar(200) NOT NULL COMMENT '本地路径',
  `size` bigint NOT NULL DEFAULT '0' COMMENT '文件大小(byte)',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ms_upload_file`
--

LOCK TABLES `ms_upload_file` WRITE;
/*!40000 ALTER TABLE `ms_upload_file` DISABLE KEYS */;
INSERT INTO `ms_upload_file` VALUES (1591712178829893635,'c792c7bf7d014497867ee38e8bc17d58.jpg','2022xuexiershida.jpg','http://localhost:5050/static/2022-11-13/c792c7bf7d014497867ee38e8bc17d58.jpg','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-13\\c792c7bf7d014497867ee38e8bc17d58.jpg',2615555,1,'2022-11-13 16:38:51',1,'2022-11-13 16:38:51'),(1592139386048319489,'492c7919704c40b5991dd9be54b22fef.jpg','banner07.jpg','http://localhost:5050/static/2022-11-14/492c7919704c40b5991dd9be54b22fef.jpg','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-14\\492c7919704c40b5991dd9be54b22fef.jpg',643258,1,'2022-11-14 20:56:25',1,'2022-11-14 20:56:25'),(1592139528570769410,'c7633f3d650b408b88821133c4190cd5.jpg','xiangan10.jpg','http://localhost:5050/static/2022-11-14/c7633f3d650b408b88821133c4190cd5.jpg','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-14\\c7633f3d650b408b88821133c4190cd5.jpg',13202636,1,'2022-11-14 20:56:59',1,'2022-11-14 20:56:59'),(1592139629825462273,'8aad1de221df45b287b3810fe55bdec6.jpg','xiangan10.jpg','http://localhost:5050/static/2022-11-14/8aad1de221df45b287b3810fe55bdec6.jpg','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-14\\8aad1de221df45b287b3810fe55bdec6.jpg',13202636,1,'2022-11-14 20:57:23',1,'2022-11-14 20:57:23'),(1592803727420485634,'356b41acda1f4921a216537564766983.png','QQ浏览器截图20201212233444.png','http://localhost:5050/static/2022-11-16/356b41acda1f4921a216537564766983.png','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-16\\356b41acda1f4921a216537564766983.png',30643,1,'2022-11-16 16:56:16',1,'2022-11-16 16:56:16'),(1592806435716079618,'c5738674aeba43c6bc5f9dc08551d39d.png','QQ浏览器截图20201212233444.png','http://localhost:5050/static/2022-11-16/c5738674aeba43c6bc5f9dc08551d39d.png','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-16\\c5738674aeba43c6bc5f9dc08551d39d.png',30643,1,'2022-11-16 17:07:02',1,'2022-11-16 17:07:02'),(1592806519556022273,'c1341b3044a346bfb50781d5726eac8b.png','QQ浏览器截图20210102184710.png','http://localhost:5050/static/2022-11-16/c1341b3044a346bfb50781d5726eac8b.png','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-16\\c1341b3044a346bfb50781d5726eac8b.png',12603,1,'2022-11-16 17:07:22',1,'2022-11-16 17:07:22'),(1592806590527840257,'c82e66b08bdf4b92a6b37be75b3e4b4a.png','QQ浏览器截图20210102184710.png','http://localhost:5050/static/2022-11-16/c82e66b08bdf4b92a6b37be75b3e4b4a.png','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-16\\c82e66b08bdf4b92a6b37be75b3e4b4a.png',12603,1,'2022-11-16 17:07:39',1,'2022-11-16 17:07:39'),(1592806728986009602,'217fd05469964621b17fea43535db348.png','QQ浏览器截图20210102185040.png','http://localhost:5050/static/2022-11-16/217fd05469964621b17fea43535db348.png','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-16\\217fd05469964621b17fea43535db348.png',12604,1,'2022-11-16 17:08:12',1,'2022-11-16 17:08:12'),(1592807935729205249,'03dcc728972f4f69bd80038b636951d8.png','QQ浏览器截图20210102185040.png','http://localhost:5050/static/2022-11-16/03dcc728972f4f69bd80038b636951d8.png','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-16\\03dcc728972f4f69bd80038b636951d8.png',12604,1,'2022-11-16 17:12:59',1,'2022-11-16 17:12:59'),(1592808745376751617,'08ba97da74e1471093015dcece7c77c9.png','QQ浏览器截图20210103183018.png','http://localhost:5050/static/2022-11-16/08ba97da74e1471093015dcece7c77c9.png','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-16\\08ba97da74e1471093015dcece7c77c9.png',29057,1,'2022-11-16 17:16:12',1,'2022-11-16 17:16:12'),(1592809265147539458,'b044319f57d743a5b12a3db0b1e013c8.png','QQ浏览器截图20210104202202.png','http://localhost:5050/static/2022-11-16/b044319f57d743a5b12a3db0b1e013c8.png','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-16\\b044319f57d743a5b12a3db0b1e013c8.png',13190,1,'2022-11-16 17:18:16',1,'2022-11-16 17:18:16'),(1592809848717787138,'f519e7d7f19a4fea9282f2757b441262.png','QQ浏览器截图20210102184047.png','http://localhost:5050/static/2022-11-16/f519e7d7f19a4fea9282f2757b441262.png','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-16\\f519e7d7f19a4fea9282f2757b441262.png',25060,1,'2022-11-16 17:20:36',1,'2022-11-16 17:20:36'),(1592810267581947906,'7de37d66c9f240709d9224380c17e537.png','QQ浏览器截图20210102185040.png','http://localhost:5050/static/2022-11-16/7de37d66c9f240709d9224380c17e537.png','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-16\\7de37d66c9f240709d9224380c17e537.png',12604,1,'2022-11-16 17:22:15',1,'2022-11-16 17:22:15'),(1592810777722580993,'7126c889c0c5430f8ae4dbd62fce65ed.png','QQ浏览器截图20210104202202.png','http://localhost:5050/static/2022-11-16/7126c889c0c5430f8ae4dbd62fce65ed.png','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-16\\7126c889c0c5430f8ae4dbd62fce65ed.png',13190,1,'2022-11-16 17:24:17',1,'2022-11-16 17:24:17'),(1592811352082153473,'9f416753d6ad497ba067364a9d884e2c.png','QQ浏览器截图20210104202202.png','http://localhost:5050/static/2022-11-16/9f416753d6ad497ba067364a9d884e2c.png','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-16\\9f416753d6ad497ba067364a9d884e2c.png',13190,1,'2022-11-16 17:26:34',1,'2022-11-16 17:26:34'),(1592812421612916738,'f3b55a9de5cc4362bc4e3f2687cf4a23.png','QQ浏览器截图20210102184710.png','http://localhost:5050/static/2022-11-16/f3b55a9de5cc4362bc4e3f2687cf4a23.png','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-16\\f3b55a9de5cc4362bc4e3f2687cf4a23.png',12603,1,'2022-11-16 17:30:49',1,'2022-11-16 17:30:49'),(1592812669844402177,'79f2269d5bbf4e0fb91c0139a9999b1c.png','QQ浏览器截图20210102184710.png','http://localhost:5050/static/2022-11-16/79f2269d5bbf4e0fb91c0139a9999b1c.png','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-16\\79f2269d5bbf4e0fb91c0139a9999b1c.png',12603,1,'2022-11-16 17:31:48',1,'2022-11-16 17:31:48'),(1592812980311015425,'5361d2eb8ead447c97c43d7f1da3155d.png','QQ浏览器截图20210104202202.png','http://localhost:5050/static/2022-11-16/5361d2eb8ead447c97c43d7f1da3155d.png','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-16\\5361d2eb8ead447c97c43d7f1da3155d.png',13190,1,'2022-11-16 17:33:02',1,'2022-11-16 17:33:02'),(1592815114473508866,'d9fe11fa8f584c3a83c61941854699dd.png','QQ浏览器截图20210104202202.png','http://localhost:5050/static/2022-11-16/d9fe11fa8f584c3a83c61941854699dd.png','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-16\\d9fe11fa8f584c3a83c61941854699dd.png',13190,1,'2022-11-16 17:41:31',1,'2022-11-16 17:41:31'),(1592815150439665665,'ea3b1614ea9345b2a06e8744038066cb.png','QQ浏览器截图20210102184047.png','http://localhost:5050/static/2022-11-16/ea3b1614ea9345b2a06e8744038066cb.png','E:\\学习\\项目\\大师工作室官网\\master-file\\2022-11-16\\ea3b1614ea9345b2a06e8744038066cb.png',25060,1,'2022-11-16 17:41:40',1,'2022-11-16 17:41:40');
/*!40000 ALTER TABLE `ms_upload_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ms_user`
--

DROP TABLE IF EXISTS `ms_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ms_user` (
  `uid` bigint NOT NULL COMMENT '用户编号',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `nickname` varchar(30) DEFAULT NULL COMMENT '昵称',
  `password` char(60) NOT NULL COMMENT '密码',
  `gender` char(1) DEFAULT '0' COMMENT '性别(0男, 1女)',
  `age` tinyint DEFAULT NULL COMMENT '年龄',
  `phone` char(11) DEFAULT NULL COMMENT '手机号',
  `qq_number` varchar(15) DEFAULT NULL COMMENT 'qq号',
  `email` varchar(30) NOT NULL COMMENT '邮箱',
  `admin` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为管理员',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `profession_id` bigint NOT NULL COMMENT '用户岗位编号',
  `allow_del` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否允许删除',
  `locked` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否锁定',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `ms_user_email_uindex` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ms_user`
--

LOCK TABLES `ms_user` WRITE;
/*!40000 ALTER TABLE `ms_user` DISABLE KEYS */;
INSERT INTO `ms_user` VALUES (1,'admin','工作室管理员','$2a$10$jCmIBU7b3.rm6veMYSl9YOV0Y2LB1GqzCezg68h2TLpJMx5MmG9PG','0',20,'19912345678','12345678','1332674941@qq.com',_binary '','这是系统管理员的账号。',1,_binary '\0',_binary '\0',NULL,'2022-10-15 20:15:45',1,'2022-11-13 16:24:53'),(1591709165784506370,'nankaiwen','南凯文','$2a$10$ur8biqg6MLe0x46xbkMvRuVY5NlKlpyYegKHBQIzLtrm86rthQbTm','0',20,'18337503791','1726989969','1726989969@qq.com',_binary '\0','',1,_binary '',_binary '\0',1,'2022-11-13 16:26:52',1,'2022-11-13 16:26:52');
/*!40000 ALTER TABLE `ms_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-20 21:14:12
