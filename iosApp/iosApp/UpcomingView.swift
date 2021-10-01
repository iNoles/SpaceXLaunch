//
//  UpcomingView.swift
//  iosApp
//
//  Created by Jonathan Steele on 9/19/21.
//  Copyright © 2021 Jonathan Steele. All rights reserved.
//

import SwiftUI
import shared

struct UpcomingView: View {
    @ObservedObject private(set) var viewModel: ViewModel
    
    var body: some View {
        NavigationView {
            listView()
            .navigationBarTitle("Upcoming Launches")
            .navigationBarItems(trailing:
                Button("Reload") {
                    self.viewModel.loadLaunches()
            })
        }
    }
    
    private func listView() -> AnyView {
        switch viewModel.launches {
        case .loading:
            return AnyView(Text("Loading...").multilineTextAlignment(.center))
        case .result(let launches):
            return AnyView(List(launches) { launch in
                UpcomingLaunchRow(rocketLaunch: launch)
            })
        }
    }
}

extension UpcomingView {

    enum LoadableLaunches {
        case loading
        case result([GetUpcomingLaunchesQuery.Launch])
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
            sdk.getUpcomingLaunches(success: { data in
                self.launches = .result(data.launchesFilterNotNull()!)
            })
        }
    }
}

extension GetUpcomingLaunchesQuery.Launch: Identifiable { }
