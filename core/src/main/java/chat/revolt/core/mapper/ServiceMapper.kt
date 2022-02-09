/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 1:33 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 1:33 AM
 */

package chat.revolt.core.mapper

interface ServiceMapper<RequestDTO, DomainRequest, ResponseDTO, DomainResponse> {

    fun mapToRequest(from: DomainRequest): RequestDTO

    fun mapToResponse(from: ResponseDTO): DomainResponse
}