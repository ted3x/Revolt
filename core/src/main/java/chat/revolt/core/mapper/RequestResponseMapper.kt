/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 5:39 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 5:39 PM
 */

package chat.revolt.core.mapper

interface RequestResponseMapper<DTO_REQUEST, DOMAIN_REQUEST, DTO_RESPONSE, DOMAIN_RESPONSE> {

    suspend fun mapToRequest(from: DOMAIN_REQUEST): DTO_REQUEST
    suspend fun mapToResponse(from: DTO_RESPONSE): DOMAIN_RESPONSE
}