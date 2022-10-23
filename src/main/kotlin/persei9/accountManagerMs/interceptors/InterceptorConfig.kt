package persei9.accountManagerMs.interceptors

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
class InterceptorConfig: WebMvcConfigurerAdapter() {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authenticatorInterceptor()).addPathPatterns("/**")
    }

    @Bean
    fun authenticatorInterceptor(): ApplicationInterfaceInterceptor {
        return ApplicationInterfaceInterceptor()
    }
}