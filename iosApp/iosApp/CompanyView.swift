//
//  CompanyView.swift
//  iosApp
//
//  Created by Jonathan Steele on 9/19/21.
//  Copyright © 2021 Jonathan Steele. All rights reserved.
//

import SwiftUI
import shared

struct CompanyView: View {
    @ObservedObject private(set) var viewModel: ViewModel 
    var body: some View {
        NavigationView {
            listView().navigationBarTitle("Company Info")
        }
    }
    
    private func listView() -> AnyView {
        switch viewModel.launches {
        case .loading:
            return AnyView(Text("Loading...").multilineTextAlignment(.center))
        case .result(let company):
            return AnyView(List {
                CompanyRow(company: company)
            })
        }
    }
}

extension CompanyView {

    enum LoadableLaunches {
        case loading
        case result(GetCompanyInfoQuery.Company)
    }

    class ViewModel: ObservableObject {
        let sdk: SpaceXRepository
        @Published var launches = LoadableLaunches.loading

        init(sdk: SpaceXRepository) {
            self.sdk = sdk
            self.loadLaunches()
        }

        func loadLaunches() {
            self.launches = .loading
            sdk.getCompany(success: { data in
                self.launches = .result(data.company!)
            })
        }
    }
}

extension GetCompanyInfoQuery.Company: Identifiable {}
