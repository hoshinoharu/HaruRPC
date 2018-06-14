package com.reharu.rpc.aio.callback;

import com.reharu.rpc.dto.RPCResult;

public interface RPCInvokeCallback {
    void onResult(RPCResult result);
}
