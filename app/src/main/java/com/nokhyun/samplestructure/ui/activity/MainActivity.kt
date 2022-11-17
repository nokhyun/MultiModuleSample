package com.nokhyun.samplestructure.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.MotionEvent
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nokhyun.samplestructure.BR
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.adapter.BodyValue
import com.nokhyun.samplestructure.adapter.SelectAdapter
import com.nokhyun.samplestructure.adapter.SelectedUiState
import com.nokhyun.samplestructure.databinding.ActivityMainBinding
import com.nokhyun.samplestructure.module.SampleEntryPoint
import com.nokhyun.samplestructure.observe.ConnectivityObserver
import com.nokhyun.samplestructure.observe.NetworkConnectivityObserver
import com.nokhyun.samplestructure.service.LocationService
import com.nokhyun.samplestructure.utils.*
import com.nokhyun.samplestructure.utils.Const.RequestCode.REQUEST_CODE_READ_EXTERNAL_STORAGE
import com.nokhyun.samplestructure.viewmodel.BaseViewModel
import com.nokhyun.samplestructure.viewmodel.MainViewModel
import dagger.hilt.EntryPoints
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

    private val selectedAdapter by lazy {
        SelectAdapter(this)
    }

    private val list = mutableListOf<SelectedUiState>().apply {
        add(SelectedUiState.Header("header"))

        for (i in 0 until 30) {
            add(SelectedUiState.Body(BodyValue("Text # $i")))
        }
    }

    private val _selectedStateFlow: MutableStateFlow<List<SelectedUiState>?> = MutableStateFlow(null)
    val selectedStateFlow: StateFlow<List<SelectedUiState>?> = _selectedStateFlow.asStateFlow()

    // 기존 데이터 저장
    private var tempList: List<SelectedUiState> = emptyList()

    fun selected(body: SelectedUiState.Body) {
        lifecycleScope.launch {
            Timber.e("selected: $body")

            // copy 로 생성하거나 아예 새로 생성하여 처리 (deep copy)
            val copyValue = SelectedUiState.Body(body.bodyValue.copy(isSelected = !body.bodyValue.isSelected))
            // 물론 이것도 가능함.
//        val copyValue = SelectedUiState.Body(BodyValue(text = body.bodyValue.text, isSelected = !body.bodyValue.isSelected))
//        val temp = tempList.toMutableList()
//
//        // 기존데이터는 전부 false 고 선택한 데이터는 이름이 같으면 true로 변경됨. 그로 인해 나머지는 자동으로 선택해제된다.
            tempList.toMutableList().apply {
                replaceAll {
                    when (it) {
                        is SelectedUiState.Header -> it
                        is SelectedUiState.Body -> if (it.bodyValue.text == copyValue.bodyValue.text) copyValue else it
                    }
                }
            }.also {
//                _selectedStateFlow.update { it?.toList() }
//                _selectedStateFlow.emit(it.toList())
                _selectedStateFlow.value = it.toList()
//                selectedAdapter.submitList(it.toList())
            }
        }
    }

    override fun init() {
        binding.setVariable(BR.view, this)
        binding.setVariable(BR.viewModel, _mainViewModel)
        binding.lifecycleOwner = this

        // adapter
        binding.rvSelected.apply {
            adapter = selectedAdapter
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = null
        }
        // 실제사용시엔 savedStateHandle 저장
        tempList = list

        selectedAdapter.submitList(list)

        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                selectedStateFlow.filterNotNull().collectLatest {
                    selectedAdapter.submitList(it)
                }
            }
        }
        // end

        // location
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            ), 1001
        )
        binding.btnPermission.setOnClickListener {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                Timber.e("권한 있음")
//            } else {
//                Timber.e("권한 요청")
//                ActivityCompat.requestPermissions(
//                    this, arrayOf(
//                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
//                    ), 1001
//                )
//            }

            when {
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                    Timber.e("체크 권한 있음")
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                    Timber.e("왜 취소함")
                    MaterialAlertDialogBuilder(this).setTitle("왜 취소했어?")
                        .setMessage("왜??")
                        .setPositiveButton("확인") { dialog, _ ->
                            dialog.dismiss()
                        }.create().show()
                }
                else -> {
                    Timber.e("권한 요청")
                    ActivityCompat.requestPermissions(
                        this, arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
                        ), 1001
                    )
                }
            }
        }

        // EntryPoint Test
        val money = EntryPoints.get(this, SampleEntryPoint::class.java).getMoney()
        Timber.e("money result: ${money.money}")

        //
        changeTextColor()

        // liveData
        liveDataTest()

        // TOUCH EVENT
        setTouchEvent()

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
        binding.btnTest.singleClick {
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

        binding.tvChangeColor.setOnClickListener {
            startService(Intent(applicationContext, LocationService::class.java).apply {
                action = LocationService.ACTION_STOP
            })
        }
    }

    private fun liveDataTest() {
        val liveData: LiveData<String> = MutableLiveData<String>()

        /*
       * https://developer.android.com/reference/androidx/lifecycle/LiveData#observeForever(androidx.lifecycle.Observer%3C?%20super%20T%3E)
       * 지정된 관찰자를 관찰자 목록에 추가합니다. 이 호출은 observe항상 활성 상태인 LifecycleOwner와 유사합니다.
       *  이는 주어진 관찰자가 모든 이벤트를 수신하고 자동으로 제거되지 않음을 의미합니다. removeObserver이 LiveData 관찰을 중지 하려면 수동으로 호출해야 합니다 .
       *  LiveData에는 이러한 관찰자 중 하나가 있지만 활성 상태로 간주됩니다.
       * 관찰자가 이 LiveData의 소유자와 함께 이미 추가된 경우 LiveData는 IllegalArgumentException.
       * */
//        liveData.observeForever(ForeverObserver())
//        liveData.removeObservers(this)

        val testLiveData: TestLiveData<String> = TestLiveData()
        testLiveData.value = "asd"

        testLiveData.observe(this) {
            Timber.e("observe Call")
        }
    }

    class TestLiveData<String> : MutableLiveData<String>() {
        override fun onActive() {
            // 활성화 되었을 때 동작
            super.onActive()
            Timber.e("onActive hasObserver: ${hasObservers()}")
        }

        override fun onInactive() {
            // 백그라운드 또는 재 시작 되었을 때 동작하긴하는데... 문서에 따르면 start, resume, 옵저버의 유무를 의미하지 않음.
            super.onInactive()
            Timber.e("onInactive hasObserver: ${hasObservers()}")
        }
    }

    private class ForeverObserver : androidx.lifecycle.Observer<String> {
        override fun onChanged(t: String?) {
            Timber.e("value changed")
        }
    }

    private fun changeTextColor() {
//        binding.tvChangeColor.changeKeywordColor("스트")
    }

    private fun showDialog() {
        binding.btnDialog.setOnClickListener {
//            PopupDialogFragment().show(supportFragmentManager, "popup")
            startService(Intent(applicationContext, LocationService::class.java).apply {
                action = LocationService.ACTION_START
            })
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

    private val _requestActivity: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
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
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                goActivity(GalleryActivity::class.java)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                // todo 권한 알림 Popup
                Timber.e("shouldShowRequestPermissionRationale")

            }
            else -> {
                Timber.e("requestPermissions")
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_READ_EXTERNAL_STORAGE
                )
            }

//        _requestActivity.launch(Intent().apply {
//            action = Intent.ACTION_GET_CONTENT
//            type = "image/*"
//        })
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
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
            1001 -> {
                Timber.e("위치권한")
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Timber.e("동의")
                } else {
                    Timber.e("거절")
                }
            }
            else -> {
                Timber.e("누구냐")
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchEvent() {
        binding.btnStroke.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
                    binding.flStroke.background = ContextCompat.getDrawable(this, R.drawable.stroke_double)
                }
                MotionEvent.ACTION_UP -> {
                    v.background = ContextCompat.getDrawable(this, R.drawable.stroke_single)
                    binding.flStroke.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
                }
            }

            false
        }

        binding.btnPermission.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.updateLayoutParams {
                        width = 92.dp
                        height = 48.dp
                    }

                    v.background = ContextCompat.getDrawable(this, R.drawable.stroke_double)
                }
                MotionEvent.ACTION_UP -> {
                    v.updateLayoutParams {
                        width = 80.dp
                        height = 36.dp
                    }

                    v.background = ContextCompat.getDrawable(this, R.drawable.stroke_single)
                }
            }

            false
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