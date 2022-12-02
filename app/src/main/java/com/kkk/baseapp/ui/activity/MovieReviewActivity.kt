//package com.kkk.baseapp.ui.activity
//
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.Menu
//import android.view.MenuInflater
//import android.view.MenuItem
//import androidx.core.view.MenuProvider
//import com.kkk.baseapp.R
//import com.kkk.baseapp.databinding.ActivityMainBinding
//import com.kkk.baseapp.databinding.ActivityMovieReviewBinding
//import com.kkk.baseapp.ui.fragment.moviereview.popular.PopularMovieFragment
//import com.kkk.baseapp.ui.fragment.moviereview.upcoming.UpcomingMovieFragment
//import com.kkk.baseapp.viewmodel.MainViewModel
//import com.kkk.mylibrary.ui.activity.BaseActivity
//import com.kkk.mylibrary.ui.activity.BaseViewBindingActivity
//import com.kkk.mylibrary.ui.adapter.ViewPagerAdapter
//import org.koin.androidx.viewmodel.ext.android.viewModel
//
//
//class MovieReviewActivity : BaseViewBindingActivity<ActivityMovieReviewBinding>() {
//
////    private val mViewModel: MainViewModel by lazy {
////        ViewModelProviders.of(this, MainViewModelFactory(Injection.provideMainRepository(applicationContext), AndroidSchedulerProvider()
////        ))
////            .get(MainViewModel::class.java)
////    }
//    private val mViewModel: MainViewModel by viewModel()
//
//    companion object{
//        private const val IE_NAME = "IE_NAME"
//        fun newIntent(context:Context,name:String):Intent{
//            val intent = Intent(context,MovieReviewActivity::class.java)
//            intent.putExtra(IE_NAME,name)
//            return intent
//        }
//    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        setContentView(binding.root)
//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = intent.getStringExtra(IE_NAME)
////        showLoadingView()
//        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager,this)
//        viewPagerAdapter.addFragments(UpcomingMovieFragment(),"Upcoming")
//        viewPagerAdapter.addFragments(PopularMovieFragment(),"Popular")
//        binding.viewPagerMovie.adapter = viewPagerAdapter
//        binding.tabLayoutType.setupWithViewPager(binding.viewPagerMovie)
//    }
//
//    override fun setup() {
//        addMenuProvider(object:MenuProvider{
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menuInflater.inflate(R.menu.movie_review_menu,menu)
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                val id: Int = menuItem.itemId
//                if (id == R.id.menu_favourite) {
//                    val intent = FavouriteListActivity.newIntent(this@MovieReviewActivity,"Favourite")
//                    startActivity(intent)
//                    return true
//                }
//                return true
//            }
//
//        })
//    }
//
//    override fun observers() {
//    }
//
//    override fun listeners() {
//    }
//
//    override val bindingInflater: (LayoutInflater) -> ActivityMovieReviewBinding
//        get() = ActivityMovieReviewBinding::inflate
//
//}