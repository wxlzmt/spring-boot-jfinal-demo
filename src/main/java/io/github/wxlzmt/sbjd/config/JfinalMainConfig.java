package io.github.wxlzmt.sbjd.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.UrlSkipHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.template.Engine;

import io.github.wxlzmt.sbjd.controller.IndexController;

@Component
public class JfinalMainConfig extends JFinalConfig implements InitializingBean {

    private static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(JfinalMainConfig.class);

    @Autowired
    private Environment environment;

    private Boolean isDev = null;

    @Override
    public void configConstant(Constants me) {
        me.setDevMode(this.isDevMode());
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/", IndexController.class);
        // 自动绑定
        // 1.如果没用加入注解，必须以Controller结尾,自动截取前半部分为key
        // 2.加入ControllerBind的 获取 key
        me.add(new AutoBindRoutes());
    }

    @Override
    public void configEngine(Engine me) {
        me.setBaseTemplatePath("/pages");
        //为了解决spring-boot的jar运行模式找不到资源文件的问题
        me.setToClassPathSourceFactory();
    }

    @Override
    public void configPlugin(Plugins me) {
        if ("true".equalsIgnoreCase(environment.getProperty("jfinal.database.enable", "false"))) {// 这个if是为了展示下述示例代码切保证不运行它也不报错
            LOG.info("初始化数据库...");
            // 配置C3p0数据库连接池插件
            C3p0Plugin c3p0Plugin = null;
            //String dbType = "mysql";
            String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/test?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
            String userName = "root";
            String passWord = "123456";
            String driverClass = "com.mysql.jdbc.Driver";
            c3p0Plugin = new C3p0Plugin(jdbcUrl, userName, passWord, driverClass);
            me.add(c3p0Plugin);
            // 配置ActiveRecord插件
            ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
            me.add(arp);
            //动态扫描Model并绑定
            new AutoBindModels(arp);
        }
    }

    @Override
    public void configInterceptor(Interceptors me) {
        // TODO Auto-generated method stub
    }

    @Override
    public void configHandler(Handlers me) {
        //静态资源不经过jfinal
        me.add(new UrlSkipHandler("^/actuator(/.*)?|.*.js|.*.css|.*.json|.*.png|.*.gif|.*.jpg|.*.jpeg|.*.bmp|.*.ico|.*.exe|.*.txt|.*.zip|.*.rar|.*.7z$", false));
    }

    public boolean isDevMode() {
        return this.isDev;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("调用" + this.getClass().getName() + ".afterPropertiesSet()");
        if (isDev == null) {
            String act = environment.getProperty("spring.profiles.active", "dev");
            this.isDev = !"prod".equalsIgnoreCase(act);
            LOG.info("当前是否是devMode====>" + this.isDev);
        }
    }

}
