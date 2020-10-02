package com.studa.android.client.api.data_sources.impl

import com.studa.android.client.api.Response
import com.studa.android.client.api.WeakApiResponse
import com.studa.android.client.api.data_sources.TeacherDataSource
import com.studa.android.client.api.dto.Teacher
import com.studa.android.client.api.network_wrapper.NetworkWrapper
import com.studa.android.client.utils.defaultErrorHandler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class TeacherDataSourceImpl @Inject constructor(
    networkWrapper: NetworkWrapper
) : TeacherDataSource {
    private val api = networkWrapper.getApi()

    override fun createTeacher(teacher: Teacher): Single<Response<Teacher>> =
        Single.create { emitter ->
            api.createTeacher(teacher)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getTeacherObservableSubscriber(emitter))
        }

    override fun getTeacherById(id: UUID): Single<Response<Teacher>> =
        Single.create { emitter ->
            api.getTeacherById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getTeacherObservableSubscriber(emitter))
        }


    override fun updateTeacher(teacher: Teacher): Single<Response<Unit>> =
        Single.create { emitter ->
            teacher.id?.let {
                api.updateTeacher(it, teacher)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { emitter.onSuccess(Response.Ok()) },
                        { error -> emitter.onSuccess(defaultErrorHandler(error)) }
                    )
            }
        }

    override fun deleteTeacherById(id: UUID): Single<Response<Unit>> =
        Single.create { emitter ->
            api.deleteTeacher(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { emitter.onSuccess(Response.Ok()) },
                    { error -> emitter.onSuccess(defaultErrorHandler(error)) }
                )
        }

    override fun getAllAccessibleTeachers(): Single<Response<List<Teacher>>> =
        Single.create { emitter ->
            api.getAllAccessibleTeachers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .map { weakResponse -> weakResponse.payload ?: emptyList() }
                .flatMapIterable { it }
                .filter { it.firstName != null && it.id != null }
                .toList()
                .subscribe(
                    { emitter.onSuccess(Response.Ok(it)) },
                    { error -> emitter.onSuccess(defaultErrorHandler(error)) }
                )
        }


    private fun getTeacherObservableSubscriber(
        emitter: SingleEmitter<Response<Teacher>>
    ) = object : SingleObserver<WeakApiResponse<Teacher>> {
        override fun onSuccess(response: WeakApiResponse<Teacher>) =
            if (response.payload != null) {
                emitter.onSuccess(Response.Ok(response.payload))
            } else {
                emitter.onSuccess(Response.Error.UnresolvedApiError())
            }

        override fun onError(error: Throwable) =
            emitter.onSuccess(defaultErrorHandler(error))

        override fun onSubscribe(d: Disposable) = Unit
    }
}