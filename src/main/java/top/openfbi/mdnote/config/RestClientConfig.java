package top.openfbi.mdnote.config;


import nl.altindag.ssl.SSLFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

/**
 * ElasticSearch 客户端配置
 *
 * @author geng
 * 2020/12/19
 */
@Configuration
public class RestClientConfig extends ElasticsearchConfiguration {

    @Value(value = "${Es.User}")
    private String User;
    @Value(value = "${Es.Password}")
    private String Password;
    @Value(value = "${Es.Url}")
    private String Url;
    @Value(value = "${Es.Post}")
    private String Post;

    /**
     * 设置elastic数据库的链接秘钥和账户
     */
    @Override
    public ClientConfiguration clientConfiguration() {
//        HttpHeaders compatibilityHeaders = new HttpHeaders();
//        compatibilityHeaders.add("Accept", "application/vnd.elasticsearch+json;compatible-with=7");
//        compatibilityHeaders.add("Content-Type", "application/vnd.elasticsearch+json;"
//                + "compatible-with=7");
        SSLFactory sslFactory = SSLFactory.builder()
                .withUnsafeTrustMaterial()
                .withUnsafeHostnameVerifier()
                .build();

        return ClientConfiguration.builder()
                .connectedTo(Url+':'+Post)
                .usingSsl(sslFactory.getSslContext(), sslFactory.getHostnameVerifier())
                .withBasicAuth(User,Password)
//                .withDefaultHeaders(compatibilityHeaders)    // this variant for imperative code
//                .withHeaders(() -> compatibilityHeaders)     // this variant for reactive code
                .build();
    }
}
