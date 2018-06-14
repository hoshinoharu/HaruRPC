package com.reharu.rpc.aio.handler;

import com.reharu.rpc.aio.callback.IOCallback;
import com.reharu.rpc.dto.RPCCommand;
import com.reharu.rpc.dto.RPCResult;

import java.nio.channels.AsynchronousSocketChannel;

public class ClientIOHandler extends JsonIOHandler<RPCResult, RPCCommand> {
    public ClientIOHandler(AsynchronousSocketChannel channel, IOCallback<RPCResult, RPCCommand> callback) {
        super(channel, callback);
    }
}
