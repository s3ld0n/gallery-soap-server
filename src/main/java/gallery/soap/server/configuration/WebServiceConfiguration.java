package gallery.soap.server.configuration;

import gallery.soap.server.configuration.properties.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import service.soap.gallery.ObjectFactory;

@Configuration
@EnableWs
public class WebServiceConfiguration extends WsConfigurerAdapter {

    private Properties properties;

    @Autowired
    public WebServiceConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, properties.getUrlMapping());
    }

    @Bean(name = "works")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName(properties.getWsdl().getPortTypeName());
        wsdl11Definition.setLocationUri(properties.getWsdl().getLocationUri());
        wsdl11Definition.setTargetNamespace(properties.getWsdl().getTargetNamespace());
        wsdl11Definition.setSchema(schema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema coursesSchema() {
        return new SimpleXsdSchema(new ClassPathResource(properties.getSchemaPath()));
    }

    @Bean
    public ObjectFactory objectFactory() {
        return new ObjectFactory();
    }
}
