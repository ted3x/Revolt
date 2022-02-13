/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 2:13 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 2:13 AM
 */

package chat.revolt.socket.server.type

enum class AuthenticationErrorType {
    LabelMe,
    InternalError,
    InvalidSession,
    OnboardingNotFinished,
    AlreadyAuthenticated
}