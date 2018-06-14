package com.reharu.rpc.aio.callback;


import com.reharu.rpc.aio.handler.IOHandler;

public interface IOCallback<I, O> {
    void onReadCompleted(IOHandler<I, O> handler) ;
}
