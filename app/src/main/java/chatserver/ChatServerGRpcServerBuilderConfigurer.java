package chatserver;

import io.grpc.ServerBuilder;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.netty.shaded.io.netty.channel.ChannelOption;
import org.lognet.springboot.grpc.GRpcServerBuilderConfigurer;
import org.springframework.stereotype.Component;

/**
 * 这个类用于更改gRPC ServerBuilder 的配置，此处，只添加TCP_NODELAY条目
 */
@Component
public class ChatServerGRpcServerBuilderConfigurer extends GRpcServerBuilderConfigurer {
    @Override
    public void configure(ServerBuilder<?> serverBuilder) {
        ((NettyServerBuilder)serverBuilder).withChildOption(ChannelOption.TCP_NODELAY, true);
    }
}
