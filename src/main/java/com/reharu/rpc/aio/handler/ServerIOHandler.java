package com.reharu.rpc.aio.handler;

import com.reharu.rpc.aio.callback.IOCallback;
import com.reharu.rpc.dto.RPCCommand;
import com.reharu.rpc.dto.RPCResult;

import java.nio.channels.AsynchronousSocketChannel;

public class ServerIOHandler extends JsonIOHandler<RPCCommand, RPCResult> {

    public ServerIOHandler(AsynchronousSocketChannel channel, IOCallback<RPCCommand, RPCResult> callback) {
        super(channel, callback);
    }
}
