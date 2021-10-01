//
//  UpcomingLaunchRow.swift
//  iosApp
//
//  Created by Jonathan Steele on 9/24/21.
//  Copyright © 2021 Jonathan Steele. All rights reserved.
//

import SwiftUI
import shared
import NukeUI

struct UpcomingLaunchRow: View {
    var rocketLaunch: GetUpcomingLaunchesQuery.Launch
    
    static let dateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateStyle = .medium
        formatter.timeStyle = .short
        return formatter
    }()

    var body: some View {
        let date = Date(timeIntervalSince1970: rocketLaunch.date_unix as! TimeInterval)
        HStack {
            LazyImage(source: rocketLaunch.links?.patch?.small)
                .frame(width: 50, height: 50, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
            VStack(alignment: .leading, spacing: 10.0) {
                Text(rocketLaunch.name!)
                Text(RocketLaunchRow.dateFormatter.string(from: date))
            }
            Text("#\(rocketLaunch.flight_number!)")
        }
    }
}

struct UpcomingLaunchRow_Preview: PreviewProvider {
    static var previews: some View {
        UpcomingLaunchRow(
            rocketLaunch:
                GetUpcomingLaunchesQuery.Launch(
                    name: "FalconStat",
                    date_unix: 00000,
                    flight_number: 1,
                    links:
                        GetUpcomingLaunchesQuery.Links(
                            patch:
                                GetUpcomingLaunchesQuery.Patch(
                                    small: "https://images2.imgbox.com/3c/0e/T8iJcSN3_o.png"
                                )
                        )
                )
        )
    }
}
