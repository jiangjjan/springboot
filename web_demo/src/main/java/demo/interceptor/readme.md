# 拦截器

对比过滤器,拦截器interceptor是针对dispatcher后 controller 前后执行,是属于spring mvc 的部件;  Filter属于servlet规范,执行在dispatcher之前,没有后续执行;  

使用:  
1.实现*HandlerInterceptor*接口  
2.WebMvcConfigurer内添加拦截器  
eg:  
实现拦截器 
```java
     public class InterceptorImp implements HandlerInterceptor{
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            log.info("preHandle");
            return true;
        }
    
        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            log.info("postHandle");
        }
    
        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            log.info("afterCompletion");
        }
    }
```
注册拦截器
```java
   @Configurable
   public class InterceptorConfig {
       @Bean
       public WebMvcConfigurer interceptor(HandlerInterceptor[] handlerInterceptors) {
           return new WebMvcConfigurer() {
               @Override
               public void addInterceptors(InterceptorRegistry registry) {
                   for (HandlerInterceptor t : handlerInterceptors) {
                       registry.addInterceptor(t);
                   }
               }
           };
       }
   }
```
此处选择将异常抛出,不作任何处理
