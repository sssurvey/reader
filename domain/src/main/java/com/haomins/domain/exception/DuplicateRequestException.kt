package com.haomins.domain.exception

class DuplicateRequestException : IllegalStateException("The Use Case Can Only Be Executed One At a Time")