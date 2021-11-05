package com.jonathansteele.spacexlaunch.android

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jonathansteele.spacexlaunch.android.ui.theme.SpaceX_LaunchTheme
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

@Composable
fun CompanyList(companyInfo: GetCompanyInfoQuery.Company) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Company Info") }) }
    ) {
        LazyColumn(contentPadding = it) {
            item {
                Text(text = companyInfo.name!!, style = typography.subtitle1)
                Text(text = companyInfo.founded.toString(), style = typography.body2)
                Divider(color = Color.Black, thickness = 1.dp)
            }
            item {
                Text(text = "CEO", style = typography.subtitle1)
                Text(text = companyInfo.ceo!!, style = typography.body2)
                Divider(color = Color.Black, thickness = 1.dp)
            }
            item {
                Text(text = "CTO", style = typography.subtitle1)
                Text(text = companyInfo.cto!!, style = typography.body2)
                Divider(color = Color.Black, thickness = 1.dp)
            }
            item {
                Text(text = "COO", style = typography.subtitle1)
                Text(text = companyInfo.coo!!, style = typography.body2)
                Divider(color = Color.Black, thickness = 1.dp)
            }
            item {
                val currencyFormat = NumberFormat.getCurrencyInstance()
                Text(text = "Valuation", style = typography.subtitle1)
                Text(text = currencyFormat.format(companyInfo.valuation), style = typography.body2)
                Divider(color = Color.Black, thickness = 1.dp)
            }
            item {
                val hq = companyInfo.headquarters!!
                Text(text = "Location", style = typography.subtitle1)
                Text(text = hq.city.plus(", ").plus(hq.state), style = typography.body2)
                Divider(color = Color.Black, thickness = 1.dp)
            }
            item {
                val decimalFormat = DecimalFormat()
                Text(text = "Employees", style = typography.subtitle1)
                Text(text = decimalFormat.format(companyInfo.employees), style = typography.body2)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompanyTabsPreview() {
    SpaceX_LaunchTheme {
        CompanyList(
            companyInfo = GetCompanyInfoQuery.Company(
                name = "SpaceX",
                founded = 2002,
                ceo = "Elon Musk",
                cto = "Elon Musk",
                coo = "Gwynne Shotwell",
                valuation = 52000000000.0,
                employees = 8000,
                headquarters = GetCompanyInfoQuery.Headquarters(
                    city = "Hawthorne",
                    state = "California"
                )
            )
        )
    }
}
