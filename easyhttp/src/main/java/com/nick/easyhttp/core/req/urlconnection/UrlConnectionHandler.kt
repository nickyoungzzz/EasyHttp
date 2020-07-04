package com.nick.easyhttp.core.req.urlconnection

import com.nick.easyhttp.config.EasyHttp
import com.nick.easyhttp.core.req.HttpHandler
import com.nick.easyhttp.result.HttpReq
import com.nick.easyhttp.result.HttpResp

class UrlConnectionHandler : HttpHandler {

	override fun execute(httpReq: HttpReq): HttpResp {
		val urlConnectionReq = UrlConnectionReq.Builder()
			.reqMethod(httpReq.reqMethod).reqTag(httpReq.reqTag).url(httpReq.url)
			.asDownload(httpReq.asDownload).isMultiPart(httpReq.httpReqBody.isMultiPart)
			.jsonString(httpReq.httpReqBody.jsonString).fieldMap(httpReq.httpReqBody.fieldMap)
			.headerMap(httpReq.httpReqHead.headerMap).queryMap(httpReq.httpReqHead.queryMap)
			.multipartBody(httpReq.httpReqBody.multipartBody).build()

		val urlConnectionResp = EasyHttp.urlConnectionClient.proceed(urlConnectionReq)
		return HttpResp(urlConnectionResp.resp, urlConnectionResp.code, urlConnectionResp.isSuccessful,
			urlConnectionResp.headers, urlConnectionResp.exception, urlConnectionResp.contentLength, urlConnectionResp.inputStream, urlConnectionResp.url)
	}

	override fun cancel() {
		EasyHttp.urlConnectionClient.cancel()
	}

	override val requestClient: String
		get() = "UrlConnection"
}