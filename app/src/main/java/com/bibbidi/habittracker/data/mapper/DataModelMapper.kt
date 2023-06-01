package com.bibbidi.habittracker.data.mapper

interface DataModelMapper<Data, Domain> {

    fun asData(domain: Domain): Data

    fun asDomain(data: Data): Domain
}
