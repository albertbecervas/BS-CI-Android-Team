package com.diet.network.diary.model

import com.google.firebase.firestore.PropertyName

data class DiaryDto(
    val userId: String = "",
    @get:PropertyName("summary") @set:PropertyName("summary") var summaryDto: SummaryDto = SummaryDto(),
    @get:PropertyName("foodRegister") @set:PropertyName("foodRegister") var foodRegisterDto: List<FoodRegisterDto> = listOf(),
    @get:PropertyName("goals") @set:PropertyName("goals") var goalsDto: GoalsDto = GoalsDto()
)
