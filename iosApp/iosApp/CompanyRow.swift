//
//  CompanyRow.swift
//  iosApp
//
//  Created by Jonathan Steele on 9/20/21.
//  Copyright © 2021 Jonathan Steele. All rights reserved.
//

import SwiftUI
import shared

struct CompanyRow: View {
    var company: GetCompanyInfoQuery.Company
    
    static let currencyFormatter: NumberFormatter = {
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        return formatter
    }()
    
    static let decimalFormatter: NumberFormatter = {
       let formatter = NumberFormatter()
        formatter.numberStyle = .decimal
        return formatter
    }()
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(company.name!)
            Text(company.founded!.stringValue)
        }
        VStack(alignment: .leading) {
            Text("CEO")
            Text(company.ceo!)
        }
        VStack(alignment: .leading) {
            Text("CTO")
            Text(company.cto!)
        }
        VStack(alignment: .leading) {
            Text("COO")
            Text(company.coo!)
        }
        VStack(alignment: .leading) {
            Text("Valuation")
            Text(CompanyRow.currencyFormatter.string(from: company.valuation!)!)
        }
        VStack(alignment: .leading) {
            Text("Location")
            Text("\(company.headquarters!.city!), \(company.headquarters!.state!)")
        }
        VStack(alignment: .leading) {
            Text("Employees")
            Text(CompanyRow.decimalFormatter.string(from: company.employees!)!)
        }
    }
}

struct CompanyRow_Previews: PreviewProvider {
    static var previews: some View {
        CompanyRow(
            company: GetCompanyInfoQuery.Company(
                name: "Space X",
                founded: 2002,
                ceo: "Elon Musk",
                cto: "Elon Musk",
                coo: "Gwynne Shotwell",
                valuation: 74000000000,
                employees: 9500,
                headquarters:
                    GetCompanyInfoQuery.Headquarters(
                        city: "Hawtrone",
                        state: "CA"
                    )
            )
        )
    }
}
