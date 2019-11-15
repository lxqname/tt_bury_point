package com.deepexi.bury.point.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @version V1.0
 * @author: LuoGuang
 * @Package com.deepexi.customer.analysis.local.config
 * @Description:
 * @date: 2019/8/26 13:49
 */
@ConfigurationProperties(prefix = "tt.traffic-statistics.client")
public class ServerProperties {
    /**
     * 推送的服务器地址，多个地址用逗号隔开
     */
    String servers = "localhost";
    /**
     * 失败重试次数
     */
    int retries = 2;
    /**
     * 批大小
     */
    int batchSize = 4096;
    int linger = 1;
    /**
     * 缓冲区内存大小
     */
    int bufferMemory = 40960;
    /**
     * acks次数
     */
    String acks = "-1";
    /**
     * 线程池保留线程数，即空闲状态最少保留的线程数量
     */
    int corePoolSize = 3;
    /**
     * 线程池最大数量，当任务多于该数量时会导致任务积压
     */
    int maxPoolSize = 10;
    /**
     * 任务缓冲区大小，若任务消耗速度小于产生产生速度，会导致积压，积压任务大于该数量时，会导致服务崩溃
     */
    int maxQueueSize = 4096;
    /**
     * dns服务配置
     */
    Map<String, String> hosts;

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getLinger() {
        return linger;
    }

    public void setLinger(int linger) {
        this.linger = linger;
    }

    public int getBufferMemory() {
        return bufferMemory;
    }

    public void setBufferMemory(int bufferMemory) {
        this.bufferMemory = bufferMemory;
    }

    public String getAcks() {
        return acks;
    }

    public void setAcks(String acks) {
        this.acks = acks;
    }

    public Map<String, String> getHosts() {
        return hosts;
    }

    public void setHosts(Map<String, String> hosts) {
        this.hosts = hosts;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getMaxQueueSize() {
        return maxQueueSize;
    }

    public void setMaxQueueSize(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }
}
