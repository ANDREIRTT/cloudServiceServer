package com.mal.cloud.auth.data.exceptions

import javax.naming.AuthenticationException

class UserNotExistException(
    explanation: String = ""
) : AuthenticationException(explanation)