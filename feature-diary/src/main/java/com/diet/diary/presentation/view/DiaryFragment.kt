package com.diet.diary.presentation.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abecerra.base.presentation.BasePresenterFragment
import com.abecerra.components.diary.FabAddFoodListener
import com.abecerra.components.diary.MealRegisterRecyclerAdapter
import com.diet.common.model.FoodRegisterViewModel
import com.diet.common.model.MacronutrientsViewModel
import com.diet.common.model.MealRegisterViewModel
import com.diet.diary.R
import com.diet.diary.presentation.model.DiaryViewModel
import com.diet.diary.presentation.model.SummaryViewModel
import com.diet.diary.presentation.presenter.DiaryPresenter
import kotlinx.android.synthetic.main.fragment_diary.*


class DiaryFragment : BasePresenterFragment<DiaryPresenter>(R.layout.fragment_diary), DiaryView {

    private lateinit var mealRecyclerAdapter: MealRegisterRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter?.setView(this)
        presenter?.getCurrentDaydiary()
        initRecycler(view)
        setListeners()
    }

    private fun initRecycler(view: View) {
        val mealRegisterRecycler = view.findViewById<RecyclerView>(R.id.recyclerview_meal_register)
        mealRegisterRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mealRecyclerAdapter = MealRegisterRecyclerAdapter().also {
            it.listener = object : FabAddFoodListener {
                override fun onFabAddFoodClick(mealTitle: String) {
                    val mealRegisterItem = it.getItem { mealRegister ->
                        mealRegister.mealTitle == mealTitle
                    }
                    mealRegisterItem?.let { mealRegister ->
                        mealRegister.foodRegister.add(
                            FoodRegisterViewModel(
                                "DummyItem" + mealRegister.foodRegister.size,
                                0,
                                MacronutrientsViewModel(0, 0, 0, 0, 0, 0)
                            )
                        )
                        presenter?.addFoodRegisterToMeal(it.getItems())
                    }
                }
            }
        }
        mealRegisterRecycler.adapter = mealRecyclerAdapter
    }

    private fun setListeners() {
        button_diary_add_meal.setOnClickListener {
            presenter?.addMeal(
                MealRegisterViewModel(
                    "DummyMeal" + mealRecyclerAdapter.itemCount,
                    mutableListOf(),
                    MacronutrientsViewModel(0, 0, 0, 0, 0, 0), 0
                )
            )
        }
    }

    override fun showEmptyDiary() {
        TODO("think about empty diary")
    }

    override fun updateMealList(mealRegisterList: List<MealRegisterViewModel>) {
        mealRecyclerAdapter.setItems(mealRegisterList)
    }

    override fun addMeal(meal: MealRegisterViewModel) {
        mealRecyclerAdapter.addItem(meal)
    }

    override fun showDiary(diary: DiaryViewModel) {
        fillProgressBars(diary.summary)
        fillSummaryTextViews(diary.summary)
        mealRecyclerAdapter.addItems(diary.mealRegister)
    }

    private fun fillProgressBars(summaryViewModel: SummaryViewModel) {
        with(summaryViewModel) {
            progress_summary_calories.progress = caloriesPercent
            with(macronutrients) {
                progress_summary_protein.progress = proteinPercent
                progress_summary_carbohydrates.progress = carbohydratesPercent
                progress_summary_fat.progress = fatPercent
            }
        }
    }

    private fun fillSummaryTextViews(summaryViewModel: SummaryViewModel) {
        with(summaryViewModel) {
            textview_summary_calories_value.text = calories.toString()
            with(macronutrients) {
                textview_summary_protein_value.text = protein.toString()
                textview_summary_carbohydrates_value.text = carbohydrates.toString()
                textview_summary_fat_value.text = fat.toString()
            }
        }
    }
}