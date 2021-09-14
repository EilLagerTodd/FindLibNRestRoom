package com.example.a5_week_first.TTdata

data class GetParkInfo(
    val RESULT: RESULT,
    val list_total_count: Int,
    val row: List<Row>
)