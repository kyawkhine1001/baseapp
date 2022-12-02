//package com.kkk.baseapp.ui.fragment.moviereview.popular
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.lifecycle.Observer
//import androidx.recyclerview.widget.GridLayoutManager
//import com.kkk.baseapp.databinding.FragmentPopularMovieBinding
//import com.kkk.baseapp.ui.activity.MovieReviewDetailActivity
//import com.kkk.baseapp.ui.adapter.MovieListAdapter
//import com.kkk.baseapp.ui.vo.MovieVO
//import com.kkk.baseapp.viewmodel.MainViewModel
//import com.kkk.mylibrary.ui.fragment.BaseViewBindingFragment
//import org.koin.androidx.viewmodel.ext.android.viewModel
//
//class PopularMovieFragment : BaseViewBindingFragment<FragmentPopularMovieBinding>() {
//    private val mMovieListAdapter: MovieListAdapter by lazy { MovieListAdapter{
//        val intent = MovieReviewDetailActivity.newIntent(requireContext(),it.movieId!!,it.title!!,it.favouriteMovie!!)
//        startActivity(intent)
//    } }
//
////    private val mViewModel: MainViewModel by lazy {
////        ViewModelProviders.of(this, MainViewModelFactory(Injection.provideMainRepository(requireContext()), AndroidSchedulerProvider()))
////            .get(MainViewModel::class.java)
////    }
//    private val mViewModel: MainViewModel by viewModel()
//
////    override fun onCreateView(
////        inflater: LayoutInflater, container: ViewGroup?,
////        savedInstanceState: Bundle?
////    ): View? {
//////        val view = inflater.inflate(R.layout.fragment_popular_movie, container, false)
////
////
////        return view
////    }
//
//    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPopularMovieBinding
//        get() = FragmentPopularMovieBinding::inflate
//
//    override fun setup() {
//        binding.recyclerViewPopularMovie.apply {
//            layoutManager = GridLayoutManager(activity?.applicationContext, 1)
//            adapter = mMovieListAdapter
//        }
//        mViewModel.popularMovieSuccessState.observe(viewLifecycleOwner, Observer {
//            this.mMovieListAdapter.setNewList(it as MutableList<MovieVO>)
//            Toast.makeText(requireContext(), it.size.toString(), Toast.LENGTH_SHORT).show()
//        })
//        mViewModel.popularMovieErrorState.observe(viewLifecycleOwner, Observer {
//            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//        })
//        mViewModel.loadPopularMovieList()
//    }
//
//    override fun observers() {
//    }
//
//    override fun listeners() {
//    }
//
//}