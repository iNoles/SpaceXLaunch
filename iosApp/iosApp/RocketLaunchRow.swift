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
        formatter.timeStyle = .medium
        return formatter
    }()

    var body: some View {
        let date = RocketLaunchRow.isoFormatter.date(from: rocketLaunch.date_utc!)!
        HStack() {
            LazyImage(source: rocketLaunch.links?.patch?.small)
                .frame(width: 90, height: 90, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
            VStack(alignment: .leading, spacing: 10.0) {
                Text("Launch name: \(rocketLaunch.name!)")
                Text(launchText).foregroundColor(launchColor)
                Text("Launch details: \(rocketLaunch.details ?? "")")
                Text(RocketLaunchRow.dateFormatter.string(from: date))
            }
            Spacer()
        }
    }
}

extension RocketLaunchRow {
   private var launchText: String {
       if let isSuccess = rocketLaunch.success {
           return isSuccess.boolValue ? "Successful" : "Unsuccessful"
       } else {
           return "No data"
       }
   }

   private var launchColor: Color {
       if let isSuccess = rocketLaunch.success {
           return isSuccess.boolValue ? Color.green : Color.red
       } else {
           return Color.gray
       }
   }
}
