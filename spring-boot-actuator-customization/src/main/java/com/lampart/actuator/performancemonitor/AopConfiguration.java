package com.lampart.actuator.performancemonitor;

//@Configuration
//@EnableAspectJAutoProxy
public class AopConfiguration {

//    @Pointcut("execution(public String com.lampart.actuator.mvc.PersonService.getFullName(..))")
//    public void monitor() {
//    }
//
//    @Pointcut("execution(public int com.lampart.actuator.mvc.PersonService.getAge(..))")
//    public void myMonitor() {
//    }
//
//    @Bean
//    public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
//        return new PerformanceMonitorInterceptor(true);
//    }
//
//    @Bean
//    public Advisor performanceMonitorAdvisor() {
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression("com.lampart.actuator.performancemonitor.AopConfiguration.monitor()");
//        return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor());
//    }
}
