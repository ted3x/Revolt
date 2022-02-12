/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 6:13 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 6:13 PM
 */

package chat.revolt.domain.interactors

abstract class BaseUseCase<I, O> {
    abstract suspend fun invoke(params: I): O
}