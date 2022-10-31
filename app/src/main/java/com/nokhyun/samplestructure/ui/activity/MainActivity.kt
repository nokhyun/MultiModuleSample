package com.nokhyun.samplestructure.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.doOnAttach
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.lifecycleScope
import com.nokhyun.samplestructure.BR
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.ActivityMainBinding
import com.nokhyun.samplestructure.observe.ConnectivityObserver
import com.nokhyun.samplestructure.observe.NetworkConnectivityObserver
import com.nokhyun.samplestructure.ui.dialog.PopupDialogFragment
import com.nokhyun.samplestructure.utils.*
import com.nokhyun.samplestructure.utils.Const.RequestCode.REQUEST_CODE_READ_EXTERNAL_STORAGE
import com.nokhyun.samplestructure.viewmodel.BaseViewModel
import com.nokhyun.samplestructure.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ActorScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Nokhyun90 on 2022-02-11
 * */
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var connectivityObserver: ConnectivityObserver

    private val _mainViewModel: MainViewModel by viewModels()

    override fun init() {
        binding.setVariable(BR.view, this)
        binding.setVariable(BR.viewModel, _mainViewModel)
        binding.lifecycleOwner = this

        //
        changeTextColor()

        // Device NetworkConnectivityObserver
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        lifecycleScope.launch {

            connectivityObserver.observe().onEach {
                binding.btnTest.text = "Network Status: $it"
            }.launchIn(lifecycleScope)
//            connectivityObserver.observe().collect {
//                binding.btnTest.text = "Network Status: $it"
//            }
        }


        permission(Manifest.permission.READ_EXTERNAL_STORAGE) { isGranted ->
            Timber.e("isGranted: $isGranted")
        }

        // lifecyclerScope
        Timber.e("init")


        var count = 0

//        CoroutineScope(Dispatchers.Main).launch {
        binding.btnTest.singleClick() {
//                log("${System.currentTimeMillis()}:: 눌러!!!: $count")
            log("${System.currentTimeMillis()}:: 눌러!!!")
//                count++


        }

//        binding.test.singleClick(2000) {
//        binding.test.test(CoroutineScope(Dispatchers.Main)) {
        binding.test.test1 {
            log("이게맞나")
        }

//        }
//

        lifecycleScope.launchWhenCreated {
            _mainViewModel.getRepoList()
        }

        showDialog()

//        lifecycleScope.launchWhenResumed {
//
//            _mainViewModel.githubReposStateSaved.collect {
//                Timber.e("githubRepos: $it")
//            }
//        }

        lifecycleScope.launchWhenCreated {
            _mainViewModel.githubRepos.collect {
                Timber.e("githubRepos1: $it")
            }
        }

        lifecycleScope.launchWhenCreated {
            _mainViewModel.githubRepos.collect {
                Timber.e("githubRepos2: $it")
            }
        }

        lifecycleScope.launchWhenResumed {
            _mainViewModel.githubRepoFirst10.collect {
                Timber.e("githubRepoFirst10: $it")
            }
        }

        lifecycleScope.launchWhenResumed {
            _mainViewModel.gitHubRepoLast10.collect {
                Timber.e("gitHubRepoLast10: $it")
            }
        }

        lifecycleScope.launchWhenResumed {
            _mainViewModel.gitHubRepos.collect {
                Timber.e("gitHubRepos1: $it")
            }
        }

        lifecycleScope.launchWhenResumed {
            _mainViewModel.gitHubRepos.collect {
                Timber.e("gitHubRepos2: $it")
            }
        }


        lifecycleScope.launch {
            val delayTest = listOf<Int>(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

            delayTest.map {
                Timber.e("it: $it ${SimpleDateFormat("mm:ss").format(Date(System.currentTimeMillis()))}")
            }.onEach {
                delay(1000)
            }.map {
                Timber.e("after delay result Time: ${SimpleDateFormat("mm:ss").format(Date(System.currentTimeMillis()))}")
            }
        }


        lifecycleScope.launch {
            delay(3000)
            binding.tilTest.error = "error"
        }
        binding.tilTest2.setEndIconOnClickListener {
            log("setEndIconOnClickListener")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            _mainViewModel.removeValue()
        }
    }

    private fun changeTextColor() {
//        binding.tvChangeColor.changeKeywordColor("스트")
    }

    private fun showDialog() {
        binding.btnDialog.setOnClickListener {
            PopupDialogFragment().show(supportFragmentManager, "popup")
        }
    }

    fun figmaSample() {
        goActivity(LoggedOutActivity::class.java)
    }

    override fun setView(view: (layoutId: Int) -> Unit) {
        view(R.layout.activity_main)
    }

    fun log(msg: String) {
        Timber.e(msg)
    }

    override suspend fun coroutineInit() {
//        _mainViewModel.test()
    }

    override fun navigator() {
        _mainViewModel.baseResultNavigator.observe(this) { result ->
            when (result) {
                is BaseViewModel.BaseResult.ToastMsg -> result.message.showToastShort(this)
                is BaseViewModel.BaseResult.ServerError -> result.message.showToastShort(this)
                else -> {}
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }

    private val _requestActivity: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            activityResult?.let { result ->
                if (result.resultCode == RESULT_OK && result.data != null) {
                    // todo
                    Timber.e("result: ${result.data}")
                }
            }
        }

    fun gallery() {
        // todo 권한체크
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                goActivity(GalleryActivity::class.java)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                // todo 권한 알림 Popup
                Timber.e("shouldShowRequestPermissionRationale")
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_READ_EXTERNAL_STORAGE
                )
            }
            else -> {
                Timber.e("requestPermissions")
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_READ_EXTERNAL_STORAGE
                )
            }

//        _requestActivity.launch(Intent().apply {
//            action = Intent.ACTION_GET_CONTENT
//            type = "image/*"
//        })
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE_READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // todo 동의
                    Timber.e("동의")
                } else {
                    // todo 거절 토스트 보여줌
                    Timber.e("거절")
                    // todo 팝업으로 알려준 후 동의 시 앱 설정으로 이동
                    goAppSetting()
                }
            }
            else -> {}
        }
    }

}

/** kotlin actor 2022 중에 수정 될 수 있다고 함. */
@OptIn(ObsoleteCoroutinesApi::class)
fun View.singleClick(delay: Long = 500, click: (v: View) -> Unit) {
    var clickTime: Long = 0
    val event = CoroutineScope(Dispatchers.Main).actor<View>(Dispatchers.Main) {
        for (event in channel) {
            Timber.e("actor: $event")
            click(event)
        }
    }
    setOnClickListener {
        if (System.currentTimeMillis() > clickTime + delay) {
            clickTime = System.currentTimeMillis()
            event.trySend(it)
        }
    }
}

fun View.test(coroutineScope: CoroutineScope, click: (v: View) -> Unit) {
//    coroutineScope.launch {
    val flow = MutableSharedFlow<View>()

    coroutineScope.launch {
        setOnClickListener {
//            flow.emit(this@test)
        }
    }

    suspend {
        flow.collectLatest {
            click.invoke(it)
        }
    }
//    }

}

fun View.test1(click: (v: View) -> Unit) {
    val c = Channel<View>()

    c.trySend(this)

    setOnClickListener {
        Timber.e("alskdjl")
        suspend {
            click(c.receive())
        }
    }
}

fun <E> ActorScope<E>.delay(clickTime: Long) {

}

suspend fun test() {
    val sharedFlow = MutableSharedFlow<Any>()
    sharedFlow.emit("asd")

}