/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 9:22 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 9:22 PM
 */

package chat.revolt.dashboard.presentation.dashboard.di

import chat.revolt.dashboard.data.ServerService
import chat.revolt.dashboard.data.data_source.server.ServerDataSource
import chat.revolt.dashboard.data.data_source.server.ServerDataSourceImpl
import chat.revolt.dashboard.data.mapper.FetchMembersMapper
import chat.revolt.dashboard.data.repository.members.MembersRepositoryImpl
import chat.revolt.dashboard.domain.repository.members.MembersRepository
import chat.revolt.dashboard.presentation.dashboard.vm.DashboardViewModel
import chat.revolt.data.local.mappers.member.MemberEntityMapper
import chat.revolt.data.remote.mappers.member.MemberMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val dashboardModule = module {
    viewModel { DashboardViewModel(networkStateManager = get()) }
    single<ServerService> { get<Retrofit>().create(ServerService::class.java) }
    single<ServerDataSource> { ServerDataSourceImpl(service = get()) }
    single { MemberMapper(attachmentMapper = get()) }
    single { MemberEntityMapper(attachmentEntityMapper = get()) }
    single { FetchMembersMapper(memberMapper = get(), userMapper = get()) }
    single<MembersRepository> {
        MembersRepositoryImpl(
            serverDataSource = get(),
            fetchMembersMapper = get(),
            memberDao = get(),
            memberEntityMapper = get()
        )
    }
}