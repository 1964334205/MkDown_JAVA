package top.openfbi.mdnote;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication()
@MapperScan(basePackages = { "top.openfbi.mdnote.*.dao" })
public class MkDownJavaApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(MkDownJavaApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Bean
//    public EmbeddedServletContainerCustomizer containerCustomizer() {
//        return (container -> {
//            container.setSessionTimeout(1000);  // session timeout value
//            container.setSessionTimeout(1000); // 会话超时值
//        });
//    }
//}

}
