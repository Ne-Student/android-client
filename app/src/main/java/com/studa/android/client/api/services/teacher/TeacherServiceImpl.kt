package com.studa.android.client.api.services.teacher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.studa.android.client.api.ApiEndpoints
import com.studa.android.client.api.Response
import com.studa.android.client.api.WeakApiResponse
import com.studa.android.client.api.model.Teacher
import com.studa.android.client.utils.defaultErrorHandler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class TeacherServiceImpl @Inject constructor(
    val api: ApiEndpoints
): TeacherService {

    override fun createTeacher(teacher: Teacher): LiveData<Response<Teacher>> {
        val result: MutableLiveData<Response<Teacher>> = MutableLiveData()
        api.createTeacher(teacher)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getTeacherObservableSubscriber(result))
        return result
    }

    override fun getTeacherById(id: UUID): LiveData<Response<Teacher>> {
        val result: MutableLiveData<Response<Teacher>> = MutableLiveData()
        api.getTeacherById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getTeacherObservableSubscriber(result))
        return result
    }

    override fun updateTeacher(teacher: Teacher): LiveData<Response<Unit>> {
        val result: MutableLiveData<Response<Unit>> = MutableLiveData()
        teacher.id?.let {
            api.updateTeacher(it, teacher)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result.value = Response.Ok() },
                    { error -> result.value = defaultErrorHandler(error) }
                )
        }
        return result
    }

    override fun deleteTeacherById(id: UUID): LiveData<Response<Unit>> {
        val result: MutableLiveData<Response<Unit>> = MutableLiveData()
        api.deleteTeacher(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result.value = Response.Ok() },
                { error -> result.value = defaultErrorHandler(error) }
            )

        return result
    }

    override fun getAllAccessibleTeachers(): LiveData<Response<List<Teacher>>> {
        val result: MutableLiveData<Response<List<Teacher>>> = MutableLiveData()
        api.getAllAccessibleTeachers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()
            .map { weakResponse -> weakResponse.payload ?: emptyList() }
            .flatMapIterable { it }
            .filter { it.firstName != null && it.id != null }
            .toList()
            .subscribe(
                { result.value = Response.Ok(it) },
                { error -> result.value = defaultErrorHandler(error) }
            )

        return result
    }

    private fun getTeacherObservableSubscriber(
        result: MutableLiveData<Response<Teacher>>
    ) = object : SingleObserver<WeakApiResponse<Teacher>> {
        override fun onSuccess(response: WeakApiResponse<Teacher>) =
            if (response.payload != null) {
                result.value = Response.Ok(response.payload)
            } else {
                result.value = Response.Error.UnresolvedApiError()
            }

        override fun onError(error: Throwable) {
            result.value = defaultErrorHandler(error)
        }

        override fun onSubscribe(d: Disposable) {}
    }
}