package persei9.accountManagerMs.interceptors

import persei9.accountManagerMs.annotations.ApiKey
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ApplicationInterfaceInterceptor: HandlerInterceptor {
    @Value("\${ms.x-api-key}")
    private val xApiKey: String = ""
    private val xApiKeyLabel: String = "x-api-key"

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        try {
            if (handler !is HandlerMethod) {
                return true
            }

            val method = handler.method

            if (method.isAnnotationPresent(ApiKey::class.java)) {
                val xApiKeyHeader = request.getHeader(xApiKeyLabel)

                if (xApiKeyHeader == null) {
                    response.status = 401

                    return false
                }

                if (xApiKeyHeader != xApiKey) {
                    response.status = 401

                    return false
                }

                return true
            }

            return true
        } catch (e: Exception) {
            response.status = 400

            return false
        }
    }

    @Throws(Exception::class)
    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {}

    @Throws(Exception::class)
    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: java.lang.Exception?) {}
}