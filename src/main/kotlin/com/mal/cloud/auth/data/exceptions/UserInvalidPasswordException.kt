package com.mal.cloud.auth.data.exceptions

import javax.naming.AuthenticationException

class UserInvalidPasswordException(
    explanation: String = ""
) : AuthenticationException(explanation)