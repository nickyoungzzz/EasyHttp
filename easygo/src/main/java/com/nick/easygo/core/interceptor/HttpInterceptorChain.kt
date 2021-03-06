package com.nick.easygo.core.interceptor

import com.nick.easygo.result.HttpReq
import com.nick.easygo.result.HttpResp

class HttpInterceptorChain(
    private val httpInterceptors: List<HttpInterceptor>,
    private var index: Int = 0,
    private val httpReq: HttpReq
) : HttpInterceptor.Chain {

    override fun proceed(httpReq: HttpReq): HttpResp {
        if (index >= httpInterceptors.size) {
            throw AssertionError()
        }
        val next = HttpInterceptorChain(httpInterceptors, index + 1, httpReq)
        val interceptor = httpInterceptors[index]
        return interceptor.intercept(next)
    }

    override fun request(): HttpReq {
        return httpReq
    }
}