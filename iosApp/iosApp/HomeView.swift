import SwiftUI
import shared

struct HomeView: View {
  @ObservedObject private(set) var viewModel: ViewModel

    var body: some View {
        NavigationView {
            listView()
            .navigationBarTitle("SpaceX Launches")
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
                RocketLaunchRow(rocketLaunch: launch)
            })
        }
    }
}

extension HomeView {

    enum LoadableLaunches {
        case loading
        case result([GetAllLaunchesQuery.Launch])
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
            sdk.getLaunches(success: { data in
                self.launches = .result(data.launchesFilterNotNull()!)
            })
        }
    }
}


extension GetAllLaunchesQuery.Launch: Identifiable { }
