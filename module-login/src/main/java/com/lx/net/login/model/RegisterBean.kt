package com.lx.net.login.model

data class RegisterBean(
    val member: Member,
    val status: Int,
    val token: String
) {
    data class Member(
        val isApp: Int,
        val members: Members
    ) {
        data class Members(
            val accountId: Int,
            val cnyBalance: Int,
            val email: String,
            val enabled: Boolean,
            val inviteCode: String,
            val isApp: Int,
            val level: Int,
            val memberId: Int,
            val password: String,
            val phone: String,
            val usdtBalance: Int,
            val username: String
        )
    }
}