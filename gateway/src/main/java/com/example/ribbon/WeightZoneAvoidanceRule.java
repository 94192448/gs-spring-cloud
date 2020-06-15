package com.example.ribbon;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base on Weighted Round-Robin Scheduling
 * @author yangzq80@gmail.com
 * @date 2020-06-11
 */
public class WeightZoneAvoidanceRule extends ZoneAvoidanceRule {

    public static final String META_DATA_KEY_WEIGHT = "weight";

    /**上次选择的服务器*/
    private int currentIndex = -1;
    /**当前调度的权值*/
    private int currentWeight = 0;
    /**最大权重*/
    private int maxWeight;
    /**权重的最大公约数*/
    private int gcdWeight;
    /**服务器数*/
    private int serverCount;
    Map<Server, Integer> serverWeightMap;
    List<Server> serverList;

    @Override
    public Server choose(Object key) {

        List<Server> serverList = this.getPredicate().getEligibleServers(this.getLoadBalancer().getAllServers(), key);
        if (CollectionUtils.isEmpty(serverList)) {
            return null;
        }

        maxWeight = greatestWeightAndExcludeZero(serverList);
        if (serverList.isEmpty()){
            return null;
        }

        gcdWeight = greatestCommonDivisor(serverList);
        serverCount = serverList.size();

        /**
         * 算法流程：
         * 假设有一组服务器 S = {S0, S1, …, Sn-1}
         * 有相应的权重，变量currentIndex表示上次选择的服务器
         * 权值currentWeight初始化为0，currentIndex初始化为-1 ，当第一次的时候返回 权值取最大的那个服务器，
         * 通过权重的不断递减 寻找 适合的服务器返回，直到轮询结束，权值返回为0
         */
        while(true){
            currentIndex = (currentIndex + 1) % serverCount;
            if(currentIndex == 0){
                currentWeight = currentWeight - gcdWeight;
                if(currentWeight <= 0){
                    currentWeight = maxWeight;
                    if(currentWeight == 0){
                        return null;
                    }
                }
            }
            if(serverWeightMap.get(serverList.get(currentIndex)) >= currentWeight){
                //log.debug("Cost :{}/nanoseconds Server size:{} Weight round robin choose server-->{}",System.nanoTime()-t0,serverCount,serverList.get(currentIndex));
                return serverList.get(currentIndex);
            }
        }

    }


    public int greaterCommonDivisor(int a, int b){
        if (b==0){
            return a;
        }
        if(a % b == 0){
            return b;
        }else{
            return greaterCommonDivisor(b,a % b);
        }
    }

    /**
     * 所有权重的最大公约数
     * @param servers
     * @return
     */
    public int greatestCommonDivisor(List<Server> servers){
        int divisor = 0;
        for(int index = 0, len = servers.size(); index < len - 1; index++){
            if(index ==0){
                divisor = greaterCommonDivisor(
                        serverWeightMap.get(servers.get(index)), serverWeightMap.get(servers.get(index + 1)));
            }else{
                divisor = greaterCommonDivisor(divisor, serverWeightMap.get(servers.get(index)));
            }
        }
        return divisor;
    }

    public int greatestWeightAndExcludeZero(List<Server> servers){
        serverList  = new ArrayList<>();
        serverWeightMap = new HashMap<>();

        int weight = 0;
        for(Server server : servers){
            Map<String, String> metadata = ((DiscoveryEnabledServer) server).getInstanceInfo().getMetadata();

            String strWeight = metadata.get(META_DATA_KEY_WEIGHT);
            if (StringUtils.isEmpty(strWeight)){
                strWeight = "1";
            }

            int currentWeight = Integer.parseInt(strWeight);

            if (currentWeight == 0){
                continue;
            }

            serverWeightMap.put(server,currentWeight);
            serverList.add(server);

            if(weight < currentWeight){
                weight = currentWeight;
            }
        }
        return weight;
    }
}
