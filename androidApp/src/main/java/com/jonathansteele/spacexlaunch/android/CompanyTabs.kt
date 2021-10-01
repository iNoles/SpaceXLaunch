package com.jonathansteele.spacexlaunch.android

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jonathansteele.spacexlaunch.shared.GetCompanyInfoQuery
import com.jonathansteele.spacexlaunch.shared.SpaceXRepository
import java.text.DecimalFormat
import java.text.NumberFormat

@Composable
fun CompanyTabs(repo: SpaceXRepository) {
    val companyData = repo.getCompany().collectAsState(initial = null).value
    companyData?.data?.company?.let {
        CompanyList(companyInfo = it)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CompanyList(companyInfo: GetCompanyInfoQuery.Company) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Company Info") }) }
    ) {
        LazyColumn {
            item {
                ListItem(
                    text = { Text(text = companyInfo.name!!) },
                    secondaryText = { Text(text = companyInfo.founded.toString()) }
                )
                Divider(color = Color.Black, thickness = 1.dp)
            }
            item {
                ListItem(
                    text = { Text(text = "CEO") },
                    secondaryText = { Text(text = companyInfo.ceo!!) }
                )
                Divider(color = Color.Black, thickness = 1.dp)
            }
            item {
                ListItem(
                    text = { Text(text = "CTO") },
                    secondaryText = { Text(text = companyInfo.cto!!) }
                )
                Divider(color = Color.Black, thickness = 1.dp)
            }
            item {
                ListItem(
                    text = { Text(text = "COO") },
                    secondaryText = { Text(text = companyInfo.coo!!) }
                )
                Divider(color = Color.Black, thickness = 1.dp)
            }
            item {
                val currencyFormat = NumberFormat.getCurrencyInstance()
                ListItem(
                    text = { Text(text = "Valuation") },
                    secondaryText = { Text(text = currencyFormat.format(companyInfo.valuation)) }
                )
                Divider(color = Color.Black, thickness = 1.dp)
            }
            item {
                val hq = companyInfo.headquarters
                ListItem(
                    text = { Text(text = "Location") },
                    secondaryText = { Text(text = "${hq?.city}, ${hq?.state}") }
                )
            }
            item {
                val decimalFormat = DecimalFormat()
                ListItem(
                    text = { Text(text = "Employees") },
                    secondaryText = { Text(text = decimalFormat.format(companyInfo.employees)) }
                )
            }
        }
    }
}