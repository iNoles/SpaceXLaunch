import SwiftUI
import shared

@main
struct iOSApp: App {
    let sdk = SpaceXRepository()
    var body: some Scene {
        WindowGroup {
            TabView {
                HomeView(viewModel: .init(sdk: sdk)).tabItem {
                    Image(systemName: "house")
                    Text("Home")
                }
                UpcomingView(viewModel: .init(sdk: sdk)).tabItem {
                    Image("Watch Later")
                    Text("Upcoming")
                }
                CompanyView(viewModel: .init(sdk: sdk)).tabItem {
                    Image("Corporate")
                    Text("Company")
                }
            }
        }
    }
}
