//
//  RocketLaunchRow.swift
//  iosApp
//
//  Created by Jonathan Steele on 7/29/21.
//  Copyright © 2021 Jonathan Steele. All rights reserved.
//

import SwiftUI
import shared
import NukeUI

struct RocketLaunchRow: View {
    var rocketLaunch: GetAllLaunchesQuery.Launch
    
    static let isoFormatter: ISO8601DateFormatter = {
        let formatter = ISO8601DateFormatter()
        formatter.formatOptions =  [.withInternetDateTime, .withFractionalSeconds]
        return formatter
    }()
    
    static let dateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateStyle = .medium
        formatter.timeStyle = .short
        return formatter
    }()

    var body: some View {
        let date = RocketLaunchRow.isoFormatter.date(from: rocketLaunch.date_utc!)!
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

struct RocketLaunchRow_Previews: PreviewProvider {
    static var previews: some View {
        RocketLaunchRow(
            rocketLaunch:
                GetAllLaunchesQuery.Launch(
                    id: "5eb87cd9ffd86e000604b32a",
                    name: "FalconStat",
                    date_utc: "2006-03-24T22:30:00.000Z",
                    flight_number: 1,
                    links:
                        GetAllLaunchesQuery.Links(
                            article: "https://www.space.com/2196-spacex-inaugural-falcon-1-rocket-lost-launch.html",
                            patch:
                                GetAllLaunchesQuery.Patch(
                                    small: "https://images2.imgbox.com/3c/0e/T8iJcSN3_o.png"
                                )
                        )
                )
        )
    }
}
