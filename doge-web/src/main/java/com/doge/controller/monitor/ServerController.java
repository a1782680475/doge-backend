package com.doge.controller.monitor;

import com.doge.utils.monitor.Server;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控业务
 *
 * @author shixinyu
 * @date 2021-08-28 10:05
 */
@RestController
@RequestMapping("/monitor")
@Api(value = "ServerController", tags = "服务器监控业务")
public class ServerController {
    @PreAuthorize("@aps.hasPermission('monitor:server')")
    @GetMapping("/server")
    public Server get() throws Exception {
        Server server = new Server();
        server.copyTo();
        return server;
    }
}
