package com.mal.cloud.auth.data.exceptions

import javax.naming.AuthenticationException

class UserInvalidValuesException(
    explanation: String = ""
) : AuthenticationException(explanation)