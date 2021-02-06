package com.myutils.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * 获得spring上下文对象。注意，此处获得的时不含controller的bean对象
 * @author lizp
 * @date May 31, 2016
 */
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext; // Spring应用上下文环境
    
    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     * 
     * @param applicationContext
     * @throws org.springframework.beans.BeansException
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 得到Spring环境中的上下文
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
    	if(applicationContext == null){
    		throw new RuntimeException("上下文对象为空！");
    	}
        return applicationContext;
    }

    /**
     * 获取Spring管理的实体对象
     *
     * @param name Spring中注册的对象名
     * @return T 一个以所给名字注册的bean的实例
     * @throws org.springframework.beans.BeansException
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) getApplicationContext().getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     *
     * @param clz 类
     * @return T 类对应的注册的bean的实例
     * @throws org.springframework.beans.BeansException
     */
    public static <T> T getBean(Class<T> clz) throws BeansException {
        @SuppressWarnings("unchecked")
        T result = (T) getApplicationContext().getBean(clz);
        return result;
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name bean的注册名
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return getApplicationContext().containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param name bean的注册名
     * @return boolean
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return getApplicationContext().isSingleton(name);
    }

    /**
     * 根据bean的注册名得到注册对象类型
     * @param name bean的注册名
     * @return Class 注册对象的类型
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return getApplicationContext().getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     *
     * @param name bean的注册名
     * @return
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return getApplicationContext().getAliases(name);
    }

    /**
     * 获得所有自定义标签bean。继承BaseTemplateDirectiveModel及在属性配置中配置名称为directive.开头的bean名称。
     * 继承BaseTemplateDirectiveModel的标签名为注解或者bean的bean名称，配置文件中的名称directive.后的部分。
     * @return
     */

}
