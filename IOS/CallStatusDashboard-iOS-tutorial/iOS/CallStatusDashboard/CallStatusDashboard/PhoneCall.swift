//
//  PhoneCall.swift
//  CallStatusDashboard
//
//  Created by Joan Angb on 30/08/2017.
//  Copyright © 2017 Sam Agnew. All rights reserved.
//

import Foundation
struct PhoneCall {
    let callSid: String
    let toNumber: String
    let fromNumber: String
    var callStatus: String
    
    init(callSid: String, toNumber: String, fromNumber: String, callStatus: String) {
        self.callSid = callSid
        self.toNumber = toNumber
        self.fromNumber = fromNumber
        self.callStatus = callStatus
    }
}
