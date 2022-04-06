package com.fork.spoonfeed.data.remote.datasource

import com.fork.spoonfeed.data.remote.api.community.PostService
import com.fork.spoonfeed.data.remote.model.community.*
import com.fork.spoonfeed.data.remote.model.policy.RequestReportData
import com.fork.spoonfeed.data.remote.model.policy.ResponseReportData
import javax.inject.Inject

class PostDataSourceImpl @Inject constructor(private val postService: PostService) : PostDataSource {

    override suspend fun getPostAll(): ResponsePostAllData {
        return postService.getPostAll()
    }

    override suspend fun sendPost(body: RequestSendPostData): ResponseSendPostData {
        return postService.sendPost(body = body)
    }

    override suspend fun getPostDetail(pk: Int): ResponsePostData {
        return postService.getPostDetail(pk = pk)
    }

    override suspend fun patchPost(pk: Int, body: RequestPatchPostData): ResponsePatchPostData {
        return postService.patchPost(pk = pk, body = body)
    }

    override suspend fun deletePost(pk: Int): ResponseDeletePostData {
        return postService.deletePost(pk = pk)
    }

    override suspend fun searchPost(query: String): ResponseSearchPostAllData {
        return postService.searchPost(query)
    }

    override suspend fun postReport(postPk: Int, body: RequestReportData): ResponseReportData {
        return postService.postReport(postPk = postPk, body = body)
    }
}
