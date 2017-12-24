package com.bbinsurance.android.app.fresco

import com.facebook.imagepipeline.memory.ByteArrayPool
import com.facebook.imagepipeline.memory.PooledByteBufferFactory
import com.facebook.imagepipeline.producers.FetchState
import com.facebook.imagepipeline.producers.NetworkFetchProducer
import com.facebook.imagepipeline.producers.NetworkFetcher

/**
 * Created by jiaminchen on 17/12/25.
 */
class BBNetworkFetchProducer : NetworkFetchProducer {
    constructor(pooledByteBufferFactory: PooledByteBufferFactory,
                byteArrayPool: ByteArrayPool, networkFetcher: NetworkFetcher<FetchState>)
            : super(pooledByteBufferFactory, byteArrayPool, networkFetcher)


}