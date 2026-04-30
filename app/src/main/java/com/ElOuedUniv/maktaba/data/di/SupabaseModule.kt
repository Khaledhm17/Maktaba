package com.ElOuedUniv.maktaba.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            // الرابط الخاص بمشروعك
            supabaseUrl = "https://zpnjocoeccqcrccevydq.supabase.co",
            // تأكد من وضع مفتاح الـ API (Anon Key) الصحيح هنا
            supabaseKey = "sb_publishable_ArjDRZvfAyoGe1l_bUd0ag_ip1nfs5k"
        ) {
            // تثبيت الإضافات التي تحتاجها
            install(Postgrest)
            install(Storage)
        }
    }

    @Provides
    @Singleton
    fun providePostgrest(client: SupabaseClient): Postgrest {
        return client.postgrest
    }

    @Provides
    @Singleton
    fun provideStorage(client: SupabaseClient): Storage {
        return client.storage
    }
}