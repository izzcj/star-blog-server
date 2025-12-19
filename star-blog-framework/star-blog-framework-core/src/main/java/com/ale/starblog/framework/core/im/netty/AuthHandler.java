package com.ale.starblog.framework.core.im.netty;

import com.ale.starblog.framework.common.security.AuthenticatedUser;
import com.ale.starblog.framework.common.security.TokenManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.AttributeKey;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 鉴权处理器
 *
 * @author Ale
 * @version 1.0.0 2025/12/19 16:36
 */
@ChannelHandler.Sharable
public class AuthHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    /**
     * token管理器
     */
    private final TokenManager tokenManager;

    public AuthHandler(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) {
        String uri = req.uri();
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
        Map<String, List<String>> params = queryStringDecoder.parameters();
        String userId = null;
        if (params.containsKey("token")) {
            String token = params.get("token").getFirst();
            Object principal = Optional.ofNullable(this.tokenManager.extractAuthentication(token))
                .map(Authentication::getPrincipal)
                .orElse(null);
            if (principal == null) {
                ctx.close();
                return;
            }
            if (principal instanceof AuthenticatedUser authenticatedUser) {
                userId = authenticatedUser.getId().toString();
            }
        }
        ctx.channel().attr(AttributeKey.valueOf("userId")).set(userId);
        ctx.fireChannelRead(req.retain());
    }

}
