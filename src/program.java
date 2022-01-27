public UserLoginResponse loginUser(UserLoginRequest request) throws BadRequestException {
        User user = userService.getUserByEmail(request.getEmail());
        if (user == null) {
        throw new BadRequestException(ErrorCode.INCORRECT_LOGIN, "login");
        }
        String token = tokenService.getToken(user);
        response.addHeader(SET_AUTH_HEADER_STRING, token);
        UserLoginResponse loginDtoResponse = new UserLoginResponse(token);
        return loginDtoResponse;
        }
