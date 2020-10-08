package com.studa.android.client.api.data_sources.impl

import com.studa.android.client.api.Response
import com.studa.android.client.api.WeakApiResponse
import com.studa.android.client.api.data_sources.LessonDataSource
import com.studa.android.client.api.dto.LessonDTO
import com.studa.android.client.api.dto.TeacherDTO
import com.studa.android.client.api.network_wrapper.NetworkWrapper
import com.studa.android.client.utils.defaultErrorHandler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class LessonDataSourceImpl
@Inject constructor(networkWrapper: NetworkWrapper) : LessonDataSource {
    private val api = networkWrapper.getApi()

    override fun createLesson(lesson: LessonDTO): Single<Response<LessonDTO>> =
        Single.create { emitter ->
            api.createLesson(lesson)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getLessonObservableSubscriber(emitter))
        }

    override fun getLessonById(id: UUID): Single<Response<LessonDTO>> =
        Single.create { emitter ->
            api.getLessonById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getLessonObservableSubscriber(emitter))
        }

    override fun updateLesson(lesson: LessonDTO): Single<Response<Unit>> =
        Single.create { emitter ->
            lesson.id?.let {
                api.updateLesson(it, lesson)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { emitter.onSuccess(Response.Ok()) },
                        { error -> emitter.onSuccess(defaultErrorHandler(error)) }
                    )
            }
        }

    override fun deleteLessonById(id: UUID): Single<Response<Unit>> =
        Single.create { emitter ->
            api.deleteLesson(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { emitter.onSuccess(Response.Ok()) },
                    { error -> emitter.onSuccess(defaultErrorHandler(error)) }
                )
        }


    override fun getAllLessonsForDate(date: String): Single<Response<List<LessonDTO>>> =
        Single.create { emitter ->
            api.getAllLessonsForDate(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .map { it.payload ?: emptyList() }
                .flatMapIterable { it }
                .filter { lesson -> lesson.title != null }
                .toList()
                .subscribe(
                    { emitter.onSuccess(Response.Ok(it)) },
                    { error -> emitter.onSuccess(defaultErrorHandler(error)) }
                )
        }

    private fun getLessonObservableSubscriber(
        emitter: SingleEmitter<Response<LessonDTO>>
    ) = object : SingleObserver<WeakApiResponse<LessonDTO>> {
        override fun onSuccess(response: WeakApiResponse<LessonDTO>) =
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