package cn.xiaozhuoge.locallocal;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;

/**
 * @author zhaowz
 * @date 2023/6/6 10:46
 */
@Data
public class Student implements InitializingBean, ApplicationContextAware {
    
    private Integer id;
    
    private String name;
    
    private int age;
    
    public Student(Integer id){
        this.id = id;
    }
    
    public int hashCode(){
        return id;
    }
    
    public boolean equals(Object obj){
        return true;
    }

    @PostConstruct
    private void postConstruct(){
        System.out.printf("PostConstruct");
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }
    
    public void init(){
        System.out.println("init");
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.printf("setApplicationContext");
    }
}

