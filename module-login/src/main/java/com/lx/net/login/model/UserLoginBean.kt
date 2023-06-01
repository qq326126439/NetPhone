package com.lx.net.login.model

data class UserLoginBean(
    val member: Member,
    val token: String
) {
    data class Member(
        val isApp: Int,
        val members: Members
    ) {
        data class Members(
            val accountId: Int,
            val email: String,
            val enabled: Boolean,
            val inviteCode: String,
            val isApp: Int,
            val memberId: Int,
            val password: String,
            val ref_id: Int,
            val trxBalance: Int,
            val usdtBalance: Int,
            val username: String
        )
    }
}