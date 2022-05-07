package com.mal.cloud.auth.data.exceptions

import javax.naming.AuthenticationException

class UserAlreadyExistException(
    explanation: String = ""
) : AuthenticationException(explanation)