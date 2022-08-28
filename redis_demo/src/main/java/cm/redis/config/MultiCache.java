package cm.redis.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class MultiCache implements CacheResolver {


    final CacheManager[] cacheManagers;

    public MultiCache(CacheManager ...cacheManagers){
        this.cacheManagers=cacheManagers;
    }

    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {

        Collection<String> cacheNames = context.getOperation().getCacheNames();

        Collection<Cache> result = new ArrayList<>(cacheNames.size());

        for (String cacheName : cacheNames) {

            List<Cache> collect = Arrays.stream(cacheManagers).map(e->e.getCache(cacheName)).collect(Collectors.toList());
            result.addAll(collect);
            if (result.size()==0) {
                throw new IllegalArgumentException("Cannot find cache named '" +
                        cacheName + "' for " + context.getOperation());
            }
        }
        return result;
    }

}
