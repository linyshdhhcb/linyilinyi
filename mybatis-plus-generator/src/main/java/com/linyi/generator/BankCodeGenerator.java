package com.linyi.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;

/**
 * MyBatis-Plus 代码生成类
 */
public class BankCodeGenerator {

	// TODO 修改服务名以及数据表名
	private static final String SERVICE_NAME = "linyilinyi";
	private static final String DATA_SOURCE_USER_NAME  = "root";
	private static final String DATA_SOURCE_PASSWORD  = "123456";
	private static final String[] TABLE_NAMES = new String[]{
//			"article",
//			"article_data",
//			"collect",
//			"collect_group",
//			"comment",
//			"comment_notice",
//			"danmaku",
//			"play",
//			"privilege",
//			"user",
//			"user_home",
//			"video",
//			"video_data",
//			"likes",
//			"follow",
//			"file",
//			"dictionary_label",
//			"dictionary_type"
			"reviewer"
	};

	// TODO 默认生成entity，需要生成DTO修改此变量
	// 一般情况下要先生成 DTO类 然后修改此参数再生成 PO 类。
	private static final Boolean IS_DTO = false;

	/**
	 * 主函数入口
	 * 用于自动化生成代码
	 * @param args 命令行参数
	 */
	public static void main(String[] args) {
		// 代码生成器
		AutoGenerator mpg = new AutoGenerator();
		// 选择 freemarker 引擎，默认 Velocity
		mpg.setTemplateEngine(new FreemarkerTemplateEngine());
		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		// 设置是否覆盖文件
		gc.setFileOverride(true);
		// 设置输出目录
		gc.setOutputDir(System.getProperty("user.dir") + "/mybatis-plus-generator/src/main/java");
		// 设置作者名
		gc.setAuthor("linyi");
		// 设置打开输出目录
		gc.setOpen(false);
		// 设置是否生成Swagger文档
		gc.setSwagger2(true);
		// 设置service层文件名
		gc.setServiceName("%sService");
		// 设置是否生成基本ResultMap
		gc.setBaseResultMap(true);
		// 设置是否生成基本ColumnList
		gc.setBaseColumnList(true);

		// 根据条件判断生成不同的实体类（如需要DTO）
		if (IS_DTO) {
			gc.setSwagger2(true);
			gc.setEntityName("%sDTO");
		}
		// 将全局配置应用到代码生成器
		mpg.setGlobalConfig(gc);

		// 数据库配置
		DataSourceConfig dsc = new DataSourceConfig();
		// 设置数据库类型
		dsc.setDbType(DbType.MYSQL);
		// 设置数据库连接URL
		dsc.setUrl("jdbc:mysql://192.168.85.129:3306/" + SERVICE_NAME
				+ "?characterEncoding=utf-8&useSSL=false");
		// 设置数据库驱动名
		dsc.setDriverName("com.mysql.cj.jdbc.Driver");
		// 设置数据库用户名
		dsc.setUsername(DATA_SOURCE_USER_NAME);
		// 设置数据库密码
		dsc.setPassword(DATA_SOURCE_PASSWORD);
		// 将数据库配置应用到代码生成器
		mpg.setDataSource(dsc);

		// 包配置
		PackageConfig pc = new PackageConfig();
		// 设置父包名
		pc.setParent("com.linyilinyi");

		// 设置service实现类包名
		pc.setServiceImpl("service.impl");
		// 设置mapper XML文件包名
		pc.setXml("mapper");
		// 设置实体类包名
		pc.setEntity("entity.po");
		// 将包配置应用到代码生成器
		mpg.setPackageInfo(pc);


		// 设置模板
		TemplateConfig tc = new TemplateConfig();
		// 应用模板配置
		mpg.setTemplate(tc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		// 设置命名风格
		strategy.setNaming(NamingStrategy.underline_to_camel);
		// 设置列命名风格
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		// 设置是否使用Lombok模型
		strategy.setEntityLombokModel(true);
		// 设置是否使用Restful风格控制器
		strategy.setRestControllerStyle(true);
		// 设置需要生成的表名
		strategy.setInclude(TABLE_NAMES);
		// 设置控制器映射风格
		strategy.setControllerMappingHyphenStyle(true);
		// 设置表前缀
		strategy.setTablePrefix(pc.getModuleName() + "_");
		// 设置是否移除Boolean字段的is前缀
		strategy.setEntityBooleanColumnRemoveIsPrefix(true);
		// 设置是否使用Restful风格控制器
		strategy.setRestControllerStyle(true);
		strategy.setLogicDeleteFieldName("is_delete");

		// 自动填充字段配置
		strategy.setTableFillList(Arrays.asList(
				new TableFill("create_date", FieldFill.INSERT),
				new TableFill("change_date", FieldFill.INSERT_UPDATE),
				new TableFill("modify_date", FieldFill.UPDATE)
		));
		// 将策略配置应用到代码生成器
		mpg.setStrategy(strategy);

		// 执行代码生成
		mpg.execute();
	}


}
