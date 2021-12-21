package demo;


import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * tomcat 配置http转向https ;启动时http占用一个端口{@link TomcatConf#webPort},
 * https占用一个端口{@link TomcatConf#port}
 */
@Configuration
public class TomcatConf {

    @Value("${server.port}")
    int port;

    @Value("${web.port:80}")
    int webPort;

    public Connector httpConnector() {
        Connector con = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        con.setScheme("http");
        con.setSecure(false);
        con.setPort(webPort);
        con.setRedirectPort(port);
        return con;
    }

    @Bean
    public ServletWebServerFactory createFac() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {

            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }


}
