package joni.jmx;

import java.util.concurrent.atomic.AtomicLong;

import javax.management.Notification;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.util.Assert;

/**
 * @author Jonatan Ivanov
 */
@ManagedResource(objectName = "beans:name=testBean")
public class JmxTestBean implements InitializingBean, NotificationPublisherAware {
    private String name;
    private int age;
    private boolean isSuperman;
    private NotificationPublisher publisher;
    private AtomicLong sequenceNumber = new AtomicLong(0);

    @ManagedAttribute
    public String getName() {
        return name;
    }

    @ManagedOperation
    @ManagedOperationParameters({ @ManagedOperationParameter(name = "name", description = "Name") })
    public void setName(String name) {
        this.name = name;
    }

    @ManagedAttribute
    public int getAge() {
        return age;
    }

    @ManagedOperation
    @ManagedOperationParameters({ @ManagedOperationParameter(name = "age", description = "Age") })
    public void setAge(int age) {
        this.age = age;
    }

    @ManagedAttribute
    public boolean isSuperman() {
        return isSuperman;
    }

    @ManagedOperation
    @ManagedOperationParameters({ @ManagedOperationParameter(name = "isSuperman", description = "Super power") })
    public void setSuperman(boolean isSuperman) {
        this.isSuperman = isSuperman;
    }

    @ManagedOperation
    @ManagedOperationParameters({
        @ManagedOperationParameter(name = "x", description = "The first number"),
        @ManagedOperationParameter(name = "y", description = "The second number") })
    public int add(int x, int y) {
        this.publisher.sendNotification(new Notification("ADD", this,
                sequenceNumber.getAndIncrement(), System.currentTimeMillis(), "addition"));

        return x + y;
    }

    @ManagedOperation
    public void sayHello() {
        System.out.println("Hello World");
    }

    @ManagedOperation
    public void dontExposeMe() {
        throw new RuntimeException("yay!");
    }

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.isNull(publisher, "Publisher must not be null!");
    }

    /**
     * @see org.springframework.jmx.export.notification.NotificationPublisherAware#setNotificationPublisher(org.springframework.jmx.export.notification.NotificationPublisher)
     */
    @Override
    public void setNotificationPublisher(NotificationPublisher publisher) {
        this.publisher = publisher;
    }
}
