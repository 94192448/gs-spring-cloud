package com.example;

import com.google.common.base.Optional;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yangzq80@gmail.com
 * @date 2020-06-09
 */
public class VersionZoneAvoidanceRule extends ZoneAvoidanceRule {

    @Override
    public Server choose(Object key) {

        ILoadBalancer lb = getLoadBalancer();

        List<Server> eligible = getEligibleServers(lb.getAllServers());

        Optional<Server> server = getPredicate().chooseRoundRobinAfterFiltering(eligible, key);

        if (server.isPresent()) {
            return server.get();
        } else {
            return null;
        }
    }

    /**
     * Get servers filtered by this predicate from list of servers.
     */
    public List<Server> getEligibleServers(List<Server> servers){

        int higherVersion = 0;
        boolean onlyOneVersion = true;

        //比较出高版本
        for(Server server : servers) {
            int version = extractNumberVersion(server);

            if (higherVersion==0){
                higherVersion = version;
            }

            if (version>higherVersion){
                higherVersion = version;
            }

            if (version!=higherVersion){
                onlyOneVersion = false;
            }
        }

        //如果多个版本并存，那么选择高版本server
        if (onlyOneVersion){
            return servers;
        }else {
            List<Server> list = new ArrayList<Server>();
            for(Server server : servers) {

                int version = extractNumberVersion(server);

                if (version==higherVersion){
                    list.add(server);
                }
            }
            return list;
        }
    }

    //通过注册中心读取服务元数据信息中版本号
    private static int extractNumberVersion(Server server){

        Map<String, String> metadata = ((DiscoveryEnabledServer) server).getInstanceInfo().getMetadata();

        String version = metadata.get("version");

        if (!StringUtils.isEmpty(version)){
            Matcher m = Pattern.compile("[^0-9]").matcher(version);
            return Integer.valueOf(m.replaceAll("").trim());
        }
        return 0;
    }
}
